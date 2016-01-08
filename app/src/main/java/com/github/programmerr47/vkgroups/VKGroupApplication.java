package com.github.programmerr47.vkgroups;

import android.app.Application;
import android.content.Context;

import com.vk.sdk.VKSdk;

/**
 * @author Michael Spitsin
 * @since 2016-01-04
 */
public class VKGroupApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();
        VKSdk.initialize(appContext);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
