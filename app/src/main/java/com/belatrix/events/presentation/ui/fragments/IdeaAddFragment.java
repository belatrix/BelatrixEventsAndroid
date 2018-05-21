package com.belatrix.events.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import com.belatrix.events.presentation.presenters.IdeaAddPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.DialogUtils;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

public class IdeaAddFragment extends BelatrixBaseFragment implements IdeaAddPresenter.View {

    private static final String ARGS_EVENT_ID = "args_event_id";

    @BindView(R.id.et_idea_title)
    EditText etIdeaTitle;

    @BindView(R.id.et_idea_detail)
    EditText etIdeaDetail;

    @BindString(R.string.new_idea)
    String sNewIdeaTitle;

    @BindString(R.string.new_idea_dialog)
    String sNewIdeaContent;

    @Inject
    IdeaAddPresenter mIdeaAddPresenter;

    public static Fragment create(Context context, int eventId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_EVENT_ID, eventId);
        return Fragment.instantiate(context, IdeaAddFragment.class.getName(), args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_idea_add, container, false);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(IdeaAddFragment.this);
        mIdeaAddPresenter.setView(IdeaAddFragment.this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_idea_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accept:
                onAcceptPressed();
                break;
            case R.id.action_cancel:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onAcceptPressed() {
        DialogUtils.createConfirmationDialogWithTitle(getActivity(), sNewIdeaTitle, sNewIdeaContent, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createIdea();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private void createIdea() {
        String title = etIdeaTitle.getText().toString();
        String description = etIdeaDetail.getText().toString();
        if (TextUtils.isEmpty(title)) {
            etIdeaTitle.setError(getString(R.string.field_empty_2));
        }
        if (TextUtils.isEmpty(description)) {
            etIdeaDetail.setError(getString(R.string.field_empty_2));
        }
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && getArguments() != null) {
            int eventId = getArguments().getInt(ARGS_EVENT_ID);
            mIdeaAddPresenter.createIdea(eventId, title, description);
        }
    }

    @Override
    public void onIdeaCreated() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onIdeaError() {

    }

    @Override
    public void authenticationFailed() {
        showSnackBar(getString(R.string.idea_create_authenticator_error));
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public Context getContext() {
        return getContext();
    }
}
