package com.belatrix.events.presentation.presenters;

import android.graphics.Bitmap;

import com.belatrix.events.R;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.belatrix.events.utils.account.AccountUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import javax.inject.Inject;

import butterknife.BindDimen;

public class ProfilePresenter extends BelatrixBasePresenter<ProfilePresenter.View> {

    private final AccountUtils mAccountUtils;

    @Inject
    ProfilePresenter(AccountUtils accountUtils) {
        this.mAccountUtils = accountUtils;
    }

    @Override
    public void cancelRequests() {

    }

    public void loadUser() {
        view.loadEmailField(mAccountUtils.getEmail());
        view.loadFullNameField(mAccountUtils.getFullName());
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = barcodeEncoder.encodeBitmap(mAccountUtils.getEmail(), BarcodeFormat.QR_CODE, 400, 400);
            view.loadQRImage(bitmap);
        } catch (WriterException wex) {
            wex.printStackTrace();
        }
    }

    public interface View extends BelatrixBaseView {
        void loadEmailField(String email);

        void loadFullNameField(String fullName);

        void loadQRImage(Bitmap bitmap);
    }
}