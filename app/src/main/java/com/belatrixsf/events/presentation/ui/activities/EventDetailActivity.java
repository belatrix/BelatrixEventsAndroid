package com.belatrixsf.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.belatrixsf.events.R;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailAboutFragment;
import com.belatrixsf.events.presentation.ui.fragments.EventDetailVoteFragment;

import butterknife.BindString;
import butterknife.BindView;

public class EventDetailActivity extends BelatrixBaseActivity {


    public static final String EVENT_ID_KEY = "_event_id";

    @BindView(R.id.event_picture)
    ImageView pictureImageView;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindString(R.string.bottom_navigation_color) String navigationColor;
    private int eventId;
    public static final int TAB_ABOUT = 0;
    public static final int TAB_VOTE = 1;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ActivityCompat.postponeEnterTransition(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            eventId = getIntent().getIntExtra(EVENT_ID_KEY, -1);
        }
        initViews();
    }

    private void initViews() {
        setupViews();
        replaceFragment(EventDetailAboutFragment.newInstance(eventId),false);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (!wasSelected) {
                    switch (position) {

                        case TAB_ABOUT:
                            replaceFragment(EventDetailAboutFragment.newInstance(eventId),false);
                            break;

                        case TAB_VOTE:
                            replaceFragment(EventDetailVoteFragment.newInstance(eventId),false);
                            break;

                    }
                }
                return true;
            }
        });
    }

    private void setupViews() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_event_about, R.drawable.ic_about, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_event_vote, R.drawable.ic_about, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(Color.parseColor(navigationColor));
        bottomNavigation.setBehaviorTranslationEnabled(false);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    public static Intent makeIntent(Context context, int eventId) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(EVENT_ID_KEY,eventId);
        return intent;
    }

}
