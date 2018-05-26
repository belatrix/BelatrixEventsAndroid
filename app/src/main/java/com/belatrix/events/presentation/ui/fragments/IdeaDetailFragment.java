package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.IdeaDetailPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.DialogUtils;
import com.belatrix.events.utils.account.AccountUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class IdeaDetailFragment extends BelatrixBaseFragment implements IdeaDetailPresenter.View {

    private static final String ARGS_PROJECT = "args_project";

    @BindView(R.id.tv_idea_title)
    TextView tvIdeaTitle;
    @BindView(R.id.tv_idea_detail)
    TextView tvIdeaDetail;
    @BindView(R.id.fl_author_content)
    FrameLayout flAuthorContent;
    @BindView(R.id.tv_caption_participants)
    TextView tvCaptionParticipants;
    @BindView(R.id.ll_participants_content)
    LinearLayout llParticipantsContent;
    @BindView(R.id.tv_caption_candidates)
    TextView tvCaptionCandidates;
    @BindView(R.id.ll_candidates_content)
    LinearLayout llCandidatesContent;

    @Inject
    IdeaDetailPresenter mPresenter;

    @Inject
    AccountUtils mAccountUtils;

    private LayoutInflater inflater;
    private Project project;
    private boolean isRegistered;
    private boolean isCandidate;
    private MenuItem menuItem;

    public static Fragment create(Context context, Project project) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PROJECT, project);
        return Fragment.instantiate(context, IdeaDetailFragment.class.getName(), args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_idea_detail, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(IdeaDetailFragment.this);
        mPresenter.setView(IdeaDetailFragment.this);
    }

    @Override
    protected void initViews() {
        inflater = LayoutInflater.from(getContext());
        if (getArguments() != null) {
            Bundle args = getArguments();
            if (args.containsKey(ARGS_PROJECT)) {
                project = args.getParcelable(ARGS_PROJECT);
                if (project != null) {
                    tvIdeaTitle.setText(project.getTitle());
                    tvIdeaDetail.setText(project.getDescription());
                    addAuthor(project.getAuthor());
                    mPresenter.setProject(project);
                }
            } else {
                getActivity().finish();
            }
        }
    }

    @Override
    public void addParticipant(Author author) {
        if (tvCaptionParticipants.getVisibility() == View.GONE) {
            tvCaptionParticipants.setVisibility(View.VISIBLE);
        }
        addParticipant(author, llParticipantsContent);
    }

    @Override
    public void clearParticipantContainer() {
        tvCaptionParticipants.setVisibility(View.GONE);
        llParticipantsContent.removeAllViews();
    }

    @Override
    public void addCandidateForOwner(Author author) {
        if (tvCaptionCandidates.getVisibility() == View.GONE) {
            tvCaptionCandidates.setVisibility(View.VISIBLE);
        }
        addCandidateForOwner(author, llCandidatesContent);
    }

    @Override
    public void clearCandidateContainer() {
        tvCaptionCandidates.setVisibility(View.GONE);
        llCandidatesContent.removeAllViews();
    }

    @Override
    public void onRegisteredAsCandidate() {
        showSnackBar(getString(R.string.request_join_successful));
        menuItem.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel));
        menuItem.setTitle(R.string.request_cancel);
        isCandidate = true;
    }

    @Override
    public void onCandidateError() {
        showError(getString(R.string.request_join_error));
    }

    @Override
    public void onApprovedCandidateError() {
        showError(getString(R.string.accept_candidate_server_error));
    }

    @Override
    public void onUnregisterCandidateError() {
        showError(getString(R.string.remove_candidate_server_error));
    }

    @Override
    public void userIsCandidate(boolean isCandidate) {
        this.isCandidate = isCandidate;
        if (isCandidate && menuItem != null) {
            menuItem.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel));
            menuItem.setTitle(R.string.request_cancel);
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_group_add));
            menuItem.setTitle(R.string.request_join);
        }
    }

    @Override
    public void userIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
        if (isRegistered && menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    private void addAuthor(Author author) {
        if (TextUtils.isEmpty(author.getPhoneNumber())) {
            addParticipant(author, flAuthorContent);
        } else {
            addAuthor(author, flAuthorContent);
        }
    }

    private void addAuthor(Author author, ViewGroup container) {
        View view = inflater.inflate(R.layout.item_author, container, false);
        TextView tvAuthorName = view.findViewById(R.id.id_author_name);
        TextView tvAuthorEmail = view.findViewById(R.id.id_author_email);
        TextView tvAuthorPhone = view.findViewById(R.id.id_author_phone);
        TextView tvAuthorRole = view.findViewById(R.id.id_author_role);
        tvAuthorName.setText(author.getFullName());
        tvAuthorEmail.setText(author.getEmail());
        tvAuthorPhone.setText(author.getPhoneNumber());
        if (author.getRole() != null) {
            tvAuthorRole.setText(author.getRole().getName());
        }
        container.addView(view);
    }

    private void addCandidateForOwner(Author author, ViewGroup container) {
        View view = inflater.inflate(R.layout.item_candidate_owner, container, false);
        TextView tvAuthorName = view.findViewById(R.id.id_participant_name);
        TextView tvAuthorEmail = view.findViewById(R.id.id_participant_email);
        TextView tvAuthorRole = view.findViewById(R.id.id_participant_role);
        ImageButton ibCancel = view.findViewById(R.id.ib_candidate_cancel);
        ImageButton ibAccept = view.findViewById(R.id.ib_candidate_ok);

        tvAuthorName.setText(author.getFullName());
        tvAuthorEmail.setText(author.getEmail());
        if (author.getRole() != null) {
            tvAuthorRole.setText(author.getRole().getName());
        }
        ibCancel.setTag(author);
        ibAccept.setTag(author);

        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Author author = (Author) v.getTag();
                mPresenter.cancelCandidate(author.getId());
            }
        });
        ibAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Author author = (Author) v.getTag();
                mPresenter.acceptCandidate(author.getId());
            }
        });

        container.addView(view);
    }

    private void addParticipant(Author author, ViewGroup container) {
        View view = inflater.inflate(R.layout.item_participant, container, false);
        TextView tvAuthorName = view.findViewById(R.id.id_participant_name);
        TextView tvAuthorEmail = view.findViewById(R.id.id_participant_email);
        TextView tvAuthorRole = view.findViewById(R.id.id_participant_role);
        tvAuthorName.setText(author.getFullName());
        tvAuthorEmail.setText(author.getEmail());
        if (author.getRole() != null) {
            tvAuthorRole.setText(author.getRole().getName());
        }
        container.addView(view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mPresenter.isOwner()) {
            inflater.inflate(R.menu.menu_idea_owner, menu);
        } else if (!project.isCompleted() && mAccountUtils.existsAccount()) {
            inflater.inflate(R.menu.menu_idea_participant, menu);
            menuItem = menu.findItem(R.id.action_request_join);
            if (isRegistered) {
                menuItem.setVisible(false);
            } else if (isCandidate) {
                menuItem.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel));
                menuItem.setTitle(R.string.request_cancel);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                replaceFragment(UpdateIdeaFragment.create(getContext(), project), false);
                return false;
            case R.id.action_request_join:
                if (isCandidate) {
                    DialogUtils.createConfirmationDialogWithTitle(getActivity(),
                            getString(R.string.cancel_request_dialog_title),
                            getString(R.string.cancel_request_dialog_content),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.cancelCandidate(mAccountUtils.getUserId());
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    DialogUtils.createConfirmationDialogWithTitle(getActivity(),
                            getString(R.string.request_join_dialog_title),
                            getString(R.string.request_join_dialog_content),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.requestToJoin();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void showProgressIndicator() {
        showProgressDialog();
    }

    @Override
    public void hideProgressIndicator() {
        dismissProgressDialog();
    }
}
