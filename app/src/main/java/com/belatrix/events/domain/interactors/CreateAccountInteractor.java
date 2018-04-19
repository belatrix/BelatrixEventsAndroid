package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CreateAccountInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;

    @Inject
    CreateAccountInteractor(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public void createAccount(final Callback callback, final String email) {
        disposable = mUserRepository.createAccount(email).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                callback.onCreateAccountSuccessful();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onCreateAccountFailed();
            }
        });
    }

    public interface Callback {
        void onCreateAccountSuccessful();

        void onCreateAccountFailed();
    }
}
