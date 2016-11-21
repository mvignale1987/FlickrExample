package com.mauri.android.flickrexample.app.dependencyinjection.components;

import com.mauri.android.flickrexample.app.dependencyinjection.modules.MainActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.NetworkModule;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PublicationActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mauri on 17/11/16.
 */
@Component(modules = {NetworkModule.class})
@Singleton
public interface NetworkComponent {

    MainActivityComponent plus(MainActivityModule mainActivityModule);
    PublicationActivityComponent plus(PublicationActivityModule publicationActivityModule);
}
