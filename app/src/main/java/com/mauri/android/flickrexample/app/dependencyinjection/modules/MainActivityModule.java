package com.mauri.android.flickrexample.app.dependencyinjection.modules;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;
import com.mauri.android.flickrexample.interactors.GetRecentPhotosInteractor;
import com.mauri.android.flickrexample.interactors.SearchPhotosInteractor;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.presenters.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mauri on 17/11/16.
 */
@Module
public class MainActivityModule {
    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Provides
    @ActivityScope
    public GetRecentPhotosInteractor providesGetRecentPhotosInteractor(FlickrApi flickrApi){
        return new GetRecentPhotosInteractor(flickrApi);
    }
    @Provides
    @ActivityScope
    public SearchPhotosInteractor providesSearchPhotosInteractor(FlickrApi flickrApi){
        return new SearchPhotosInteractor(flickrApi);
    }
    @Provides
    @ActivityScope
    public MainActivityPresenter providesMainActivityPresenter(SearchPhotosInteractor searchPhotosInteractor, GetRecentPhotosInteractor getRecentPhotosInteractor){
        return new MainActivityPresenter(mainActivity, getRecentPhotosInteractor,searchPhotosInteractor);
    }


}
