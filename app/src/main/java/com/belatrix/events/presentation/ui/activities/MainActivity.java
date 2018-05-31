package com.belatrix.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.presentation.ui.fragments.ManageIdeasFragment;
import com.belatrix.events.presentation.ui.fragments.NewHomeFragment;
import com.belatrix.events.presentation.ui.fragments.RegisterAssistanceFragment;
import com.belatrix.events.presentation.ui.fragments.SearchUserFragment;
import com.belatrix.events.utils.account.AccountUtils;
import com.belatrix.events.utils.cache.Cache;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BelatrixBaseActivity {

    public static final String PARAM_FROM_NOTIFICATION = "param_from_notification";
    private static final int REQ_AUTHENTICATION = 432;
    private static final int REQ_SETTINGS = 234;

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    TextView tvName;
    TextView tvEmail;

    @BindString(R.string.belatrix_url)
    String stringURL;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Inject
    Cache cache;

    @Inject
    AccountUtils accountUtils;

    private MenuItem menuEvents;
    private MenuItem menuRegisterAssistance;
    private MenuItem menuManageIdeas;
    private MenuItem menuSearchUser;
    private MenuItem menuMyIdeas;


    public static Intent makeIntent(Context context, Bundle params) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(params);
        return intent;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static Intent makeIntentWithoutAnimation(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.overridePendingTransition(0, 0);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setupViews();
        if (getIntent().hasExtra(PARAM_FROM_NOTIFICATION)) {
            boolean isFromNotification = getIntent().getBooleanExtra(PARAM_FROM_NOTIFICATION, false);
            if (isFromNotification) {
                startActivity(NotificationListActivity.makeIntent(MainActivity.this));
            }
        }
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    protected void setupViews() {
        setupNavigationDrawerMenu();
        setupNavigationDrawerListener();
        View header = navigationView.getHeaderView(0);
        tvName = header.findViewById(R.id.tv_name);
        tvEmail = header.findViewById(R.id.tv_email);

        replaceFragment(NewHomeFragment.create(MainActivity.this, cache.getCity()), false);
        cache.clearStartAppFlag();
        setupProfile();
    }

    private void setupProfile() {
        if (accountUtils.existsAccount()) {
            tvName.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);
            tvName.setText(accountUtils.getFullName());
            tvEmail.setText(accountUtils.getEmail());
            tvEmail.setOnClickListener(null);
            navigationView.getMenu().findItem(R.id.menu_user_options).setVisible(true);
            if (accountUtils.isStaff()) {
                navigationView.getMenu().findItem(R.id.menu_organizer_options).setVisible(true);
            }
            if (accountUtils.isModerator()) {
                navigationView.getMenu().findItem(R.id.menu_moderator_options).setVisible(true);
            }
        } else {
            tvName.setVisibility(View.INVISIBLE);
            tvEmail.setText(R.string.sign_in_or_sign_up);
            tvEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(AuthenticatorActivity.makeIntent(MainActivity.this), REQ_AUTHENTICATION);
                }
            });
            navigationView.getMenu().findItem(R.id.menu_user_options).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_organizer_options).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_moderator_options).setVisible(false);
        }
    }

    private void setupNavigationDrawerMenu() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    protected void setupNavigationDrawerListener() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                if (menuEvents == null || menuManageIdeas == null || menuMyIdeas == null || menuRegisterAssistance == null || menuSearchUser == null) {
                    Menu menu = navigationView.getMenu();
                    menuEvents = menu.findItem(R.id.menu_events);
                    menuRegisterAssistance = menu.findItem(R.id.menu_register_assistance);
                    menuManageIdeas = navigationView.getMenu().findItem(R.id.menu_manage_ideas);
                    menuSearchUser = navigationView.getMenu().findItem(R.id.menu_search_user);
                    menuMyIdeas = navigationView.getMenu().findItem(R.id.menu_my_ideas);
                }

                menuEvents.setChecked(false);
                menuRegisterAssistance.setChecked(false);
                menuManageIdeas.setChecked(false);
                menuSearchUser.setChecked(false);
                menuMyIdeas.setChecked(false);

                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_events:
                        item.setChecked(true);
                        replaceFragment(NewHomeFragment.create(MainActivity.this, cache.getCity()), false);
                        break;
                    case R.id.menu_settings:
                        startActivity(SettingsActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_profile:
                        startActivityForResult(ProfileActivity.makeIntent(MainActivity.this), REQ_SETTINGS);
                        break;
                    case R.id.menu_my_ideas:
                        item.setChecked(true);
                        replaceFragment(ManageIdeasFragment.create(MainActivity.this, cache.getCity(), true), false);
                        break;
                    case R.id.menu_reports:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://bxevents.herokuapp.com/reports/"));
                        startActivity(intent);
                        break;
                    case R.id.menu_register_assistance:
                        item.setChecked(true);
                        replaceFragment(RegisterAssistanceFragment.create(MainActivity.this), false);
                        break;
                    case R.id.menu_search_user:
                        item.setChecked(true);
                        replaceFragment(SearchUserFragment.create(MainActivity.this), false);
                        break;
                    case R.id.menu_manage_ideas:
                        item.setChecked(true);
                        replaceFragment(ManageIdeasFragment.create(MainActivity.this, cache.getCity(), false), false);
                        break;
                    case R.id.menu_activities:
                        startActivity(NotificationListActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_about:
                        startActivity(AboutActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_help:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringURL));
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_AUTHENTICATION || requestCode == REQ_SETTINGS) {
            setupProfile();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
