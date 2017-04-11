package com.belatrix.events.presentation.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Employee;
import com.belatrix.events.presentation.presenters.FinderFragmentPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.utils.DialogUtils;
import com.belatrix.events.utils.media.ImageFactory;
import com.belatrix.events.utils.media.loaders.ImageLoader;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * created by dvelasquez
 */
public class FinderFragment extends BelatrixBaseFragment implements EasyPermissions.PermissionCallbacks, FinderFragmentPresenter.View {

    private static final int RC_CAMERA_PERM = 1024;
    @BindString(R.string.scan_qr_code)
    String stringScanQR;
    @BindView(R.id.result)
    View resultView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Inject
    FinderFragmentPresenter presenter;
    @BindView(R.id.full_name)
    TextView nameTextView;
    @BindView(R.id.role_name)
    TextView roleTextView;
    @BindView(R.id.email)
    TextView emailTextView;
    @BindView(R.id.twitter)
    TextView twitterTextView;
    @BindView(R.id.github)
    TextView githubTextView;
    @BindView(R.id.website)
    TextView websiteTextView;
    @BindView(R.id.avatar)
    ImageView avatarImageView;
    @BindDrawable(R.drawable.contact_placeholder)
    Drawable contactDrawable;


    public FinderFragment() {
    }

    public static FinderFragment newInstance() {
        FinderFragment fragment = new FinderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
        presenter.setView(this);
    }

    @Override
    public void onEmployeeSuccess(Employee employee) {
        resultView.setVisibility(View.VISIBLE);
        String name = employee.getName();
        String email = employee.getEmail();
        String role = employee.getRole();
        String twitter = employee.getTwitter();
        String github = employee.getGithub();
        String website = employee.getWebsite();

        if (name != null && !name.isEmpty()) {
            nameTextView.setText(name);
            showField(nameTextView);
        }
        if (email != null && !email.isEmpty()) {
            emailTextView.setText(email);
            showField(emailTextView);
        }
        if (role != null && !role.isEmpty()) {
            roleTextView.setText(role);
            showField(roleTextView);
        }
        if (twitter != null && !twitter.isEmpty()) {
            twitterTextView.setText(twitter);
            showField(twitterTextView);
        }
        if (github != null && !github.isEmpty()) {
            githubTextView.setText(github);
            showField(githubTextView);
        }
        if (website != null && !website.isEmpty()) {
            websiteTextView.setText(website);
            showField(websiteTextView);
        }

        ImageFactory.getLoader().loadFromUrl(employee.getAvatar(),
                avatarImageView,
                null,
                null,
                ImageLoader.ScaleType.FIT_CENTER
        );
    }

    private void showField(TextView textView){
        textView.setVisibility(View.VISIBLE);
    }

    private void hideField(TextView textView){
        textView.setVisibility(View.GONE);
    }

    @Override
    public void onEmployeeError(String errorMessage) {
        DialogUtils.createSimpleDialog(getActivity(), stringAppName, errorMessage).show();
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        resultView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.scan)
    public void onClickScan() {
        scanQR();
    }

    private void hideFields() {
        avatarImageView.setImageDrawable(contactDrawable);
        hideField(nameTextView);
        hideField(roleTextView);
        hideField(emailTextView);
        hideField(githubTextView);
        hideField(websiteTextView);
        hideField(twitterTextView);
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    @SuppressWarnings("MissingPermission")
    public void scanQR() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            FragmentIntentIntegrator integrator = new FragmentIntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt(stringScanQR);
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(true);
            integrator.initiateScan();
        } else {
            EasyPermissions.requestPermissions(getActivity(), getString(R.string.rationale_ask_again),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finder, container, false);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                findQR(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void findQR(String qrCode) {
        hideFields();
        presenter.actionFindPerson(qrCode);
    }

    public final class FragmentIntentIntegrator extends IntentIntegrator {

        private final Fragment fragment;

        public FragmentIntentIntegrator(Fragment fragment) {
            super(fragment.getActivity());
            this.fragment = fragment;
        }

        @Override
        protected void startActivityForResult(Intent intent, int code) {
            fragment.startActivityForResult(intent, code);
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

    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }
}
