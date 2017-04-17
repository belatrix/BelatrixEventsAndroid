package com.belatrix.events.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.presentation.presenters.NotificationListFragmentPresenter;
import com.belatrix.events.presentation.ui.adapters.NotificationListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by raulrashuaman on 4/11/17.
 */

public class NotificationListFragment extends BelatrixBaseFragment implements NotificationListFragmentPresenter.View {

    @Inject
    NotificationListFragmentPresenter presenter;

    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_notifications)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    NotificationListAdapter listAdapter;

    public NotificationListFragment() {
    }

    public static NotificationListFragment newInstance() {
        NotificationListFragment fragment = new NotificationListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        presenter.setView(this);
    }

    @Override
    protected void initViews() {
        listAdapter = new NotificationListAdapter();
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        presenter.actionGetNotificationList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.actionGetNotificationList();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_list, container, false);
    }

    @Override
    public void showNotificationList(List<Notification> list) {
        noDataTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        listAdapter.updateData(list);
    }

    @Override
    public void showEmptyView() {
        recyclerView.setVisibility(View.INVISIBLE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }
}
