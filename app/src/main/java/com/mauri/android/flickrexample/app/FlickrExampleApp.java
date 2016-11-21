package com.mauri.android.flickrexample.app;

import android.app.Application;
import android.content.Context;

import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.app.dependencyinjection.components.DaggerNetworkComponent;
import com.mauri.android.flickrexample.app.dependencyinjection.components.NetworkComponent;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.NetworkModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mauri on 17/11/16.
 */

public class FlickrExampleApp extends Application {

    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Bryant-Regular.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

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
