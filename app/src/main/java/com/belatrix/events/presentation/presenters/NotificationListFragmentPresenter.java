package com.belatrix.events.presentation.presenters;

import com.belatrix.events.domain.interactors.GetNotificationListInteractor;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by raulrashuaman on 4/11/17.
 */

public class NotificationListFragmentPresenter extends BelatrixBasePresenter<NotificationListFragmentPresenter.View> {

    public interface View extends BelatrixBaseView {
        void showNotificationList(List<Notification> list);
        void showEmptyView();
    }

    GetNotificationListInteractor getNotificationListInteractor;

    @Inject
    Cache cache;

    @Inject
    public NotificationListFragmentPresenter(GetNotificationListInteractor interactor) {
        this.getNotificationListInteractor = interactor;
    }

    public void actionGetNotificationList() {
        view.showProgressIndicator();
        getNotificationListInteractor.getNotificationList(new GetNotificationListInteractor.CallBack() {
            @Override
            public void onSuccess(List<Notification> result) {
                view.hideProgressIndicator();
                if (result!= null && !result.isEmpty()) {
                    view.showNotificationList(result);
                } else {
                    view.showEmptyView();
                }
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.showEmptyView();
            }
        }, GetNotificationListInteractor.Params.forCity(cache.getCity()));
    }

    @Override
    public void cancelRequests() {
        getNotificationListInteractor.cancel();
    }
}
