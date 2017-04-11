/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrix.events.utils.fcm;

import com.belatrix.events.BxEventsApplication;
import com.belatrix.events.domain.interactors.RegisterDeviceInteractor;
import com.belatrix.events.domain.model.Device;
import com.belatrix.events.utils.cache.Cache;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

import timber.log.Timber;

public class EventsFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Inject
    Cache cache;
    @Inject
    RegisterDeviceInteractor registerDeviceInteractor;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BxEventsApplication)getApplication()).getComponent().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        cache.saveDeviceToken(refreshedToken);
        registerDeviceInteractor.execute(new RegisterDeviceInteractor.CallBack() {
            @Override
            public void onSuccess(Device device) {
                Timber.d("onSuccess: Token was registered: " + refreshedToken );
                cache.saveDeviceId(device.getId());
            }

            @Override
            public void onError() {
                Timber.d("onError: Token was not registered");
            }
        }, RegisterDeviceInteractor.Params.forRegisterDevice(refreshedToken));
        super.onTokenRefresh();
    }

}