package com.github.programmerr47.vkgroups;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.github.programmerr47.vkgroups.imageloading.ImageCache;
import com.github.programmerr47.vkgroups.imageloading.ImageFetcher;
import com.github.programmerr47.vkgroups.imageloading.ImageWorker;
import com.vk.sdk.VKSdk;

/**
 * @author Michael Spitsin
 * @since 2016-01-04
 */
public class VKGroupApplication extends Application {

    private static Context appContext;
    private static ImageWorker imageWorker;
    private static Handler uiHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageCache.ImageCacheParams imageCacheParams = initImageCacheParams();

        appContext = getApplicationContext();
        imageWorker = new ImageFetcher(appContext, imageCacheParams);
        uiHandler = new Handler();

        VKSdk.initialize(appContext);
    }

    private ImageCache.ImageCacheParams initImageCacheParams() {
        return new ImageCache.ImageCacheParams(getApplicationContext(), "images");
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static ImageWorker getImageWorker() {
        return imageWorker;
    }

    public static Handler getUiHandler() {
        return uiHandler;
    }
}
