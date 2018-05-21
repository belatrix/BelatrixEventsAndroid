package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;

public class IdeaEditFragment extends BelatrixBaseFragment {

    private static final String ARGS_PROJECT = "args_project";

    public static Fragment crate(Context context, Project project) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PROJECT, project);
        return Fragment.instantiate(context, IdeaEditFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_idea_add, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {

    }

    @Override
    protected void initViews() {

    }
}
