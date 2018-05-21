package com.belatrix.events.di.component;

import com.belatrix.events.di.module.UIModule;
import com.belatrix.events.di.scope.UIScope;
import com.belatrix.events.presentation.ui.activities.AboutActivity;
import com.belatrix.events.presentation.ui.activities.CitySelectionActivity;
import com.belatrix.events.presentation.ui.activities.EventDetailActivity;
import com.belatrix.events.presentation.ui.activities.EventListActivity;
import com.belatrix.events.presentation.ui.activities.FinderActivity;
import com.belatrix.events.presentation.ui.activities.IdeaAddActivity;
import com.belatrix.events.presentation.ui.activities.IdeaDetailActivity;
import com.belatrix.events.presentation.ui.activities.MainActivity;
import com.belatrix.events.presentation.ui.activities.NotificationListActivity;
import com.belatrix.events.presentation.ui.activities.ProfileActivity;
import com.belatrix.events.presentation.ui.activities.ScannerActivity;
import com.belatrix.events.presentation.ui.activities.SettingsActivity;
import com.belatrix.events.presentation.ui.activities.SplashActivity;
import com.belatrix.events.presentation.ui.fragments.AboutFragment;
import com.belatrix.events.presentation.ui.fragments.ChangePasswordFragment;
import com.belatrix.events.presentation.ui.fragments.CitySelectionFragment;
import com.belatrix.events.presentation.ui.fragments.CreateAccountFragment;
import com.belatrix.events.presentation.ui.fragments.EditProfileFragment;
import com.belatrix.events.presentation.ui.fragments.EventDetailIdeaFragment;
import com.belatrix.events.presentation.ui.fragments.EventDetailVoteFragment;
import com.belatrix.events.presentation.ui.fragments.EventListFragment;
import com.belatrix.events.presentation.ui.fragments.FinderFragment;
import com.belatrix.events.presentation.ui.fragments.IdeaAddFragment;
import com.belatrix.events.presentation.ui.fragments.IdeaDetailFragment;
import com.belatrix.events.presentation.ui.fragments.LoginFragment;
import com.belatrix.events.presentation.ui.fragments.ManageIdeasFragment;
import com.belatrix.events.presentation.ui.fragments.ModeratorIdeaDetailFragment;
import com.belatrix.events.presentation.ui.fragments.ModeratorListIdeasFragment;
import com.belatrix.events.presentation.ui.fragments.NewHomeFragment;
import com.belatrix.events.presentation.ui.fragments.NotificationListFragment;
import com.belatrix.events.presentation.ui.fragments.ProfileFragment;
import com.belatrix.events.presentation.ui.fragments.RecoverPasswordFragment;
import com.belatrix.events.presentation.ui.fragments.RegisterAssistanceFragment;
import com.belatrix.events.presentation.ui.fragments.SettingsFragment;
import com.belatrix.events.presentation.ui.fragments.UpdateIdeaFragment;

import dagger.Component;

/**
 * Created by dvelasquez on 2/23/17.
 */
@UIScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = UIModule.class)
public interface UIComponent {
    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(SettingsActivity activity);

    void inject(EventDetailActivity activity);

    void inject(FinderActivity activity);

    void inject(AboutActivity activity);

    void inject(EventListActivity activity);

    void inject(CitySelectionActivity activity);

    void inject(NotificationListActivity activity);

    void inject(EventDetailVoteFragment fragment);

    void inject(EventListFragment fragment);

    void inject(AboutFragment fragment);

    void inject(FinderFragment fragment);

    void inject(CitySelectionFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(NotificationListFragment fragment);

    void inject(LoginFragment fragment);

    void inject(RecoverPasswordFragment fragment);

    void inject(CreateAccountFragment fragment);

    void inject(ChangePasswordFragment fragment);

    void inject(NewHomeFragment fragment);

    void inject(EventDetailIdeaFragment fragment);

    void inject(IdeaDetailActivity activity);

    void inject(IdeaDetailFragment fragment);

    void inject(RegisterAssistanceFragment fragment);

    void inject(ScannerActivity activity);

    void inject(IdeaAddActivity activity);

    void inject(IdeaAddFragment fragment);

    void inject(UpdateIdeaFragment fragment);

    void inject(ProfileActivity activity);

    void inject(ProfileFragment profileFragment);

    void inject(EditProfileFragment fragment);

    void inject(ManageIdeasFragment fragment);

    void inject(ModeratorListIdeasFragment fragment);

    void inject(ModeratorIdeaDetailFragment fragment);
}
