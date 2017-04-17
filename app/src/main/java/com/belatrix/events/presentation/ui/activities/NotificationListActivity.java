package com.belatrix.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.NotificationListFragment;

/**
 * Created by raulrashuaman on 4/11/17.
 */

public class NotificationListActivity extends BelatrixBaseActivity {

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        replaceFragment(NotificationListFragment.newInstance(), false);
        setNavigationToolbar();
    }

    public static Intent makeIntent(Activity context) {
        Intent intent = new Intent(context, NotificationListActivity.class);
        return intent;
    }
}
