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
package com.belatrix.events.utils.media.loaders;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * @author Carlos Piñan
 */
public interface ImageLoader {

    interface Callback {
        void onSuccess();

        void onFailure();
    }

    enum ImageTransformation {
        BORDERED_CIRCLE,
        CIRCLE
    }

    enum ScaleType {
        CENTER_CROP,
        FIT_CENTER
    }

    void loadFromBitmap(byte[] bitmapImg, ImageView imageView, ImageTransformation transformation, Callback callback, Drawable placeholder, ScaleType scaleType);

    void loadFromRes(int resId, ImageView imageView, Drawable placeholder);

    void loadFromRes(int resId, ImageView imageView, ImageTransformation transformation, Drawable placeholder, ScaleType scaleType);

    void loadFromRes(int resId, ImageView imageView, ImageTransformation transformation, ImageLoader.Callback callback, Drawable placeholder, ScaleType scaleType);

    void loadFromUrl(String url, ImageView imageView, Drawable placeholder);

    void loadFromUrl(String url, ImageView imageView, ImageTransformation transformation, Drawable placeholder, ScaleType scaleType);

    void loadFromUrl(String url, ImageView imageView, ImageTransformation transformation, ImageLoader.Callback callback, Drawable placeholder, ScaleType scaleType);

    void loadFromPath(String path, ImageView imageView, ImageTransformation transformation, Drawable placeholder, ScaleType scaleType);

    void loadFromPath(String path, ImageView imageView, ImageTransformation transformation, ImageLoader.Callback callback, Drawable placeholder, ScaleType scaleType);

}