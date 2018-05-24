package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.presentation.presenters.SearchUserPresenter;
import com.belatrix.events.presentation.ui.adapters.UserAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;
import com.belatrix.events.utils.DialogUtils;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchUserFragment extends BelatrixBaseFragment implements SearchUserPresenter.View, UserAdapter.OnItemPressListener, SearchView.OnQueryTextListener {

    private static final String ARGS_PROJECT = "args_project";

    @BindView(R.id.sv_user)
    SearchView svUser;
    @BindView(R.id.rv_users)
    RecyclerView rvUsers;

    @Inject
    SearchUserPresenter mPresenter;

    private UserAdapter mUserAdapter;
    private Project project;

    public static Fragment create(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, SearchUserFragment.class.getName(), args);
    }

    public static Fragment create(Context context, Project project) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PROJECT, project);
        return Fragment.instantiate(context, SearchUserFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_user, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(SearchUserFragment.this);
        mPresenter.setView(SearchUserFragment.this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.menu_title_search_user));
        mUserAdapter = new UserAdapter(SearchUserFragment.this);
        rvUsers.setAdapter(mUserAdapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), android.R.drawable.divider_horizontal_bright)));
        svUser.setOnQueryTextListener(SearchUserFragment.this);

        Bundle args = getArguments();
        if (args != null) {
            project = args.getParcelable(ARGS_PROJECT);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query) && query.length() > 2) {
            mPresenter.searchUser(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void listUsers(List<User> users) {
        mUserAdapter.addAll(users);
    }

    @Override
    public void onSearchError() {
        DialogUtils.createErrorDialog(getActivity(), getString(R.string.dialog_server_error));
    }

    @Override
    public void onItemPressed(final User user) {
        if (project == null) {
            replaceFragment(ProfileFragment.create(getContext(), user), true);
        } else {
            DialogUtils.createConfirmationDialogWithTitle(getActivity(),
                    getString(R.string.add_participant),
                    String.format(Locale.getDefault(), getString(R.string.add_participant_content), user.getFullName()),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.addParticipant(user.getId(), project.getId());
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void onParticipantAdded() {
        replaceFragment(ModeratorIdeaDetailFragment.create(getContext(), project), false);
    }

    @Override
    public void onErrorParticipant() {
        DialogUtils.createErrorDialog(getActivity(), getString(R.string.dialog_server_error));
    }
}
