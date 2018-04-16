package com.belatrix.events.domain.interactors;

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
    public SignInInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        this.mAccountUtils = accountUtils;
    }

    public void signIn(final Callback callback, final String username, final String password) {
//        TODO Change hardcoded account to call to web services.
        mAccountUtils.createAccount("Luis Miguel Burgos", username, password);
        callback.onSignInSuccessful();
//        disposable = mUserRepository.signIn(username, password).subscribe(new Consumer<User>() {
//            @Override
//            public void accept(User user) throws Exception {
//                mAccountUtils.createAccount(user.getName(), username, password);
//                callback.onSignInSuccessful();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                callback.onSignInError();
//            }
//        });
    }

    public interface Callback {
        void onSignInSuccessful();

        void onSignInError();
    }
}
