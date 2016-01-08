package com.github.programmerr47.vkgroups;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * @author Michael Spitsin
 * @since 2016-01-04
 */
public class VKGroupApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(getApplicationContext());
    }
}
