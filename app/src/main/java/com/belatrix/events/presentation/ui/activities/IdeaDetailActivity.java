package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.IdeaDetailFragment;

public class IdeaDetailActivity extends BelatrixBaseActivity {

    private static final String ARGS_PROJECT = "args_project";

    public static Intent makeIntent(Context context, Project project) {
        Intent intent = new Intent(context, IdeaDetailActivity.class);
        intent.putExtra(ARGS_PROJECT, project);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        Project project = getIntent().getParcelableExtra(ARGS_PROJECT);
        replaceFragment(IdeaDetailFragment.create(IdeaDetailActivity.this, project), false);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(IdeaDetailActivity.this);
    }
}
