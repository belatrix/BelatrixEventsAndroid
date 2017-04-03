package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.utils.DateUtils;

import butterknife.BindView;

/**
 * created by dvelasquez
 */
public class EventDetailAboutFragment extends BelatrixBaseFragment  {

    @BindView(R.id.details)
    TextView descriptionTextView;
    @BindView(R.id.location)
    TextView locationTextView;
    @BindView(R.id.link)
    TextView linkTextView;
    @BindView(R.id.date)
    TextView dateTextView;
    @BindView(R.id.map_location)
    WebView mapLocation;
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
        descriptionTextView.setText(event.getDetails());
        locationTextView.setText(event.getAddress());
        linkTextView.setText(event.getRegisterLink());
        dateTextView.setText(DateUtils.formatDate(event.getDatetime(),DateUtils.DATE_FORMAT_3,DateUtils.DATE_FORMAT_4 ));
        WebSettings webSettings = mapLocation.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //ws.setSupportZoom(false);
        mapLocation.setWebViewClient(new WebViewClient());
        mapLocation.loadUrl("https://www.google.com/maps/place/-12.09986273+-77.01899618/@-12.09986273,-77.01899618,15z");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_about, container, false);
    }
}
