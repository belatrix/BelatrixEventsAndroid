package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.IdeaAddPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.utils.DialogUtils;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

public class IdeaAddActivity extends BelatrixBaseActivity implements IdeaAddPresenter.View {

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

    private boolean ideaWasCreated = false;

    public static Intent makeIntent(Context context, int eventId) {
        Intent intent = new Intent(context, IdeaAddActivity.class);
        intent.putExtra(ARGS_EVENT_ID, eventId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_add);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(IdeaAddActivity.this);
        mIdeaAddPresenter.setView(IdeaAddActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_idea_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accept:
                onAcceptPressed();
                break;
            case R.id.action_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onAcceptPressed() {
        DialogUtils.createConfirmationDialogWithTitle(IdeaAddActivity.this, sNewIdeaTitle, sNewIdeaContent, new DialogInterface.OnClickListener() {
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
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && getIntent() != null && getIntent().getExtras() != null) {
            int eventId = getIntent().getExtras().getInt(ARGS_EVENT_ID);
            mIdeaAddPresenter.createIdea(eventId, title, description);
        }
    }

    @Override
    public void onIdeaCreated() {
        ideaWasCreated = true;
        setResult(RESULT_OK);
        finish();
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
        return IdeaAddActivity.this;
    }

    @Override
    public boolean isFinishing() {
        if (!ideaWasCreated) {
            setResult(RESULT_CANCELED);
        }
        return super.isFinishing();
    }
}
