package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.ProfilePresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BelatrixBaseActivity implements ProfilePresenter.View {

    @BindView(R.id.tv_fullname)
    TextView tvFullName;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.iv_qr_code)
    ImageView ivQRCode;

    @Inject
    ProfilePresenter mPresenter;
    @Inject
    AccountUtils mAccountUtils;

    public static Intent makeIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbar();
        initViews();
        mPresenter.loadUser();
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(ProfileActivity.this);
        mPresenter.setView(ProfileActivity.this);
    }

    private void initViews() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            setTitle("");
        }
    }

    @Override
    public void setTitle(String title) {
        if (toolbar != null) {
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        getSupportActionBar().setTitle("");
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public Context getContext() {
        return ProfileActivity.this;
    }


    @OnClick(R.id.tv_sign_out)
    public void onClickSignOutEvent() {
        mAccountUtils.signOut();
        finish();
    }
}
