package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.ChangePasswordFragment;
import com.belatrix.events.presentation.ui.fragments.LoginFragment;

public class AuthenticatorActivity extends BelatrixBaseActivity implements LoginFragment.LoginCallback, ChangePasswordFragment.ChangePasswordCallback {

    public static Intent makeIntent(Context context) {
        return new Intent(context, AuthenticatorActivity.class);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);
        setToolbar();
        setupViews();
    }

    private void setupViews() {
        replaceFragment(LoginFragment.newInstance(AuthenticatorActivity.this), false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onLoginSuccessful() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setTitle(final String title) {
        if (toolbar != null) {
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        getSupportActionBar().setTitle(title);
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
    public void onChangedPassword() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        replaceFragment(LoginFragment.newInstance(AuthenticatorActivity.this), false);
    }
}
