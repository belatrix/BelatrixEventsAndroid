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
import com.belatrix.events.presentation.ui.fragments.ModeratorIdeaDetailFragment;

public class IdeaDetailActivity extends BelatrixBaseActivity {

    private static final String ARGS_PROJECT = "args_project";
    private static final String ARGS_FROM_MODERATOR_SEARCH = "args_from_moderator_search";

    public static Intent makeIntent(Context context, Project project, boolean fromModeratorSearch) {
        Intent intent = new Intent(context, IdeaDetailActivity.class);
        intent.putExtra(ARGS_PROJECT, project);
        intent.putExtra(ARGS_FROM_MODERATOR_SEARCH, fromModeratorSearch);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        Project project = getIntent().getParcelableExtra(ARGS_PROJECT);
        boolean fromModeratorSearch = getIntent().getBooleanExtra(ARGS_FROM_MODERATOR_SEARCH, false);
        if (fromModeratorSearch) {
            replaceFragment(ModeratorIdeaDetailFragment.create(IdeaDetailActivity.this, project), false);
        } else {
            replaceFragment(IdeaDetailFragment.create(IdeaDetailActivity.this, project), false);
        }
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(IdeaDetailActivity.this);
    }
}
