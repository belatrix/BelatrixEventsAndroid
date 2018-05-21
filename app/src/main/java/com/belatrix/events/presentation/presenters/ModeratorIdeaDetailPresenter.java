package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ApproveCandidateInteractor;
import com.belatrix.events.domain.interactors.ListCandidatesInteractor;
import com.belatrix.events.domain.interactors.ListParticipantsInteractor;
import com.belatrix.events.domain.interactors.UnregisterCandidateInteractor;
import com.belatrix.events.domain.interactors.ValidateIdeaInteractor;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

public class ModeratorIdeaDetailPresenter extends BelatrixBasePresenter<ModeratorIdeaDetailPresenter.View> implements ListParticipantsInteractor.Callback, ListCandidatesInteractor.Callback, ApproveCandidateInteractor.Callback, UnregisterCandidateInteractor.Callback, ValidateIdeaInteractor.Callback {

    private final ListParticipantsInteractor mListParticipantsInteractor;
    private final ListCandidatesInteractor mListCandidatesInteractor;
    private final ApproveCandidateInteractor mApproveCandidateInteractor;
    private final UnregisterCandidateInteractor mUnregisterCandidateInteractor;
    private final ValidateIdeaInteractor mValidateIdeaInteractor;
    private final AccountUtils mAccountUtils;
    private Project objProject;

    @Inject
    ModeratorIdeaDetailPresenter(ListParticipantsInteractor listParticipantsInteractor, ListCandidatesInteractor listCandidatesInteractor, ApproveCandidateInteractor approveCandidateInteractor, UnregisterCandidateInteractor unregisterCandidateInteractor, ValidateIdeaInteractor validateIdeaInteractor, AccountUtils accountUtils) {
        this.mListParticipantsInteractor = listParticipantsInteractor;
        this.mListCandidatesInteractor = listCandidatesInteractor;
        this.mApproveCandidateInteractor = approveCandidateInteractor;
        this.mUnregisterCandidateInteractor = unregisterCandidateInteractor;
        this.mValidateIdeaInteractor = validateIdeaInteractor;
        this.mAccountUtils = accountUtils;
    }

    @Override
    public void cancelRequests() {
        mListParticipantsInteractor.cancel();
    }

    public void setProject(Project project) {
        this.objProject = project;
        mListParticipantsInteractor.getParticipantsListById(ModeratorIdeaDetailPresenter.this, objProject.getId());
        mListCandidatesInteractor.getCandidatesListById(ModeratorIdeaDetailPresenter.this, mAccountUtils.getToken(), objProject.getId());
    }

    @Override
    public void onCandidatesSuccess(boolean isCandidate, List<Author> result) {
        view.clearCandidateContainer();
        for (Author author : result) {
            view.addCandidateForOwner(author);
        }
    }

    @Override
    public void onParticipantsSuccess(boolean isRegistered, List<Author> result) {
        view.clearParticipantContainer();
        for (Author author : result) {
            view.addParticipant(author);
        }
    }

    @Override
    public void onCandidatesError() {

    }

    @Override
    public void onParticipantsError() {

    }

    public void acceptCandidate(int userId) {
        mApproveCandidateInteractor.approveCandidate(ModeratorIdeaDetailPresenter.this, objProject.getId(), userId);
    }

    public void cancelCandidate(int userId) {
        mUnregisterCandidateInteractor.unregisterCandidate(ModeratorIdeaDetailPresenter.this, objProject.getId(), userId);
    }

    @Override
    public void onApprovedCandidate(List<Author> lst, boolean isCandidate) {
        mListParticipantsInteractor.getParticipantsListById(ModeratorIdeaDetailPresenter.this, objProject.getId());
        view.clearCandidateContainer();
        for (Author author : lst) {
            view.addCandidateForOwner(author);
        }
    }

    @Override
    public void onApprovedCandidateError() {
        view.onApprovedCandidateError();
    }

    @Override
    public void onUnregisterCandidate(List<Author> lst, boolean isCandidate) {
        mListParticipantsInteractor.getParticipantsListById(ModeratorIdeaDetailPresenter.this, objProject.getId());
        view.clearCandidateContainer();
        for (Author author : lst) {
            view.addCandidateForOwner(author);
        }
    }

    public void modifyStatus(int ideaId) {
        mValidateIdeaInteractor.validateIdea(ModeratorIdeaDetailPresenter.this, ideaId);
    }

    @Override
    public void onValidatedSuccessful(Project project) {
        view.onValidateSuccessful(project);
    }

    @Override
    public void onValidatedError() {
        view.onValidateError();
    }

    @Override
    public void onUnregisterCandidateError() {
        view.onUnregisterCandidateError();
    }

    public interface View extends BelatrixBaseView {

        void addParticipant(Author author);

        void clearParticipantContainer();

        void addCandidateForOwner(Author author);

        void clearCandidateContainer();

        void onApprovedCandidateError();

        void onUnregisterCandidateError();

        void onValidateSuccessful(Project project);

        void onValidateError();
    }
}
