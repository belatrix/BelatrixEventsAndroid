package com.belatrix.events.domain.interactors;

import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.UserRepository;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UpdateProfileInteractor extends AbstractInteractor {

    private final UserRepository mUserRepository;
    private final AccountUtils mAccountUtils;

    @Inject
    UpdateProfileInteractor(UserRepository userRepository, AccountUtils accountUtils) {
        this.mUserRepository = userRepository;
        mAccountUtils = accountUtils;
    }

    public void updateProfile(final Callback callback, final String fullName, String phoneName, int roleId) {
        disposable = mUserRepository.updateUser(mAccountUtils.getToken(), fullName, phoneName, roleId).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mAccountUtils.setFullName(user.getFullName());
                mAccountUtils.setPhoneNumber(user.getPhoneNumber());
                mAccountUtils.setIsActive(user.isActive());
                mAccountUtils.setIsJury(user.isJury());
                mAccountUtils.setIsModerator(user.isModerator());
                mAccountUtils.setIsParticipant(user.isParticipant());
                mAccountUtils.setIsStaff(user.isStaff());
                if (user.getRole() != null) {
                    mAccountUtils.setRoleId(Integer.toString(user.getRole().getId()));
                    mAccountUtils.setRoleName(user.getRole().getName());
                }

                callback.onUserUpdated();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onUserError();
            }
        });
    }

    public interface Callback {
        void onUserUpdated();

        void onUserError();
    }
}
