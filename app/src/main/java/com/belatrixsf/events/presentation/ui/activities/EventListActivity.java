package com.belatrixsf.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.fragments.EventListFragment;
import com.belatrixsf.events.utils.Constants;

public class EventListActivity extends BelatrixBaseActivity  {

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        String title = getIntent().getStringExtra(Constants.EVENT_TITLE);
        String type = getIntent().getStringExtra(Constants.EVENT_TYPE);
        replaceFragment(EventListFragment.newInstance(type,title),false);
        setNavigationToolbar();
    }

    public static Intent makeIntent(Activity context, String eventType, String eventTitle) {
        Intent intent = new Intent(context, EventListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EVENT_TITLE,eventTitle);
        bundle.putString(Constants.EVENT_TYPE,eventType);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.overridePendingTransition(0, 0);
        return intent;
    }

}
