package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ListRoleInteractor;
import com.belatrix.events.domain.interactors.UpdateProfileInteractor;
import com.belatrix.events.domain.model.Role;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

public class EditProfilePresenter extends BelatrixBasePresenter<EditProfilePresenter.View> implements UpdateProfileInteractor.Callback, ListRoleInteractor.Callback {

    private final UpdateProfileInteractor mUpdateProfileInteractor;
    private final ListRoleInteractor mListRoleInteractor;
    private final AccountUtils mAccountUtils;

    @Inject
    public EditProfilePresenter(UpdateProfileInteractor updateProfileInteractor, ListRoleInteractor listRoleInteractor, AccountUtils accountUtils) {
        this.mUpdateProfileInteractor = updateProfileInteractor;
        this.mListRoleInteractor = listRoleInteractor;
        this.mAccountUtils = accountUtils;
    }

    @Override
    public void cancelRequests() {
        mUpdateProfileInteractor.cancel();
    }

    public void updateProfile(String fullName, String phoneNumber, int roleId) {
        mUpdateProfileInteractor.updateProfile(EditProfilePresenter.this, fullName, phoneNumber, roleId);
    }

    @Override
    public void onUserUpdated() {
        view.onUserUpdated();
    }

    @Override
    public void onUserError() {
        view.onUserError();
    }

    public void loadListInteractor() {
        mListRoleInteractor.loadRoleList(EditProfilePresenter.this);
    }

    @Override
    public void onListRoleSuccessful(List<Role> lst) {
        view.onRoleLoaded(lst);
        int roleId = Integer.parseInt(mAccountUtils.getRoleId());
        if (roleId != 0) {
            for (int i = 0; i < lst.size(); i++) {
                Role role = lst.get(i);
                if (role.getId() == roleId) {
                    view.setRolePosition(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onListRoleError() {
        view.onRoleError();
    }

    public interface View extends BelatrixBaseView {
        void onUserUpdated();

        void onUserError();

        void onRoleLoaded(List<Role> lst);

        void setRolePosition(int pos);

        void onRoleError();
    }
}
