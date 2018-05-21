package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.Role;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ListRoleInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    ListRoleInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        this.mAccountUtils = accountUtils;
    }

    public void loadRoleList(final Callback callback) {
        disposable = mUserRepository.listRole(mAccountUtils.getToken()).subscribe(new Consumer<List<Role>>() {
            @Override
            public void accept(List<Role> roles) throws Exception {
                callback.onListRoleSuccessful(roles);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onListRoleError();
            }
        });
    }

    public interface Callback {
        void onListRoleSuccessful(List<Role> lst);

        void onListRoleError();
    }
}
