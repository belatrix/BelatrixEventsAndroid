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
package com.belatrix.events.presentation.ui.activities;

import android.os.Bundle;

import com.belatrix.events.BuildConfig;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrix.events.utils.cache.Cache;

import javax.inject.Inject;

/**
 * Created by dvelasquez on 31/03/17.
 */
public class SplashActivity extends BelatrixBaseActivity {

    @Inject
    Cache cache;

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAppUpdated();
        if (cache.isFirstTime()) {
            startActivity(CitySelectionActivity.makeIntent(this));
            cache.updateFirstTime();
        } else {
            startActivity(MainActivity.makeIntent(this));
        }
        finish();
    }

    private void checkAppUpdated(){
        int versionCode = cache.getVersionCode();
        if(versionCode != BuildConfig.VERSION_CODE) {
            //on app updated
            cache.deleteVotes();
        }
        cache.saveVersionCode(BuildConfig.VERSION_CODE);
    }
}