package com.belatrix.events.presentation.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Location;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.Constants;
import com.belatrix.events.utils.DateUtils;

import butterknife.BindView;
import butterknife.OnClick;
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
    Event event;

    public EventDetailAboutFragment() {
    }

    public static EventDetailAboutFragment newInstance(Event event) {
        EventDetailAboutFragment fragment = new EventDetailAboutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EVENT_KEY, event);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        event = getArguments().getParcelable(Constants.EVENT_KEY);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {

    }

    @Override
    protected void initViews() {
        descriptionTextView.setText(event.getDetails());
        locationTextView.setText(event.getAddress());
        SpannableString s = SpannableString.valueOf(locationTextView.getText());
        s.setSpan(new URLSpan(s.toString()), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        locationTextView.setText(s);
        linkTextView.setText(event.getRegisterLink());
        dateTextView.setText(DateUtils.formatDate(event.getDatetime(),DateUtils.DATE_FORMAT_3,DateUtils.DATE_FORMAT_4 ));
    }

    @OnClick(R.id.location)
    public void onClickLocation(){
        Location location = event.getLocation();
        if (location != null) {
            String latitude = location.getLatitude();
            String longitude = location.getLongitude();
            try {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + latitude + "," + longitude + " (" + location.getName() + ")"));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                String mapURL = String.format("https://www.google.com/maps/place/%s+%s/@%s,%s,15z", latitude, longitude, latitude, longitude);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(mapURL));
                startActivity(intent);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_about, container, false);
    }
}
