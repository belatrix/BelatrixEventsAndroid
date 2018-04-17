package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

public class CreateAccountInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;

    @Inject
    public CreateAccountInteractor(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public void createAccount(final Callback callback, final String user, final String email, final String name, final String password) {
        //        TODO Change hardcoded account to call to web services.
        callback.onCreateAccountSuccessful();
//        disposable = mUserRepository.createAccount(user, email, name, password).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                mAccountUtils.createAccount(name, email, password);
//                callback.onCreateAccountSuccessful();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                callback.onCreateAccountFailed();
//            }
//        });
    }

    public interface Callback {
        void onCreateAccountSuccessful();

        void onCreateAccountFailed();
    }
}
