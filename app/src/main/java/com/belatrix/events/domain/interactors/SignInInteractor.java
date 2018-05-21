package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.UserAuthenticationResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class SignInInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    SignInInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        this.mAccountUtils = accountUtils;
    }

    public void signIn(final SignInInteractor.Callback callback, final String username, final String password) {
        disposable = mUserRepository.signIn(username, password).subscribe(new Consumer<UserAuthenticationResponse>() {
            @Override
            public void accept(UserAuthenticationResponse userAuthenticationResponse) {
                if (userAuthenticationResponse.isPasswordResetRequired()) {
                    callback.onChangePassword(userAuthenticationResponse.getToken(), userAuthenticationResponse.getUserId());
                } else {
                    fetchUserData(callback, userAuthenticationResponse.getToken(), userAuthenticationResponse.getUserId(), password);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onSignInError();
            }
        });
    }

    private void fetchUserData(final SignInInteractor.Callback callback, final String token, final int userId, final String password) {
        disposable = mUserRepository.getUser(token, userId).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                mAccountUtils.createAccount(token, user.getId(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getPhoneNumber(),
                        password,
                        user.getRole() != null ? user.getRole().getId() + "" : "0",
                        user.getRole() != null ? user.getRole().getName() : null,
                        user.isModerator(),
                        user.isStaff(),
                        user.isActive(),
                        user.isParticipant(),
                        user.isJury());
                callback.onSignInSuccessful();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onSignInError();
            }
        });
    }

    public interface Callback {
        void onChangePassword(String token, int userId);

        void onSignInSuccessful();

        void onSignInError();
    }
}
