package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ListMeetingInteractor;
import com.belatrix.events.domain.model.Meeting;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.List;

import javax.inject.Inject;

public class RegisterAssistancePresenter extends BelatrixBasePresenter<RegisterAssistancePresenter.View> implements ListMeetingInteractor.Callback {

    private final ListMeetingInteractor mListMeetingInteractor;
    private final AccountUtils mAccountUtils;

    @Inject
    RegisterAssistancePresenter(ListMeetingInteractor listMeetingInteractor, AccountUtils accountUtils) {
        this.mListMeetingInteractor = listMeetingInteractor;
        this.mAccountUtils = accountUtils;
    }

    public void loadEventsForModerator() {
        mListMeetingInteractor.listMeetings(RegisterAssistancePresenter.this, mAccountUtils.getToken());
    }

    @Override
    public void cancelRequests() {
        mListMeetingInteractor.cancel();
    }

    @Override
    public void onListEventsSuccessful(List<Meeting> lst) {
        view.loadEventsSuccessful(lst);
    }

    @Override
    public void onListEventsError() {
        view.loadEventsError();
    }

    public interface View extends BelatrixBaseView {
        void loadEventsSuccessful(List<Meeting> result);

        void loadEventsError();
    }
}
