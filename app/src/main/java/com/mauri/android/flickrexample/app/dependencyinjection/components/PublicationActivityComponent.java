package com.mauri.android.flickrexample.app.dependencyinjection.components;

import com.mauri.android.flickrexample.activities.PublicationActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PublicationActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by mauri on 17/11/16.
 */
@Subcomponent(modules = {PublicationActivityModule.class})
@ActivityScope
public interface PublicationActivityComponent {

    void inject(PublicationActivity publicationActivity);
}
