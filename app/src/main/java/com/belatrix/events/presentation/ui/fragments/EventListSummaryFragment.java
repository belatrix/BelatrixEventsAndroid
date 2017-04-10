package com.belatrix.events.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.presenters.EventListFragmentPresenter;
import com.belatrix.events.presentation.ui.activities.EventDetailActivity;
import com.belatrix.events.presentation.ui.activities.EventListActivity;
import com.belatrix.events.presentation.ui.adapters.EventListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.Constants;
import com.belatrix.events.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by dvelasquez
 */
public class EventListSummaryFragment extends BelatrixBaseFragment implements EventListFragmentPresenter.View, EventListAdapter.RecyclerViewClickListener {

    @Inject
    EventListFragmentPresenter presenter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_events)
    RecyclerView recyclerView;
    EventListAdapter listAdapter;
    @BindView(R.id.event_title_textview)
    TextView eventTitleTextView;
    @BindView(R.id.event_more_textview)
    TextView eventMoreTextView;


    public EventListSummaryFragment() {
    }

    public static EventListSummaryFragment newInstance(String eventType, String eventTitle) {
        EventListSummaryFragment fragment = new EventListSummaryFragment();
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
        listAdapter = new EventListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        presenter.actionGetEventList();
        eventTitleTextView.setText(presenter.getEventTitle());
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
        return inflater.inflate(R.layout.fragment_event_list_summary, container, false);
    }

    @Override
    public void showEventList(List<Event> list) {
        eventMoreTextView.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
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
        eventMoreTextView.setVisibility(View.GONE);
       progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }

    public void cancelRequest(){
        presenter.cancelRequests();
    }

    @Override
    public void showEmptyView() {
        eventMoreTextView.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.event_more_textview)
    public void onClickMore(){
        startActivity(EventListActivity.makeIntent(getActivity(),presenter.getEventType(),presenter.getEventTitle()));
    }

}
