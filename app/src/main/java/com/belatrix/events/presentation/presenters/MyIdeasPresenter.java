package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.ListMyIdeasInteractor;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import java.util.List;

import javax.inject.Inject;

public class MyIdeasPresenter extends BelatrixBasePresenter<MyIdeasPresenter.View> implements ListMyIdeasInteractor.Callback {

    private final ListMyIdeasInteractor mListMyIdeasInteractor;

    @Inject
    public MyIdeasPresenter(ListMyIdeasInteractor listMyIdeasInteractor) {
        this.mListMyIdeasInteractor = listMyIdeasInteractor;
    }

    public void listMyIdeas() {
        mListMyIdeasInteractor.listMyIdeas(MyIdeasPresenter.this);
    }

    @Override
    public void onLoadListMyIdeas(List<Project> result) {
        view.onLoadListIdeas(result);
    }

    @Override
    public void onErrorListMyIdeas() {
        view.onErrorListIdeas();
    }

    @Override
    public void cancelRequests() {
        mListMyIdeasInteractor.cancel();
    }

    public interface View extends BelatrixBaseView {
        void onLoadListIdeas(List<Project> result);

        void onErrorListIdeas();
    }
}
