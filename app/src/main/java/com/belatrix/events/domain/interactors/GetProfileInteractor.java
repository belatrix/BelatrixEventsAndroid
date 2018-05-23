package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Profile;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class GetProfileInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    GetProfileInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        this.mAccountUtils = accountUtils;
    }

    public void getProfile(final Callback callback, int userId) {
        disposable = mUserRepository.getProfile(mAccountUtils.getToken(), userId).subscribe(new Consumer<Profile>() {
            @Override
            public void accept(Profile profile) throws Exception {
                callback.onRetrieveProfile(profile);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onRetrieveError();
            }
        });
    }

    public interface Callback {
        void onRetrieveProfile(Profile profile);

        void onRetrieveError();
    }
}
