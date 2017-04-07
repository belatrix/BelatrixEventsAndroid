package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.City;
import com.belatrixsf.events.presentation.presenters.CitySelectionFragmentPresenter;
import com.belatrixsf.events.presentation.ui.activities.MainActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by dvelasquez
 */
public class CitySelectionFragment extends BelatrixBaseFragment implements  CitySelectionFragmentPresenter.View{


    @Inject
    CitySelectionFragmentPresenter presenter;
    @BindView(R.id.accept)
    Button acceptButton;
    @BindView(R.id.later)
    Button laterButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindString(R.string.alert_text_select_city)
    String stringSelectCity;
    @BindView(R.id.city_list)
    ListView cityListView;


    public CitySelectionFragment() {
    }

    public static CitySelectionFragment newInstance() {
        CitySelectionFragment fragment = new CitySelectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        presenter.setView(this);
    }

    @Override
    protected void initViews() {
        presenter.actionLoadCities();
    }

    private void enableButtons(){
        acceptButton.setEnabled(true);
        laterButton.setEnabled(true);
    }

    private void disableButtons(){
        acceptButton.setEnabled(false);
        laterButton.setEnabled(false);
    }

    @Override
    public void showProgressIndicator() {
        disableButtons();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        enableButtons();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showCityList(final List<City> list) {
        List<String> stringList = new ArrayList<>();
        for(City city: list){
            stringList.add(city.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_single_choice,stringList);
        cityListView.setAdapter(adapter);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long id) {
                    presenter.setSelectedCity(list.get(position).getId());
            }
        });
    }


    @OnClick(R.id.accept)
    public void onClickAccept(){
        if (presenter.getSelectedCity() != null){
            presenter.actionSaveCityId();
            startActivity(MainActivity.makeIntent(getActivity()));
            getActivity().finish();
        } else {
            DialogUtils.createSimpleDialog(getActivity(),stringAppName, stringSelectCity).show();
        }
    }

    @OnClick(R.id.later)
    public void onClickLater(){
        presenter.actionClearCity();
        startActivity(MainActivity.makeIntent(getActivity()));
        getActivity().finish();
    }


    @Override
    public void onCityError(String errorMessage) {
        disableButtons();
        laterButton.setVisibility(View.VISIBLE);
        DialogUtils.createSimpleDialog(getActivity(),stringAppName, errorMessage).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_selection, container, false);
    }

}
