package com.belatrix.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.presentation.presenters.SettingsFragmentPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.DialogUtils;
import com.belatrix.events.utils.account.AccountUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by dvelasquez
 */
public class SettingsFragment extends BelatrixBaseFragment implements SettingsFragmentPresenter.View {


    @Inject
    SettingsFragmentPresenter presenter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.city_list)
    Spinner cityListSpinner;
    @BindString(R.string.label_settings_select_city)
    String stringSelectCity;
    @BindView(R.id.notification)
    Switch notificationSwitch;

    @Inject
    AccountUtils mAccountUtils;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        notificationSwitch.setChecked(presenter.isNotificationEnabled());
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.saveNotification(isChecked);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        cityListSpinner.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showCityList(final List<City> list) {
        cityListSpinner.setVisibility(View.VISIBLE);
        final List<String> stringList = new ArrayList<>();
        stringList.add(stringSelectCity);
        for (City city : list) {
            stringList.add(city.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, stringList);
        cityListSpinner.setAdapter(adapter);
        reloadSelectedValue(presenter.getCityId(), list);
        cityListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    presenter.actionClearCity();
                } else {
                    presenter.actionSaveCityId(list.get(position - 1).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void reloadSelectedValue(Integer cityId, List<City> cityList) {
        if (cityId != null) {
            int selectedPosition = -1;
            final int size = cityList.size();
            for (int i = 0; i < size; i++) {
                City city = cityList.get(i);
                if (city.getId() == cityId.intValue()) {
                    selectedPosition = i + 1;
                    break;
                }
            }
            if (selectedPosition > 0) {
                cityListSpinner.setSelection(selectedPosition, true);
            }
        }
    }


    @Override
    public void onCityError(String errorMessage) {
        DialogUtils.createSimpleDialog(getActivity(), stringAppName, errorMessage).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelRequests();
    }

    @OnClick(R.id.bt_sign_out)
    public void onClickSignOutEvent() {
        if (getActivity() != null) {
            mAccountUtils.signOut();
            getActivity().finish();
        }
    }
}
