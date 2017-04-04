package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.Collaborator;
import com.belatrixsf.events.presentation.presenters.AboutFragmentPresenter;
import com.belatrixsf.events.presentation.ui.adapters.TeamListAdapter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public  class AboutFragment extends BelatrixBaseFragment implements AboutFragmentPresenter.View {

    public static final String COLLABORATORS_KEY = "_collaborators_key";
    @Inject
    AboutFragmentPresenter aboutPresenter;
    private TeamListAdapter collaboratorListAdapter;

    @BindView(R.id.collaborators)
    RecyclerView collaboratorsRecyclerView;

    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        aboutPresenter.setView(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        aboutPresenter.getContacts();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        List<Collaborator> collaborators = savedInstanceState.getParcelableArrayList(COLLABORATORS_KEY);
        aboutPresenter.loadPresenterState(collaborators);
    }

    private void saveState(Bundle outState) {
        List<Collaborator> collaborators = aboutPresenter.getCollaboratorsSync();
        if (collaborators != null && collaborators instanceof ArrayList) {
            outState.putParcelableArrayList(COLLABORATORS_KEY, (ArrayList<Collaborator>) collaborators);
        }
    }

    @Override
    public void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        collaboratorListAdapter = new TeamListAdapter();
        collaboratorsRecyclerView.setAdapter(collaboratorListAdapter);
        collaboratorsRecyclerView.setNestedScrollingEnabled(false);
        collaboratorsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void addContacts(List<Collaborator> collaborators) {
        collaboratorListAdapter.add(collaborators);
    }

    @Override
    public void resetList() {
        collaboratorListAdapter.reset();
    }
}