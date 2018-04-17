package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ChangePasswordInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;

    @Inject
    public ChangePasswordInteractor(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public void changePassword(final Callback callback, String oldPassword, String newPassword) {
        //        TODO Change hardcoded account to call to web services.
        callback.onChangeSuccessful();
//        disposable = mUserRepository.changePassword(oldPassword, newPassword).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                callback.onChangeSuccessful();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                callback.onChangeError();
//            }
//        });
    }

    public interface Callback {
        void onChangeSuccessful();

        void onChangeError();
    }
}
