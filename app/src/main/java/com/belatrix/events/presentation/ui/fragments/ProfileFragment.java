package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.ProfilePresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileFragment extends BelatrixBaseFragment implements ProfilePresenter.View {

    @BindView(R.id.iv_qr_code)
    ImageView ivQRCode;

    @BindView(R.id.tv_fullname)
    TextView tvFullName;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;

    @BindView(R.id.tv_role)
    TextView tvRole;

    @Inject
    ProfilePresenter mPresenter;
    @Inject
    AccountUtils mAccountUtils;

    public static Fragment create(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, ProfileFragment.class.getName(), args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(ProfileFragment.this);
        mPresenter.setView(ProfileFragment.this);
    }

    @Override
    protected void initViews() {
        mPresenter.loadUser();
    }

    @Override
    public void loadEmailField(String email) {
        tvEmail.setText(email);
    }

    @Override
    public void loadFullNameField(String fullName) {
        tvFullName.setText(fullName);
    }

    @Override
    public void loadQRImage(Bitmap bitmap) {
        ivQRCode.setImageBitmap(bitmap);
    }

    @Override
    public void loadPhoneNumberField(String phoneNumber) {
        if(phoneNumber != null) {
            tvPhoneNumber.setText(phoneNumber);
        }
    }

    @Override
    public void loadRoleName(String role) {
        if(role != null) {
            tvRole.setText(role);
        }
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                replaceFragment(EditProfileFragment.create(getContext()), false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.tv_sign_out)
    public void onClickSignOutEvent() {
        mAccountUtils.signOut();
        getActivity().finish();
    }

}
