package com.belatrix.events.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.presenters.EventListFragmentPresenter;
import com.belatrix.events.presentation.ui.activities.EventDetailActivity;
import com.belatrix.events.presentation.ui.adapters.EventListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.ItemOffsetDecoration;
import com.belatrix.events.utils.Constants;
import com.belatrix.events.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

/**
 * created by dvelasquez
 */
public class EventListFragment extends BelatrixBaseFragment implements EventListFragmentPresenter.View, EventListAdapter.RecyclerViewClickListener {

    @Inject
    EventListFragmentPresenter presenter;
    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_events)
    RecyclerView recyclerView;
    EventListAdapter listAdapter;
    @BindView(R.id.event_title_textview)
    TextView eventTitleTextView;
    @BindString(R.string.menu_title_share)
    String stringShare;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public EventListFragment() {
    }

    public static EventListFragment newInstance(String eventType, String eventTitle) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.EVENT_TYPE,eventType);
        args.putString(Constants.EVENT_TITLE,eventTitle);
        fragment.setArguments(args);
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
        listAdapter = new EventListAdapter(this,R.layout.item_event_grid);
        recyclerView.setAdapter(listAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_column_items));
        gridLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.grid_off_set);
        recyclerView.addItemDecoration(itemDecoration);
        presenter.actionGetEventList();
        eventTitleTextView.setText(presenter.getEventTitle());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.actionGetEventList();
            }
        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments() != null){
            presenter.setParams(getArguments().getString(Constants.EVENT_TYPE),getArguments().getString(Constants.EVENT_TITLE));
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void showEventList(List<Event> list) {
        noDataTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        listAdapter.updateData(list);
    }

    @Override
    public void onItemClicked(Event event, ImageView view) {
        EventDetailActivity.startActivity(getActivity(),event,view);
    }

    @Override
    public void onItemMoreClicked(View view) {
        final Event event = (Event) view.getTag();
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_event_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.menu_share){
                    String sharingText = (event.getSharingText() != null && !event.getSharingText().isEmpty() ? event.getSharingText(): event.getTitle());
                    DialogUtils.shareContent(getActivity(),sharingText+"\n"+event.getImage(), stringShare);
                }
                return false;
            }
        });
        popup.show();
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

    @Override
    public void showEmptyView() {
        recyclerView.setVisibility(View.INVISIBLE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMoreEventsButton(boolean show) {
        //this fragment doesn't have more button
    }

}
