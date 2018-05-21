package com.belatrix.events.presentation.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
 * modified by lburgos
 */
public class EventDetailAboutFragment extends BelatrixBaseFragment {

    @BindView(R.id.tv_details)
    TextView tvDescription;
    @BindView(R.id.tv_location)
    TextView tvPlace;
    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_agenda)
    TextView tvAgenda;
    @BindView(R.id.tv_caption_date)
    TextView tvCaptionDate;
    @BindView(R.id.tv_caption_place)
    TextView tvCaptionPlace;
    @BindView(R.id.tv_caption_link)
    TextView tvCaptionLink;
    @BindView(R.id.tv_caption_agenda)
    TextView tvCaptionAgenda;

    Event event;

    public static Fragment newInstance(Context context, Event event) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EVENT_KEY, event);
        return Fragment.instantiate(context, EventDetailAboutFragment.class.getName(), args);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments() != null) {
            event = getArguments().getParcelable(Constants.EVENT_KEY);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {

    }

    @Override
    protected void initViews() {
        tvDescription.setText(event.getDetails());

        if (TextUtils.isEmpty(event.getAddress())) {
            tvCaptionPlace.setVisibility(View.GONE);
            tvPlace.setVisibility(View.GONE);
        } else {
            tvPlace.setText(event.getAddress());
        }
        if (TextUtils.isEmpty(event.getRegisterLink())) {
            tvCaptionLink.setVisibility(View.GONE);
            tvLink.setVisibility(View.GONE);
        }else{
            tvLink.setText(event.getRegisterLink());
        }

        String agenda = "";
        if(TextUtils.isEmpty(agenda)){
            tvCaptionAgenda.setVisibility(View.GONE);
            tvAgenda.setVisibility(View.GONE);
        }else{
            tvAgenda.setText(agenda);
        }

        tvDate.setText(DateUtils.formatDate(event.getDatetime(), DateUtils.DATE_FORMAT_3, DateUtils.DATE_FORMAT_4));
    }

    @OnClick(R.id.iv_location)
    public void onClickLocation() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail_about, container, false);
    }
}
