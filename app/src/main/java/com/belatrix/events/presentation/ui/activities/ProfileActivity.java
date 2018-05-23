package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.UserFragment;

public class ProfileActivity extends BelatrixBaseActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setToolbar();
        initViews();
        replaceFragment(UserFragment.create(ProfileActivity.this), false);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(ProfileActivity.this);
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
}
