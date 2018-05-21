package com.belatrix.events.presentation.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.ScannerPresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.utils.DialogUtils;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import javax.inject.Inject;

public class ScannerActivity extends BelatrixBaseActivity implements DecoratedBarcodeView.TorchListener, View.OnClickListener, ScannerPresenter.View {

    public static final String ARGS_MEETING_ID = "args_meeting_id";

    @Inject
    ScannerPresenter mPresenter;

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private FloatingActionButton fabFlash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        fabFlash = findViewById(R.id.fab_flash);
        fabFlash.setOnClickListener(ScannerActivity.this);
        fabFlash.setSelected(true);
        if (!hasFlash()) {
            fabFlash.setVisibility(View.GONE);
        }

        capture = new CaptureManager(this, barcodeScannerView) {
            @Override
            protected void returnResult(BarcodeResult rawResult) {
                String text = rawResult.getText();
                mPresenter.registerAssistant(getIntent().getIntExtra(ARGS_MEETING_ID, 0), text);
            }
        };
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(ScannerActivity.this);
        mPresenter.setView(ScannerActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {
        fabFlash.setSelected(true);
    }

    @Override
    public void onTorchOff() {
        fabFlash.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        if (fabFlash.isSelected()) {
            barcodeScannerView.setTorchOff();
        } else {
            barcodeScannerView.setTorchOn();
        }
    }

    @Override
    public void onRegistrationSuccessful() {
        DialogUtils.createInformationDialog(ScannerActivity.this, getString(R.string.registration_successful), getString(R.string.registration_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                capture.decode();
                capture.onResume();
            }
        }).show();
    }

    @Override
    public void onRegistrationFailed() {
        DialogUtils.createInformationDialog(ScannerActivity.this, getString(R.string.registration_error_server), getString(R.string.registration_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                capture.decode();
                capture.onResume();
            }
        }).show();
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public Context getContext() {
        return ScannerActivity.this;
    }
}
