package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ListCandidatesInteractor;
import com.belatrix.events.domain.interactors.ListParticipantsInteractor;
import com.belatrix.events.domain.model.Author;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

public class IdeaDetailPresenter extends BelatrixBasePresenter<IdeaDetailPresenter.View> implements ListParticipantsInteractor.Callback, ListCandidatesInteractor.Callback {

    private final ListParticipantsInteractor mListParticipantsInteractor;
    private final ListCandidatesInteractor mListCandidatesInteractor;
    private final AccountUtils mAccountUtils;
    private Project objProject;

    @Inject
    IdeaDetailPresenter(ListParticipantsInteractor listParticipantsInteractor, ListCandidatesInteractor listCandidatesInteractor, AccountUtils accountUtils) {
        this.mListParticipantsInteractor = listParticipantsInteractor;
        this.mListCandidatesInteractor = listCandidatesInteractor;
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
            mListCandidatesInteractor.getCandidatesListById(IdeaDetailPresenter.this, mAccountUtils.getToken(), objProject.getId());
        }
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

    }

    public void cancelCandidate(Author author) {

    }

    public interface View extends BelatrixBaseView {

        void addParticipant(Author author);

        void clearParticipantContainer();

        void addCandidate(Author author);

        void addCandidateForOwner(Author author);

        void clearCandidateContainer();
    }
}
