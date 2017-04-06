package com.belatrixsf.events.presentation.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.presenters.FinderFragmentPresenter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * created by dvelasquez
 */
public class FinderFragment extends BelatrixBaseFragment implements EasyPermissions.PermissionCallbacks, FinderFragmentPresenter.View{

    private static final int RC_CAMERA_PERM = 1024;
    @BindString(R.string.scan_qr_code)
    String stringScanQR;
    @BindView(R.id.result)
    View resultView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Inject
    FinderFragmentPresenter presenter;

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
    public void onResult() {
        resultView.setVisibility(View.VISIBLE);
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
    public void onClickScan(){
        scanQR();
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
                    RC_CAMERA_PERM, Manifest.permission.WRITE_CALENDAR);
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
        if ( result != null ) {
            if ( result.getContents() == null ) {
            } else {
                findQR(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void findQR(String qrCode){
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

}
