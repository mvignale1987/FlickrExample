package com.mauri.android.flickrexample.app.dependencyinjection.modules;

import com.mauri.android.flickrexample.activities.PublicationActivity;
import com.mauri.android.flickrexample.app.dependencyinjection.scopes.ActivityScope;
import com.mauri.android.flickrexample.interactors.GetPhotoInfoInteractor;
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
    public PublicationActivityPresenter providesPublicationActivityPresenter(GetPhotoInfoInteractor getPhotoInfoInteractor) {
        PublicationActivityPresenter publicationActivityPresenter = new PublicationActivityPresenter(publicationActivity, getPhotoInfoInteractor);
        getPhotoInfoInteractor.setListener(publicationActivityPresenter);
        return publicationActivityPresenter;
    }
}
