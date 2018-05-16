package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ApproveCandidateInteractor;
import com.belatrix.events.domain.interactors.ListCandidatesInteractor;
import com.belatrix.events.domain.interactors.ListParticipantsInteractor;
import com.belatrix.events.domain.interactors.RegisterCandidateInteractor;
import com.belatrix.events.domain.interactors.UnregisterCandidateInteractor;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

public class IdeaDetailPresenter extends BelatrixBasePresenter<IdeaDetailPresenter.View> implements ListParticipantsInteractor.Callback, ListCandidatesInteractor.Callback, ApproveCandidateInteractor.Callback, UnregisterCandidateInteractor.Callback, RegisterCandidateInteractor.Callback {

    private final ListParticipantsInteractor mListParticipantsInteractor;
    private final ListCandidatesInteractor mListCandidatesInteractor;
    private final ApproveCandidateInteractor mApproveCandidateInteractor;
    private final RegisterCandidateInteractor mRegisterCandidateInteractor;
    private final UnregisterCandidateInteractor mUnregisterCandidateInteractor;
    private final AccountUtils mAccountUtils;
    private Project objProject;
    private boolean isOwner;

    @Inject
    IdeaDetailPresenter(ListParticipantsInteractor listParticipantsInteractor, ListCandidatesInteractor listCandidatesInteractor, ApproveCandidateInteractor approveCandidateInteractor, RegisterCandidateInteractor registerCandidateInteractor, UnregisterCandidateInteractor unregisterCandidateInteractor, AccountUtils accountUtils) {
        this.mListParticipantsInteractor = listParticipantsInteractor;
        this.mListCandidatesInteractor = listCandidatesInteractor;
        this.mApproveCandidateInteractor = approveCandidateInteractor;
        this.mRegisterCandidateInteractor = registerCandidateInteractor;
        this.mUnregisterCandidateInteractor = unregisterCandidateInteractor;
        this.mAccountUtils = accountUtils;
    }

    @Override
    public void cancelRequests() {
        mListParticipantsInteractor.cancel();
    }

    public void setProject(Project project) {
        this.objProject = project;
        mListParticipantsInteractor.getParticipantsListById(IdeaDetailPresenter.this, objProject.getId());
        if (mAccountUtils.existsAccount()) {
            isOwner = mAccountUtils.getUserId() == project.getAuthor().getId();
            if (isOwner && !project.isCompleted()) {
                mListCandidatesInteractor.getCandidatesListById(IdeaDetailPresenter.this, mAccountUtils.getToken(), objProject.getId());
            }
        }
    }

    public boolean isOwner() {
        return isOwner;
    }

    @Override
    public void onCandidatesSuccess(List<Author> result) {
        view.clearCandidateContainer();
        if (mAccountUtils.existsAccount()) {
            if (mAccountUtils.getUserId() == objProject.getAuthor().getId()) {
                for (Author author : result) {
                    view.addCandidateForOwner(author);
                }
            }
        } else {
            for (Author author : result) {
                view.addCandidate(author);
            }
        }
    }

    @Override
    public void onCandidatesError() {

    }

    @Override
    public void onParticipantsSuccess(List<Author> result) {
        view.clearParticipantContainer();
        for (Author author : result) {
            view.addParticipant(author);
        }
    }

    @Override
    public void onParticipantsError() {

    }

    public void acceptCandidate(Author author) {
        mApproveCandidateInteractor.approveCandidate(IdeaDetailPresenter.this, objProject.getId(), author.getId());
    }

    public void cancelCandidate(Author author) {
        mUnregisterCandidateInteractor.unregisterCandidate(IdeaDetailPresenter.this, objProject.getId(), author.getId());
    }

    @Override
    public void onApprovedCandidate() {
        mListParticipantsInteractor.getParticipantsListById(IdeaDetailPresenter.this, objProject.getId());
        mListCandidatesInteractor.getCandidatesListById(IdeaDetailPresenter.this, mAccountUtils.getToken(), objProject.getId());
    }

    @Override
    public void onApprovedCandidateError() {
        view.onApprovedCandidateError();
    }

    @Override
    public void onUnregisterCandidate() {
        mListParticipantsInteractor.getParticipantsListById(IdeaDetailPresenter.this, objProject.getId());
        mListCandidatesInteractor.getCandidatesListById(IdeaDetailPresenter.this, mAccountUtils.getToken(), objProject.getId());
    }

    @Override
    public void onUnregisterCandidateError() {
        view.onUnregisterCandidateError();
    }

    public void requestToJoin() {
        view.showProgressDialog();
        mRegisterCandidateInteractor.registerCandidate(IdeaDetailPresenter.this, objProject.getId());
    }

    @Override
    public void onRegisterCandidate() {
        view.dismissProgressDialog();
        view.onRegisteredAsCandidate();
    }

    @Override
    public void onRegisterCandidateError() {
        view.dismissProgressDialog();
        view.onCandidateError();
    }

    public interface View extends BelatrixBaseView {

        void addParticipant(Author author);

        void clearParticipantContainer();

        void addCandidate(Author author);

        void addCandidateForOwner(Author author);

        void clearCandidateContainer();

        void onRegisteredAsCandidate();

        void onCandidateError();

        void onApprovedCandidateError();

        void onUnregisterCandidateError();
    }
}
