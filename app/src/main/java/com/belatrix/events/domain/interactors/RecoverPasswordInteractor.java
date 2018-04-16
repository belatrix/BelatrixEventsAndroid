package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RecoverPasswordInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;

    @Inject
    public RecoverPasswordInteractor(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public void recoverPassword(final Callback callback, String email) {
        //        TODO Change hardcoded account to call to web services.
        callback.onRecoverSuccessful();
//        disposable = mUserRepository.recoverPassword(email).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                callback.onRecoverSuccessful();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                callback.onRecoverError();
//            }
//        });
    }

    public interface Callback {
        void onRecoverSuccessful();

        void onRecoverError();
    }
}
