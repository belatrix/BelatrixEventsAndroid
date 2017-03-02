package com.belatrixsf.events.presentation.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.adapters.ProjectListAdapter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.presentation.ui.common.DividerItemDecoration;
import com.belatrixsf.events.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

/**
 * created by dvelasquez
 */
public class EventDetailVoteFragment extends BelatrixBaseFragment implements EventDetailVotePresenter.View, ProjectListAdapter.RecyclerViewClickListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_programs)
    RecyclerView recyclerView;
    @Inject
    EventDetailVotePresenter presenter;
    ProjectListAdapter listAdapter;
    @BindString(R.string.app_name)
    String stringTitle;

    Event event;

    public EventDetailVoteFragment() {
    }

    public static EventDetailVoteFragment newInstance(Event event) {
        EventDetailVoteFragment fragment = new EventDetailVoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EventDetailActivity.EVENT_KEY, event);
        fragment.setArguments(bundle);
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
        listAdapter = new ProjectListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        presenter.getProjectList(event.getId());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        event = getArguments().getParcelable(EventDetailActivity.EVENT_KEY);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showProjectList(List<Project> list) {
        listAdapter.updateData(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_vote, container, false);
    }


    @Override
    public void onItemClicked(int position, View view) {
        final Project project = (Project) view.getTag();
        DialogUtils.createConfirmationDialogWithTitle(getActivity(), stringTitle, getString(R.string.event_dialog_confirm_vote, project.getName()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.voteForProject(project.getId());
            }
        }, null).show();
    }

    @Override
    public void onVoteFail(String errorMessage) {

    }


    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoDataView() {
        noDataTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onVoteSuccessful() {
        listAdapter.showVotes();
        presenter.getProjectListOrdered(event.getId());
        //show message / toast / etc
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
