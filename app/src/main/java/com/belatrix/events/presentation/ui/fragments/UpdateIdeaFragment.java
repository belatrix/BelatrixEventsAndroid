package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.UpdateIdeaPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class UpdateIdeaFragment extends BelatrixBaseFragment implements UpdateIdeaPresenter.View {

    public static final String ARGS_PROJECT = "args_project";

    @BindView(R.id.et_idea_title)
    EditText etIdeaTitle;

    @BindView(R.id.et_idea_detail)
    EditText etIdeaDetail;

    @Inject
    UpdateIdeaPresenter mPresenter;

    private Project objProject;

    public static Fragment create(Context context, Project project) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PROJECT, project);
        return Fragment.instantiate(context, UpdateIdeaFragment.class.getName(), args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            objProject = getArguments().getParcelable(ARGS_PROJECT);
        }
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(UpdateIdeaFragment.this);
        mPresenter.setView(UpdateIdeaFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_idea_add, container, false);
    }

    @Override
    protected void initViews() {
        etIdeaTitle.setText(objProject.getTitle());
        etIdeaDetail.setText(objProject.getDescription());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_idea_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                replaceFragment(IdeaDetailFragment.create(getContext(), objProject), false);
                break;
            case R.id.action_accept:
                updateIdea();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateIdea() {
        String title = etIdeaTitle.getText().toString();
        String description = etIdeaDetail.getText().toString();
        if (TextUtils.isEmpty(title)) {
            etIdeaTitle.setError(getString(R.string.field_empty_2));
        }
        if (TextUtils.isEmpty(description)) {
            etIdeaDetail.setError(getString(R.string.field_empty_2));
        }
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && getArguments() != null) {
            mPresenter.updateIdea(objProject.getId(), title, description);
        }
    }

    @Override
    public void updateIdeaCompleted(Project project) {
        replaceFragment(IdeaDetailFragment.create(getContext(), project), false);
    }

    @Override
    public void onUpdateIdeaError() {

    }
}
