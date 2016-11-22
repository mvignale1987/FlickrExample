package com.mauri.android.flickrexample.app.dependencyinjection.modules;

import com.mauri.android.flickrexample.activities.PublicationActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;
import com.mauri.android.flickrexample.interactors.GetPhotoInfoInteractor;
import com.mauri.android.flickrexample.interactors.SearchPhotosInteractor;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.presenters.PublicationActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mauri on 17/11/16.
 */
@Module
public class PublicationActivityModule {
    private PublicationActivity publicationActivity;

    public PublicationActivityModule(PublicationActivity publicationActivity) {
        this.publicationActivity = publicationActivity;
    }

    @Provides
    @ActivityScope
    public GetPhotoInfoInteractor providesGetPhotoInfoInteractor(FlickrApi flickrApi){
        return new GetPhotoInfoInteractor(flickrApi);
    }

    @Provides
    @ActivityScope
    public PublicationActivityPresenter providesPublicationActivityPresenter(GetPhotoInfoInteractor getPhotoInfoInteractor) {
        return new PublicationActivityPresenter(publicationActivity, getPhotoInfoInteractor);
    }
}
