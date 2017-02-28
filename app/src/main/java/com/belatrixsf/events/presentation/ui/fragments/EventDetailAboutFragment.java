package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.ApplicationComponent;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

/**
 * created by dvelasquez
 */
public class EventDetailAboutFragment extends BelatrixBaseFragment  {


    public EventDetailAboutFragment() {
    }

    public static EventDetailAboutFragment newInstance(int eventId) {
        EventDetailAboutFragment fragment = new EventDetailAboutFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EventDetailActivity.EVENT_ID_KEY, eventId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(ApplicationComponent applicationComponent) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_about, container, false);
    }
}
