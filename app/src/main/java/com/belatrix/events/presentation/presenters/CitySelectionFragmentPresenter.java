package com.belatrix.events.presentation.presenters;

import com.belatrix.events.R;
import com.belatrix.events.domain.interactors.GetCityListInteractor;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.cache.Cache;

import java.util.List;

import javax.inject.Inject;


public class CitySelectionFragmentPresenter extends BelatrixBasePresenter<CitySelectionFragmentPresenter.View> {

    private Integer selectedCity;
    @Inject
    Cache cache;

    public void setSelectedCity(Integer id) {
        selectedCity = id;
    }

    public Integer getSelectedCity() {
        return selectedCity;
    }

    public interface View extends BelatrixBaseView {
        void showCityList(List<City> list);
        void onCityError(String errorMessage);
    }

    GetCityListInteractor getCityListInteractor;

    @Inject
    public CitySelectionFragmentPresenter(GetCityListInteractor interactor) {
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

    public void actionSaveCityId(){
        cache.saveCity(getSelectedCity());
    }

    public void actionClearCity(){
        cache.removeCity();
    }

    @Override
    public void cancelRequests() {
        getCityListInteractor.cancel();
    }
}
