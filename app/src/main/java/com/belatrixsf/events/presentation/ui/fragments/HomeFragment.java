package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;


import butterknife.BindString;

/**
 * created by dvelasquez
 */
public class HomeFragment extends BelatrixBaseFragment {


    @BindString(R.string.event_title_near)
    String eventTitleNear;
    @BindString(R.string.event_title_past)
    String eventTitlePast;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }


    @Override
    protected void initViews() {
        replaceChildFragment(EventListSummaryFragment.newInstance(eventTitleNear,eventTitleNear),R.id.frame_events_near);
        replaceChildFragment(EventListSummaryFragment.newInstance(eventTitlePast,eventTitlePast),R.id.frame_events_past);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
