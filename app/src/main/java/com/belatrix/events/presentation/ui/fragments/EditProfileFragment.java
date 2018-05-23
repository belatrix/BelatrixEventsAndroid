package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Role;
import com.belatrix.events.presentation.presenters.EditProfilePresenter;
import com.belatrix.events.presentation.ui.adapters.RoleAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class EditProfileFragment extends BelatrixBaseFragment implements EditProfilePresenter.View {

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_fullname)
    EditText etFullname;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.sp_role)
    Spinner spRole;

    @Inject
    EditProfilePresenter mPresenter;
    @Inject
    AccountUtils mAccountUtils;

    private RoleAdapter mRoleAdapter;

    public static Fragment create(Context context) {
        Bundle args = new Bundle();
        return EditProfileFragment.instantiate(context, EditProfileFragment.class.getName(), args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(EditProfileFragment.this);
        mPresenter.setView(EditProfileFragment.this);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListInteractor();
        assert getContext() != null;
        mRoleAdapter = new RoleAdapter(getContext());
        etEmail.setText(mAccountUtils.getEmail());
        etFullname.setText(mAccountUtils.getFullName());
        etPhoneNumber.setText(mAccountUtils.getPhoneNumber());
        spRole.setAdapter(mRoleAdapter);
    }

    @Override
    public void onUserUpdated() {
        replaceFragment(UserFragment.create(getContext()), false);
    }

    @Override
    public void onUserError() {

    }

    @Override
    public void onRoleLoaded(List<Role> lst) {
        mRoleAdapter.addAll(lst);
        mRoleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRoleError() {

    }

    @Override
    public void setRolePosition(int pos) {
        spRole.setSelection(pos);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                replaceFragment(UserFragment.create(getContext()), false);
                break;
            case R.id.action_accept:
                updateProfile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateProfile() {
        String fullname = etFullname.getText().toString();
        String phonenumber = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            etFullname.setError(getString(R.string.field_empty_2));
        }
        if (TextUtils.isEmpty(phonenumber)) {
            etPhoneNumber.setError(getString(R.string.field_empty_2));
        }
        if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(phonenumber) && getArguments() != null) {
            mPresenter.updateProfile(fullname, phonenumber, ((Role) spRole.getSelectedItem()).getId());
        }
    }
}
