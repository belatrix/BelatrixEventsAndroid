package com.belatrixsf.events.presentation.ui.fragments;

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
import com.belatrixsf.events.di.component.ApplicationComponent;
import com.belatrixsf.events.di.module.EventDetailVoteModule;
import com.belatrixsf.events.domain.model.Project;
import com.belatrixsf.events.presentation.presenters.EventDetailVotePresenter;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.adapters.ProjectListAdapter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.presentation.ui.common.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

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

    public EventDetailVoteFragment() {
    }

    public static EventDetailVoteFragment newInstance(int eventId) {
        EventDetailVoteFragment fragment = new EventDetailVoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EventDetailActivity.EVENT_ID_KEY, eventId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(ApplicationComponent applicationComponent) {
        applicationComponent.loadModule(new EventDetailVoteModule(this)).inject(this);
    }

    @Override
    protected void initViews() {
        listAdapter = new ProjectListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        presenter.getProjectList(1);
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
        //Project project = (Project) view.getTag();
        //startActivity(EventDetailActivity.makeIntent(getActivity(),position));
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
}
