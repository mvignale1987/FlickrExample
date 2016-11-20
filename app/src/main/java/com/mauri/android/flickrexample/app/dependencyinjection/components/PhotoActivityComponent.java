package com.mauri.android.flickrexample.app.dependencyinjection.components;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.activities.PhotoActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.MainActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PhotoActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;
import com.mauri.android.flickrexample.models.Photo;

import dagger.Subcomponent;

/**
 * Created by mauri on 17/11/16.
 */
@Subcomponent(modules = {PhotoActivityModule.class})
@ActivityScope
public interface PhotoActivityComponent {

    void inject(PhotoActivity photoActivity);
}
