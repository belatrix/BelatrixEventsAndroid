package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.IdeaAddFragment;

public class IdeaAddActivity extends BelatrixBaseActivity {

    private static final String ARGS_EVENT_ID = "args_event_id";

    public static Intent makeIntent(Context context, int eventId) {
        Intent intent = new Intent(context, IdeaAddActivity.class);
        intent.putExtra(ARGS_EVENT_ID, eventId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_add);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        replaceFragment(IdeaAddFragment.create(IdeaAddActivity.this, getIntent().getIntExtra(ARGS_EVENT_ID, 0)), false);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(IdeaAddActivity.this);

    }
}
