package com.belatrix.events.presentation.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.belatrix.events.R;
import com.belatrix.events.data.datasource.common.Resource;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;
import com.belatrix.events.presentation.ui.activities.EventDetailActivity;
import com.belatrix.events.presentation.ui.adapters.NewEventAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.EventSpaceDecoration;
import com.belatrix.events.presentation.viewmodel.EventViewModel;

import java.util.List;

import javax.inject.Inject;

public class NewHomeFragment extends BelatrixBaseFragment implements NewEventAdapter.OnItemClickListener, Observer<Resource<List<Event>>> {

    private static final String ARGS_CITY_ID = "args_city_id";
    @Inject
    EventRepository eventRepository;

    private RecyclerView rvEvents;
    private SwipeRefreshLayout swipeRefreshLayout;

    private NewEventAdapter mNewEventAdapter;
    private EventViewModel mEventViewModel;

    public static Fragment newInstance(Context context, int cityId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_CITY_ID, cityId);
        return Fragment.instantiate(context, NewHomeFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_home, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(NewHomeFragment.this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvEvents = view.findViewById(R.id.recycler_events);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initViews() {
        setupRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getArguments() != null && getArguments().containsKey(ARGS_CITY_ID)) {
                    mEventViewModel.refresh(getArguments().getInt(ARGS_CITY_ID));
                }
            }
        });
    }

    private void setupRecyclerView() {
        mNewEventAdapter = new NewEventAdapter(NewHomeFragment.this);
        rvEvents.setAdapter(mNewEventAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvEvents.setLayoutManager(gridLayoutManager);
        rvEvents.addItemDecoration(new EventSpaceDecoration(getContext(), R.dimen.dimen_8));
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        if (getArguments() != null && getArguments().containsKey(ARGS_CITY_ID)) {
            mEventViewModel.init(eventRepository, getArguments().getInt(ARGS_CITY_ID));
            mEventViewModel.getListEvent().observe(NewHomeFragment.this, NewHomeFragment.this);
        }
    }

    @Override
    public void onChanged(@Nullable Resource<List<Event>> resource) {
        swipeRefreshLayout.setRefreshing(false);
        if (resource != null) {
            switch (resource.status) {
                case SUCCESS:
                    mNewEventAdapter.addAll(resource.data);
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        }
    }

    @Override
    public void onItemPressed(ImageView ivEvent, Event event) {
        EventDetailActivity.startActivity(getActivity(), event, ivEvent);
    }
}
