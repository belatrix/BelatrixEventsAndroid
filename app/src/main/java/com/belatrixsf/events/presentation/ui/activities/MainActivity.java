package com.belatrixsf.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.fragments.HomeFragment;
import com.belatrixsf.events.utils.cache.Cache;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BelatrixBaseActivity {

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindString(R.string.belatrix_url)
    String stringURL;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Inject
    Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setupViews();
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        /*
        if (cache.isFirstTime()){
           startActivity(CitySelectionActivity.makeIntent(this));
            cache.updateFirstTime();
        }*/
    }

    protected void setupViews() {
        setupNavigationDrawerMenu();
        setupNavigationDrawerListener();
        replaceFragment(HomeFragment.newInstance(),false);
        cache.clearStartAppFlag();
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
                    case R.id.menu_finder:
                        startActivity(FinderActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_about:
                        startActivity(AboutActivity.makeIntent(MainActivity.this));
                        break;
                    case R.id.menu_help:
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(stringURL));
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


    public static Intent makeIntent(Context context, Bundle params) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(params);
        return intent;
    }

    public static Intent makeIntent(Context context) {
       return new Intent(context, MainActivity.class);
    }

}
