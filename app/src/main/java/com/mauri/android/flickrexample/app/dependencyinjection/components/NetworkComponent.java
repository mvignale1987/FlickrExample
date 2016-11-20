package com.mauri.android.flickrexample.app.dependencyinjection.components;

import com.mauri.android.flickrexample.app.dependencyinjection.modules.MainActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.NetworkModule;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PhotoActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mauri on 17/11/16.
 */
@Component(modules = {NetworkModule.class})
@Singleton
public interface NetworkComponent {

    MainActivityComponent plus(MainActivityModule mainActivityModule);
    PhotoActivityComponent plus(PhotoActivityModule photoActivityModule);
}
