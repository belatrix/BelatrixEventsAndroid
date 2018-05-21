package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.ModeratorIdeaDetailPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class ModeratorIdeaDetailFragment extends BelatrixBaseFragment implements ModeratorIdeaDetailPresenter.View, CompoundButton.OnCheckedChangeListener {

    private static final String ARGS_PROJECT = "args_project";

    @BindView(R.id.sw_idea_enable)
    Switch swIdeaEnable;
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
    ModeratorIdeaDetailPresenter mPresenter;

    private LayoutInflater inflater;

    public static Fragment create(Context context, Project project) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PROJECT, project);
        return Fragment.instantiate(context, ModeratorIdeaDetailFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moderator_idea_detail, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(ModeratorIdeaDetailFragment.this);
        mPresenter.setView(ModeratorIdeaDetailFragment.this);
    }

    @Override
    protected void initViews() {
        inflater = LayoutInflater.from(getContext());

        if (getArguments() != null) {
            Bundle args = getArguments();
            if (args.containsKey(ARGS_PROJECT)) {
                Project project = args.getParcelable(ARGS_PROJECT);
                if (project != null) {
                    loadProject(project);
                }
            }
        }
    }

    private void loadProject(Project project) {
        swIdeaEnable.setOnCheckedChangeListener(null);
        tvIdeaTitle.setText(project.getTitle());
        tvIdeaDetail.setText(project.getDescription());
        addAuthor(project.getAuthor());
        swIdeaEnable.setChecked(project.isCompleted());
        mPresenter.setProject(project);
        swIdeaEnable.setTag(project);
        swIdeaEnable.setChecked(project.isValid());
        swIdeaEnable.setText(project.isValid() ? getString(R.string.valid) : getString(R.string.invalid));
        swIdeaEnable.setOnCheckedChangeListener(ModeratorIdeaDetailFragment.this);

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
    public void onApprovedCandidateError() {
        showError(getString(R.string.accept_candidate_server_error));
    }

    @Override
    public void onUnregisterCandidateError() {
        showError(getString(R.string.remove_candidate_server_error));
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
        TextView tvAuthorName = view.findViewById(R.id.id_author_name);
        TextView tvAuthorEmail = view.findViewById(R.id.id_author_email);
        TextView tvAuthorPhone = view.findViewById(R.id.id_author_phone);
        TextView tvAuthorRole = view.findViewById(R.id.id_author_role);
        ImageButton ibCancel = view.findViewById(R.id.ib_candidate_cancel);
        ImageButton ibAccept = view.findViewById(R.id.ib_candidate_ok);

        tvAuthorName.setText(author.getFullName());
        tvAuthorEmail.setText(author.getEmail());
        tvAuthorPhone.setText(author.getPhoneNumber());
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Project project = (Project) buttonView.getTag();
        mPresenter.modifyStatus(project.getId());
    }

    @Override
    public void onValidateSuccessful(Project project) {
        loadProject(project);
    }

    @Override
    public void onValidateError() {

    }
}
