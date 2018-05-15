package com.belatrix.events.presentation.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DisableableAppBarLayoutBehavior;
import com.belatrix.events.presentation.ui.fragments.EventDetailAboutFragment;
import com.belatrix.events.presentation.ui.fragments.EventDetailIdeaFragment;
import com.belatrix.events.presentation.ui.fragments.EventDetailVoteFragment;
import com.belatrix.events.utils.Constants;
import com.belatrix.events.utils.DateUtils;
import com.belatrix.events.utils.DialogUtils;
import com.belatrix.events.utils.account.AccountUtils;
import com.belatrix.events.utils.media.ImageFactory;
import com.belatrix.events.utils.media.loaders.ImageLoader;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class EventDetailActivity extends BelatrixBaseActivity implements EasyPermissions.PermissionCallbacks {

    public static final int TAB_ABOUT = 0;
    public static final int TAB_IDEA = 1;
    public static final int TAB_VOTE = 2;
    private static final int RC_CALENDAR_PERM = 1023;
    private static final int INVALID_CALENDAR_ID = -1;
    private static final int CALENDAR_NO_READ_RP = 0;
    private static final int REQ_AUTHENTICATION = 3342;
    private static final int REQ_CREATE_IDEA = 2242;
    @BindView(R.id.image_event)
    ImageView pictureImageView;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.fab_add_idea)
    FloatingActionButton fabAddIdea;
    @BindString(R.string.bottom_navigation_color)
    String navigationColor;
    @BindString(R.string.event_detail_added_calendar)
    String stringEventAdded;
    @BindString(R.string.event_invalid_calendar)
    String stringInvalidCalendar;
    @BindString(R.string.event_detail_already_added_calendar)
    String stringEventAlreadyAdded;
    @BindString(R.string.idea_created)
    String stringIdeaCreated;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindDrawable(R.drawable.event_placeholder)
    Drawable eventPlaceHolderDrawable;
    @Inject
    AccountUtils mAccountUtils;

    Fragment eventDetailAboutFragment;
    Fragment eventDetailIdeaFragment;
    Fragment eventDetailVoteFragment;
    private Event event;

    public static void startActivity(Activity context, Event event, ImageView imageView) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(Constants.EVENT_KEY, event);
        ViewCompat.setTransitionName(imageView, context.getString(R.string.transition_photo));
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context, imageView, context.getString(R.string.transition_photo));
        context.startActivity(intent, options.toBundle());
    }

    private static boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ActivityCompat.postponeEnterTransition(this);
        setNavigationToolbar();
        if (savedInstanceState == null) {
            event = getIntent().getParcelableExtra(Constants.EVENT_KEY);
        } else {
            restoreInstance(savedInstanceState);
        }
        initViews();
    }

    private void initViews() {
        ImageFactory.getLoader().loadFromUrl(event.getImage(),
                pictureImageView,
                null,
                new ImageLoader.Callback() {
                    @Override
                    public void onSuccess() {
                        startTransition();
                    }

                    @Override
                    public void onFailure() {
                        startTransition();
                    }
                },
                eventPlaceHolderDrawable,
                ImageLoader.ScaleType.CENTER_CROP
        );
        setupViews();
        replaceFragment(eventDetailAboutFragment, false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case TAB_ABOUT:
                        appBarLayout.setExpanded(true, true);
                        enableAppBarLayout(true);
                        replaceFragment(eventDetailAboutFragment, false);
                        fabAddIdea.setVisibility(View.GONE);
                        break;
                    case TAB_IDEA:
                        appBarLayout.setExpanded(false, true);
                        enableAppBarLayout(false);
                        replaceFragment(eventDetailIdeaFragment, false);
                        fabAddIdea.setVisibility(View.VISIBLE);
                        break;
                    case TAB_VOTE:
                        appBarLayout.setExpanded(false, true);
                        enableAppBarLayout(false);
                        replaceFragment(eventDetailVoteFragment, false);
                        fabAddIdea.setVisibility(View.GONE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setTitle(event.getTitle());

    }

    private void startTransition() {
        if (pictureImageView == null) {
            return;
        }
        pictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(EventDetailActivity.this);
                return false;
            }
        });
    }

    private void enableAppBarLayout(boolean value) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        ((DisableableAppBarLayoutBehavior) layoutParams.getBehavior()).setEnabled(value);
    }

    private void setupViews() {
        eventDetailAboutFragment = EventDetailAboutFragment.newInstance(EventDetailActivity.this, event);
        eventDetailIdeaFragment = EventDetailIdeaFragment.newInstance(EventDetailActivity.this, event);
        eventDetailVoteFragment = EventDetailVoteFragment.newInstance(EventDetailActivity.this, event);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_info).setText(R.string.tab_event_about));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_idea).setText(R.string.tab_event_idea));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_group).setText(R.string.tab_event_votes));

        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                BelatrixBaseFragment fragment = (BelatrixBaseFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.main_content);
                fragment.refreshData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu_event_detail, menu);
        boolean isUpcoming = event.isUpcoming();
        menu.findItem(R.id.action_share).setVisible(isUpcoming);
        menu.findItem(R.id.action_calendar).setVisible(isUpcoming);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @AfterPermissionGranted(RC_CALENDAR_PERM)
    @SuppressWarnings("MissingPermission")
    public void addToCalendar() {
        String[] permissionsCalendar = new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
        if (EasyPermissions.hasPermissions(this, permissionsCalendar)) {
            Long calendarId = getCalendarId();
            createEvent(this.getContentResolver(), calendarId);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_ask_again),
                    RC_CALENDAR_PERM, permissionsCalendar);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void createEvent(ContentResolver resolver, long calendarId) {
        if (calendarId == INVALID_CALENDAR_ID) {
            showSnackBar(stringInvalidCalendar);
            return;
        }
        Cursor cursor;
        ContentValues values = new ContentValues();
        // Check if the calendar event exists first.  If it does, we don't want to add a duplicate one.
        cursor = resolver.query(
                CalendarContract.Events.CONTENT_URI,                       // URI
                new String[]{CalendarContract.Events._ID},                // Projection
                CalendarContract.Events.CALENDAR_ID + "=? and "            // Selection
                        + CalendarContract.Events.TITLE + "=?",
                new String[]{                                              // Selection args
                        Long.valueOf(calendarId).toString(),
                        event.getTitle()
                },
                null);

        if (cursor != null && cursor.moveToFirst()) {
            showSnackBar(stringEventAlreadyAdded);
            // Calendar event already exists for this session.
            cursor.close();
        } else {
            Date date = DateUtils.getDateFromString(event.getDatetime(), DateUtils.DATE_FORMAT_3);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            values.clear();
            values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
            values.put(CalendarContract.Events.DESCRIPTION, event.getDetails());
            TimeZone timeZone = TimeZone.getDefault();
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
            values.put(CalendarContract.Events.TITLE, event.getTitle());
            values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
            //for one hour
            values.put(CalendarContract.Events.DURATION, "+P1H");
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            Uri eventUri = resolver.insert(CalendarContract.Events.CONTENT_URI, values);
            if (eventUri != null) {
                String eventId = eventUri.getLastPathSegment();
                showSnackBar(stringEventAdded);
                Log.v("NEW EVENT ID", eventId);
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private long getCalendarId() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CALENDAR)) {
            String[] EVENT_PROJECTION = new String[]{
                    CalendarContract.Calendars._ID,                           // 0
            };

            Cursor cur;
            ContentResolver cr = getContentResolver();
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            String selection = "(" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?)";
            String[] selectionArgs = new String[]{"com.google"};
            cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

            // Use the cursor to step through the returned records
            long calendarId = INVALID_CALENDAR_ID;//invalid
            if (cur != null && cur.moveToFirst()) {
                calendarId = cur.getLong(0);
                System.out.println(calendarId);
                cur.close();
            }
            return calendarId;
        } else {
            return CALENDAR_NO_READ_RP;
        }
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
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void restoreInstance(Bundle saveInstance) {
        event = saveInstance.getParcelable(Constants.EVENT_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.EVENT_KEY, event);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                addToCalendar();
                break;
            case R.id.action_share:
                String sharingText = (event.getSharingText() != null && !event.getSharingText().isEmpty() ? event.getSharingText() : event.getTitle());
                DialogUtils.shareContent(this, sharingText + "\n" + event.getImage(), stringShare);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_add_idea)
    void onAddIdeaClick() {
        if (mAccountUtils.existsAccount()) {
            Intent intent = IdeaAddActivity.makeIntent(EventDetailActivity.this, event.getId());
            startActivityForResult(intent, REQ_CREATE_IDEA);
        } else {
            startActivityForResult(AuthenticatorActivity.makeIntent(EventDetailActivity.this), REQ_AUTHENTICATION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_AUTHENTICATION:
                if (resultCode == RESULT_OK) {
                    Intent intent = IdeaAddActivity.makeIntent(EventDetailActivity.this, event.getId());
                    startActivityForResult(intent, REQ_CREATE_IDEA);
                }
                break;
            case REQ_CREATE_IDEA:
                if (resultCode == RESULT_OK) {
                    showSnackBar(stringIdeaCreated);
                }
                break;
        }
    }
}
