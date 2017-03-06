package com.belatrixsf.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.fragments.HomeFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BelatrixBaseActivity {

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.menu_logout)
    TextView menuLogoutTextView;
    @BindView(R.id.menu_login)
    TextView menuLoginTextView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setupViews();
        loadParams();
    }

    private void loadParams() {
        int loginType = getIntent().getIntExtra(LoginActivity.LOGIN_PARAM,0);
        if (loginType == LoginActivity.IS_LOGGED){
            menuLoginTextView.setVisibility(View.GONE);
            menuLogoutTextView.setVisibility(View.VISIBLE);
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.full_name)).setText("Diego Velásquez");
        } else {
            menuLoginTextView.setVisibility(View.VISIBLE);
            menuLogoutTextView.setVisibility(View.GONE);
            navigationView.getMenu().findItem(R.id.menu_events).setVisible(false);
        }
    }

    protected void setupViews() {
        setupNavigationDrawerMenu();
        setupNavigationDrawerListener();
        replaceFragment(HomeFragment.newInstance(),false);
    }


    @OnClick(R.id.menu_login)
    public void clickLogin(){
        startActivity(LoginActivity.makeIntent(this));
        finishActivity();
    }

    @OnClick(R.id.menu_logout)
    public void clickLogout(){
        startActivity(LoginActivity.makeIntent(this));
        finishActivity();
    }


    private void setupNavigationDrawerMenu(){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
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
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_events:
                        startActivity(ManageEventActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_about:
                        startActivity(AboutActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_help:
                        //TODO: HelpActivity
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


    public static Intent makeIntent(Context context, Bundle params) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(params);
        return intent;
    }


}
