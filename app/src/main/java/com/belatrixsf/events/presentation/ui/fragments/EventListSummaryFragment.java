package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.presenters.EventListPresenter;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.activities.EventListActivity;
import com.belatrixsf.events.presentation.ui.adapters.EventListAdapter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.presentation.ui.common.DividerItemDecoration;
import com.belatrixsf.events.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by dvelasquez
 */
public class EventListSummaryFragment extends BelatrixBaseFragment implements EventListPresenter.View, EventListAdapter.RecyclerViewClickListener {

    @Inject
    EventListPresenter presenter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_events)
    RecyclerView recyclerView;
    EventListAdapter listAdapter;
    @BindView(R.id.event_title_textview)
    TextView eventTitleTextView;
    @BindString(R.string.menu_title_share)
    String stringShare;

    public static final String EVENT_TYPE = "event_type";
    public static final String EVENT_TITLE = "event_title";


    public EventListSummaryFragment() {
    }

    public static EventListSummaryFragment newInstance(String eventType, String eventTitle) {
        EventListSummaryFragment fragment = new EventListSummaryFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_TYPE,eventType);
        args.putString(EVENT_TITLE,eventTitle);
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
            presenter.setParams(getArguments().getString(EVENT_TYPE),getArguments().getString(EVENT_TITLE));
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
        listAdapter.updateData(list);
    }

    @Override
    public void onItemClicked(int position, View view) {
        Event event = (Event) view.getTag();
        startActivity(EventDetailActivity.makeIntent(getActivity(),event));
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
                    DialogUtils.shareContent(getActivity(),event.getName()+"\n"+event.getImage(), stringShare);
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public void showProgressIndicator() {
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


    @OnClick(R.id.event_more_button)
    public void onClickMore(){
        startActivity(EventListActivity.makeIntent(getActivity(),presenter.getEventType(),presenter.getEventTitle()));
    }

}
