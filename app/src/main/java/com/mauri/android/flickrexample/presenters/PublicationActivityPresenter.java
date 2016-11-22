package com.mauri.android.flickrexample.presenters;

import com.mauri.android.flickrexample.activities.PublicationActivity;
import com.mauri.android.flickrexample.interactors.GetPhotoInfoInteractor;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mauri on 17/11/16.
 */

public class PublicationActivityPresenter {

    private PublicationActivity publicationActivity;
    private GetPhotoInfoInteractor mGetPhotoInfoInteractor;

    public PublicationActivityPresenter(PublicationActivity view, GetPhotoInfoInteractor getPhotoInfoInteractor){
        this.publicationActivity = view;
        this.mGetPhotoInfoInteractor = getPhotoInfoInteractor;
        mGetPhotoInfoInteractor.setPresenter(this);
    }

    public void getPhotoInfo(String photo_id){
        mGetPhotoInfoInteractor.getData(photo_id);
    }


    public void onErrorResponse(Throwable e) {
        Timber.d("response");
    }


    public void onGetPhotoResponse(GetPhotoInfoResponse getPhotoInfoResponse) {
        publicationActivity.loadPhotoInfo(getPhotoInfoResponse.getPhoto());
    }
}
