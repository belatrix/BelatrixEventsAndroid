package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.MyIdeasPresenter;
import com.belatrix.events.presentation.ui.activities.IdeaDetailActivity;
import com.belatrix.events.presentation.ui.adapters.IdeaListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MyIdeasFragment extends BelatrixBaseFragment implements MyIdeasPresenter.View, IdeaListAdapter.RecyclerViewClickListener {

    @BindView(R.id.rv_ideas)
    RecyclerView rvIdeas;

    @Inject
    MyIdeasPresenter mPresenter;

    private IdeaListAdapter mAdapter;

    public static Fragment create(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, MyIdeasFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_ideas, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(MyIdeasFragment.this);
        mPresenter.setView(MyIdeasFragment.this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.menu_title_my_ideas));
        mAdapter = new IdeaListAdapter(MyIdeasFragment.this);
        rvIdeas.setAdapter(mAdapter);
        rvIdeas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIdeas.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), android.R.drawable.divider_horizontal_bright)));
        mPresenter.listMyIdeas();
    }

    @Override
    public void onLoadListIdeas(List<Project> result) {
        mAdapter.updateData(result);
    }

    @Override
    public void onErrorListIdeas() {

    }

    @Override
    public void onItemClicked(int position, View view) {
        Project project = (Project) view.getTag();
        startActivity(IdeaDetailActivity.makeIntent(getContext(), project));
    }
}
