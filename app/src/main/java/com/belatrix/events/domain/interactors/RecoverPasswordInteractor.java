package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RecoverPasswordInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;

    @Inject
    RecoverPasswordInteractor(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public void recoverPassword(final Callback callback, String email) {
        disposable = mUserRepository.recoverPassword(email).subscribe(new Consumer<String>() {
            @Override
            public void accept(String sString) {
                callback.onRecoverSuccessful();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onRecoverError();
            }
        });
    }

    public interface Callback {
        void onRecoverSuccessful();

        void onRecoverError();
    }
}
