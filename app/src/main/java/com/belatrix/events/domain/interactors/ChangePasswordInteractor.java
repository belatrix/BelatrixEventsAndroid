package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ChangePasswordInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    ChangePasswordInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        this.mAccountUtils = accountUtils;
    }

    public void changePassword(final Callback callback, final int userId, String oldPassword, final String newPassword) {
        disposable = mUserRepository.changePassword(userId, oldPassword, newPassword).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                fetchUserData(callback, userId, newPassword);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onChangeError();
            }
        });
    }

    private void fetchUserData(final ChangePasswordInteractor.Callback callback, final int userId, final String password) {
        disposable = mUserRepository.getUser(userId).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                mAccountUtils.createAccount(userId, user.getFirstName(), user.getLastName(), user.isStaff(), user.isActive(), user.isParticipant(), user.getEmail(), password);
                callback.onChangeSuccessful();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onChangeError();
            }
        });
    }

    public interface Callback {
        void onChangeSuccessful();

        void onChangeError();
    }
}
