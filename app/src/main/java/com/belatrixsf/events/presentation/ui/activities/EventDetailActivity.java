package com.belatrixsf.events.presentation.ui.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.events.R;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.presentation.ui.common.DisableableAppBarLayoutBehavior;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailAboutFragment;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailVoteFragment;
import com.belatrixsf.events.utils.DateUtils;
import com.belatrixsf.events.utils.DialogUtils;
import com.belatrixsf.events.utils.media.ImageFactory;
import com.belatrixsf.events.utils.media.loaders.ImageLoader;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class EventDetailActivity extends BelatrixBaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final int RC_CALENDAR_PERM = 1023;
    public static final String EVENT_KEY = "_event_id";

    @BindView(R.id.event_picture)
    ImageView pictureImageView;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindString(R.string.bottom_navigation_color)
    String navigationColor;
    @BindString(R.string.event_detail_added_calendar)
    String stringEventAdded;
    private Event event;
    public static final int TAB_ABOUT = 0;
    public static final int TAB_VOTE = 1;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindDrawable(R.drawable.event_placeholder)
    Drawable eventPlaceHolderDrawable;
    boolean calendarSaved;

    EventDetailAboutFragment eventDetailAboutFragment;
    EventDetailVoteFragment eventDetailVoteFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ActivityCompat.postponeEnterTransition(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(EVENT_KEY);
        }
        initViews();
    }

    private void initViews() {
        setupViews();
        replaceFragment(eventDetailAboutFragment, false);
        if (event.isHasInteractions()){
            bottomNavigation.setVisibility(View.VISIBLE);
        } else {
            bottomNavigation.setVisibility(View.GONE);
        }
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    switch (position) {
                        case TAB_ABOUT:
                            enableAppBarLayout(true);
                            replaceFragment(eventDetailAboutFragment, false);
                            break;

                        case TAB_VOTE:
                            appBarLayout.setExpanded(false, true);
                            enableAppBarLayout(false);
                            replaceFragment(eventDetailVoteFragment, false);
                            break;

                    }
                }
                return true;
            }
        });
        setTitle(event.getTitle());
        ImageFactory.getLoader().loadFromUrl(event.getImage(),
                pictureImageView,
                null,
                eventPlaceHolderDrawable,
                ImageLoader.ScaleType.CENTER_CROP
        );
    }

    private void enableAppBarLayout(boolean value){
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        ((DisableableAppBarLayoutBehavior) layoutParams.getBehavior()).setEnabled(value);
    }

    private void setupViews() {
        eventDetailAboutFragment = EventDetailAboutFragment.newInstance(event);
        eventDetailVoteFragment = EventDetailVoteFragment.newInstance(event);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_event_about, R.drawable.ic_about, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_event_interact, R.drawable.ic_about, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor(navigationColor));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                BelatrixBaseFragment fragment = (BelatrixBaseFragment)getSupportFragmentManager().findFragmentById(R.id.main_content);
                fragment.refreshData();
            }
        });
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    public static Intent makeIntent(Context context, Event event) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(EVENT_KEY, event);
        return intent;
    }

    @OnClick(R.id.button_add_calendar)
    public void onClickAddCalendar(){
        addToCalendar();
    }


    @AfterPermissionGranted(RC_CALENDAR_PERM)
    @SuppressWarnings("MissingPermission")
    public void addToCalendar(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA) && !calendarSaved) {
            Date date = DateUtils.getDateFromtString(event.getDatetime(), DateUtils.DATE_FORMAT_3);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            ContentResolver cr = this.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
            values.put(CalendarContract.Events.TITLE, event.getTitle());
            values.put(CalendarContract.Events.DESCRIPTION, event.getDetails());
            TimeZone timeZone = TimeZone.getDefault();
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            //values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL="+ dtUntill);
            //for one hour
            values.put(CalendarContract.Events.DURATION, "+P1H");
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            // insert event to calendar
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            showSnackBar(stringEventAdded);
            calendarSaved = true;
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_ask_again),
                    RC_CALENDAR_PERM, Manifest.permission.WRITE_CALENDAR);
        }
    }

    @OnClick(R.id.button_share)
    public void onClickShare(){
        String sharingText = (event.getSharingText() != null && !event.getSharingText().isEmpty() ? event.getSharingText(): event.getTitle());
        DialogUtils.shareContent(this,sharingText+"\n"+event.getImage(), stringShare);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Timber.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

}
