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
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.presenters.ManageIdeaPresenter;
import com.belatrix.events.presentation.ui.adapters.ModeratorEventListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ManageIdeasFragment extends BelatrixBaseFragment implements ManageIdeaPresenter.View, ModeratorEventListAdapter.OnItemPressListener {

    private static final String ARGS_CITY_ID = "args_city_id";
    private static final String ARGS_FROM_MY_IDEAS = "args_from_my_ideas";

    @BindView(R.id.rv_events)
    RecyclerView rvEvents;

    @Inject
    ManageIdeaPresenter mPresenter;

    private ModeratorEventListAdapter mModeratorEventListAdapter;

    public static Fragment create(Context context, int cityId, boolean fromMyIdeas) {
        Bundle args = new Bundle();
        args.putInt(ARGS_CITY_ID, cityId);
        args.putBoolean(ARGS_FROM_MY_IDEAS, fromMyIdeas);
        return Fragment.instantiate(context, ManageIdeasFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_idea, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(ManageIdeasFragment.this);
        mPresenter.setView(ManageIdeasFragment.this);
    }

    @Override
    protected void initViews() {
        mModeratorEventListAdapter = new ModeratorEventListAdapter(ManageIdeasFragment.this);
        rvEvents.setAdapter(mModeratorEventListAdapter);
        if (getContext() != null) {
            rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEvents.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), android.R.drawable.divider_horizontal_bright)));
            mPresenter.listEvent();
        }
        if (getArguments() != null && getArguments().containsKey(ARGS_FROM_MY_IDEAS)) {
            setTitle(getString(R.string.menu_title_my_ideas));
        } else {
            setTitle(getString(R.string.menu_title_manage_ideas));
        }
    }

    @Override
    public void onListEventLoaded(List<Event> lstEvent) {
        mModeratorEventListAdapter.addAll(lstEvent);
    }

    @Override
    public void onListEventError() {

    }

    @Override
    public void onItemPressed(Event event) {
        if (getArguments() != null && getArguments().containsKey(ARGS_FROM_MY_IDEAS)) {
            replaceFragment(MyIdeasFragment.create(getContext(), event.getId()), true);
        } else {
            replaceFragment(ModeratorListIdeasFragment.create(getContext(), event.getId()), true);
        }
    }
}
