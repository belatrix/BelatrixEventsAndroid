package com.belatrixsf.events.presentation.presenters;

import com.belatrixsf.events.R;
import com.belatrixsf.events.domain.interactors.GetCityListInteractor;
import com.belatrixsf.events.domain.model.City;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrixsf.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrixsf.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;


public class SettingsFragmentPresenter extends BelatrixBasePresenter<SettingsFragmentPresenter.View> {

    @Inject
    Cache cache;

    public interface View extends BelatrixBaseView {
        void showCityList(List<City> list);
        void onCityError(String errorMessage);
    }

    GetCityListInteractor getCityListInteractor;

    @Inject
    public SettingsFragmentPresenter(GetCityListInteractor interactor) {
        this.getCityListInteractor = interactor;
    }

    public void actionLoadCities() {
        view.showProgressIndicator();
        getCityListInteractor.execute(new GetCityListInteractor.CallBack() {
            @Override
            public void onSuccess(List<City> result) {
                view.hideProgressIndicator();
                view.showCityList(result);
            }

            @Override
            public void onError() {
                view.hideProgressIndicator();
                view.onCityError(view.getContext().getString(R.string.dialog_title_error));
            }
        });
    }

    public void actionSaveCityId(Integer cityId){
        cache.saveCity(cityId);
    }

    public void actionClearCity(){
        cache.removeCity();
    }

    public Integer getCityId(){
        return cache.getCity();
    }

    @Override
    public void cancelRequests() {
        getCityListInteractor.cancel();
    }
}
