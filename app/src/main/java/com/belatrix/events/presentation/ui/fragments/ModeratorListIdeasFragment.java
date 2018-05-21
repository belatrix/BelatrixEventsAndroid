package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.ModeratorListIdeasPresenter;
import com.belatrix.events.presentation.ui.adapters.ModeratorIdeaListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ModeratorListIdeasFragment extends BelatrixBaseFragment implements ModeratorListIdeasPresenter.View, ModeratorIdeaListAdapter.OnItemPressListener {

    private static final String ARGS_EVENT_ID = "args_event_id";

    @BindView(R.id.rv_ideas)
    RecyclerView rvIdeas;

    @Inject
    ModeratorListIdeasPresenter mPresenter;

    ModeratorIdeaListAdapter mAdapter;

    public static Fragment create(Context context, int eventId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_EVENT_ID, eventId);
        return Fragment.instantiate(context, ModeratorListIdeasFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moderator_list_idea, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(ModeratorListIdeasFragment.this);
        mPresenter.setView(ModeratorListIdeasFragment.this);
    }

    @Override
    protected void initViews() {
        mAdapter = new ModeratorIdeaListAdapter(ModeratorListIdeasFragment.this);
        rvIdeas.setAdapter(mAdapter);
        if (getContext() != null && getArguments() != null) {
            rvIdeas.setLayoutManager(new LinearLayoutManager(getContext()));
            rvIdeas.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), android.R.drawable.divider_horizontal_bright)));
            mPresenter.listIdeas(getArguments().getInt(ARGS_EVENT_ID));
        }
    }

    @Override
    public void onListLoaded(List<Project> result) {
        mAdapter.addAll(result);
    }

    @Override
    public void onListError() {

    }

    @Override
    public void onItemPressed(Project project) {
        replaceFragment(ModeratorIdeaDetailFragment.create(getContext(), project), true);
    }
}
