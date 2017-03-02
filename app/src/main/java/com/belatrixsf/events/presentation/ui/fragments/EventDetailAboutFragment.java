package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

import butterknife.BindView;

/**
 * created by dvelasquez
 */
public class EventDetailAboutFragment extends BelatrixBaseFragment  {

    @BindView(R.id.description)
    TextView descriptionTextView;
    Event event;


    public EventDetailAboutFragment() {
    }

    public static EventDetailAboutFragment newInstance(Event event) {
        EventDetailAboutFragment fragment = new EventDetailAboutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EventDetailActivity.EVENT_KEY, event);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        event = getArguments().getParcelable(EventDetailActivity.EVENT_KEY);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {

    }

    @Override
    protected void initViews() {
            descriptionTextView.setText(event.getDescription());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_about, container, false);
    }
}
