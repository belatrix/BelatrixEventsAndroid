package com.belatrixsf.events.presentation.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.domain.model.Project;
import com.belatrixsf.events.presentation.presenters.EventDetailVotePresenter;
import com.belatrixsf.events.presentation.presenters.ManageEventListPresenter;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.adapters.EventListAdapter;
import com.belatrixsf.events.presentation.ui.adapters.ProjectListAdapter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.presentation.ui.common.DividerItemDecoration;
import com.belatrixsf.events.presentation.ui.common.SwipeRecyclerView;
import com.belatrixsf.events.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * created by dvelasquez
 */
public class ManageEventListFragment extends BelatrixBaseFragment implements ManageEventListPresenter.View, EventListAdapter.RecyclerViewClickListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_programs)
    SwipeRecyclerView recyclerView;
    @Inject
    ManageEventListPresenter presenter;
    EventListAdapter listAdapter;
    @BindString(R.string.app_name)
    String stringTitle;
    @BindString(R.string.dialog_confirmation_remove_event)
    String stringMessage;

    public ManageEventListFragment() {
    }

    public static ManageEventListFragment newInstance() {
        ManageEventListFragment fragment = new ManageEventListFragment();
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


    @OnClick(R.id.add_event)
    public void onClickAddEvent(){
    }

    @Override
    protected void initViews() {
        listAdapter = new EventListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        recyclerView.addSwipeListener(ItemTouchHelper.LEFT, new SwipeRecyclerView.OnSwipeListener() {
            @Override
            public void onSwipeToRight(int position) {
            }

            @Override
            public void onSwipeToLeft(final int position) {
                DialogUtils.createConfirmationDialogWithTitle(getActivity(), stringTitle, stringMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listAdapter.notifyItemRemoved(position);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listAdapter.notifyItemChanged(position);
                    }
                }).show();


            }
        });
        presenter.getEventList();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_event_list, container, false);
    }


    @Override
    public void onItemClicked(int position, View view) {
        final Event event = (Event) view.getTag();
        /*
        DialogUtils.createConfirmationDialogWithTitle(getActivity(), stringTitle, getString(R.string.event_dialog_confirm_vote, project.getName()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.voteForProject(project.getId());
            }
        }, null).show();
        */
    }

    @Override
    public void showEventList(List<Event> list) {
        listAdapter.updateData(list);
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressIndicator() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }
}
