package com.belatrix.events.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.belatrix.events.presentation.ui.activities.AuthenticatorActivity;
import com.belatrix.events.presentation.ui.activities.IdeaAddActivity;
import com.belatrix.events.presentation.ui.activities.IdeaDetailActivity;
import com.belatrix.events.presentation.ui.adapters.IdeaListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class MyIdeasFragment extends BelatrixBaseFragment implements MyIdeasPresenter.View, IdeaListAdapter.RecyclerViewClickListener {

    private static final int REQ_AUTHENTICATION = 3342;
    private static final int REQ_CREATE_IDEA = 2242;

    private static final String ARGS_EVENT_ID = "args_event_id";

    @BindView(R.id.rv_ideas)
    RecyclerView rvIdeas;

    @BindString(R.string.idea_created)
    String stringIdeaCreated;

    @Inject
    MyIdeasPresenter mPresenter;

    @Inject
    AccountUtils mAccountUtils;

    private IdeaListAdapter mAdapter;
    private int eventId;

    public static Fragment create(Context context, int eventId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_EVENT_ID, eventId);
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
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARGS_EVENT_ID)) {
            eventId = args.getInt(ARGS_EVENT_ID);
            mPresenter.listMyIdeas(eventId);
        }
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
        startActivity(IdeaDetailActivity.makeIntent(getContext(), project, false));
    }

    @OnClick(R.id.fab_add_idea)
    void onAddIdeaClick() {
        if (mAccountUtils.existsAccount()) {
            Intent intent = IdeaAddActivity.makeIntent(getContext(), eventId);
            startActivityForResult(intent, REQ_CREATE_IDEA);
        } else {
            startActivityForResult(AuthenticatorActivity.makeIntent(getContext()), REQ_AUTHENTICATION);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_AUTHENTICATION:
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = IdeaAddActivity.makeIntent(getContext(), eventId);
                    startActivityForResult(intent, REQ_CREATE_IDEA);
                }
                break;
            case REQ_CREATE_IDEA:
                if (resultCode == Activity.RESULT_OK) {
                    mPresenter.listMyIdeas(eventId);
                    showSnackBar(stringIdeaCreated);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
