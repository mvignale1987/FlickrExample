package com.mauri.android.flickrexample.app;

import android.app.Application;
import android.content.Context;

import com.mauri.android.flickrexample.BuildConfig;
import com.mauri.android.flickrexample.app.dependencyinjection.components.DaggerNetworkComponent;
import com.mauri.android.flickrexample.app.dependencyinjection.components.NetworkComponent;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.NetworkModule;

import timber.log.Timber;

/**
 * Created by mauri on 17/11/16.
 */

public class FlickrExampleApp extends Application {

    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkComponent = DaggerNetworkComponent.builder().networkModule(new NetworkModule(this,Constants.FLICKR_ENDPOINT)).build();
        Timber.plant(new Timber.DebugTree());
    }

    public static FlickrExampleApp get(Context context) {
        return ((FlickrExampleApp) context.getApplicationContext());
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }
}
