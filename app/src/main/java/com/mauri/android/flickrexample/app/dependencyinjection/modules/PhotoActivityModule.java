package com.mauri.android.flickrexample.app.dependencyinjection.modules;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.activities.PhotoActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.presenters.MainActivityPresenter;
import com.mauri.android.flickrexample.presenters.PhotoActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mauri on 17/11/16.
 */
@Module
public class PhotoActivityModule {
    private PhotoActivity photoActivity;

    public PhotoActivityModule(PhotoActivity photoActivity) {
        this.photoActivity = photoActivity;
    }

    @Provides
    @ActivityScope
    public PhotoActivityPresenter providesMainActivityPresenter(FlickrApi flickrApi) {
        return new PhotoActivityPresenter(photoActivity, flickrApi);
    }
}
