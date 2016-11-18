package com.mauri.android.flickrexample.app.dependencyinjection.components;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.MainActivityModule;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by mauri on 17/11/16.
 */
@Subcomponent(modules = {MainActivityModule.class})
@ActivityScope
public interface MainActivityComponent {

    public void inject(MainActivity mainActivity);
}
