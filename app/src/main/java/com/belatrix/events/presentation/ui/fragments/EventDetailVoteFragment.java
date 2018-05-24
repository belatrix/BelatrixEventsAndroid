package com.belatrix.events.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Vote;
import com.belatrix.events.presentation.presenters.EventDetailVoteFragmentPresenter;
import com.belatrix.events.presentation.ui.activities.AuthenticatorActivity;
import com.belatrix.events.presentation.ui.adapters.VoteListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;
import com.belatrix.events.utils.Constants;
import com.belatrix.events.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

/**
 * created by dvelasquez
 * modified by lburgos
 */
public class EventDetailVoteFragment extends BelatrixBaseFragment implements EventDetailVoteFragmentPresenter.View, VoteListAdapter.RecyclerViewClickListener {

    private static final int REQ_AUTHENTICATION = 1142;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_textview)
    TextView noDataTextView;
    @BindView(R.id.recycler_programs)
    RecyclerView recyclerView;

    @Inject
    EventDetailVoteFragmentPresenter presenter;

    VoteListAdapter listAdapter;

    @BindString(R.string.app_name)
    String stringTitle;
    @BindString(R.string.dialog_option_participate)
    String stringParticipate;

    private Vote vote;

    public EventDetailVoteFragment() {
    }

    public static Fragment newInstance(Context context, Event event) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EVENT_KEY, event);
        return Fragment.instantiate(context, EventDetailVoteFragment.class.getName(), args);
    }

    @Override
    public void onError(String errorMessage) {
        DialogUtils.createSimpleDialog(getActivity(), stringTitle, errorMessage).show();
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(EventDetailVoteFragment.this);
        presenter.setView(EventDetailVoteFragment.this);
    }

    @Override
    protected void initViews() {
        listAdapter = new VoteListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        if (getActivity() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
        }
        loadData();
    }

    @Override
    public void refreshData() {
        loadData();
    }

    private void showFirstDialog() {
        presenter.updateFirstTime();
        Event event = presenter.getEvent();
        String message = (event.getInteractionText() != null && !event.getInteractionText().isEmpty() ? event.getInteractionText() : "");
        DialogUtils.createSimpleDialog(getActivity(), event.getTitle(), message, stringParticipate, true).show();
    }

    private void loadData() {
        presenter.getProjectList(presenter.getEvent().getId());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(Constants.EVENT_KEY)) {
            presenter.setEvent((Event) getArguments().getParcelable(Constants.EVENT_KEY));
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showVoteList(List<Vote> list) {
        if (presenter.isFirstTime() && presenter.getEvent().isInteractionActive()) {
            showFirstDialog();
        }
        if (noDataTextView != null)
            noDataTextView.setVisibility(View.GONE);
        if (recyclerView != null)
            recyclerView.setVisibility(View.VISIBLE);
        listAdapter.updateData(list);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_vote, container, false);
    }

    @Override
    public void onItemClicked(int position, View view) {
        vote = (Vote) view.getTag();
        presenter.voteForIdea(vote);
    }

    @Override
    public void showEmptyView() {
        if (noDataTextView != null)
            noDataTextView.setVisibility(View.VISIBLE);
        if (recyclerView != null)
            recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressIndicator() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        if (recyclerView != null)
            recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    public void onAuthorizationRequired() {
        startActivityForResult(AuthenticatorActivity.makeIntent(getContext()), REQ_AUTHENTICATION);
    }

    @Override
    public void onVoteError() {
        DialogUtils.createErrorDialog(getActivity(), getString(R.string.vote_error)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_AUTHENTICATION) {
            if (resultCode == Activity.RESULT_OK) {
                if (vote != null) {
                    presenter.voteForIdea(vote);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
