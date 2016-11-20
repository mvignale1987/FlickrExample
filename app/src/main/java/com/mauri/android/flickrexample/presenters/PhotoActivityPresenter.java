package com.mauri.android.flickrexample.presenters;

import com.mauri.android.flickrexample.activities.PhotoActivity;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mauri on 17/11/16.
 */

public class PhotoActivityPresenter implements Observer<GetPhotoInfoResponse> {

    private PhotoActivity photoActivity;
    private FlickrApi flickrApi;

    public PhotoActivityPresenter(PhotoActivity view, FlickrApi flickrApi){
        this.flickrApi = flickrApi;
        this.photoActivity = view;
    }

    public void getPhotoInfo(String photo_id){
        flickrApi.getPhotoInfo(photo_id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Timber.d("response");
    }

    @Override
    public void onNext(GetPhotoInfoResponse getPhotoInfoResponse) {
        photoActivity.loadPhotoInfo(getPhotoInfoResponse.getPhoto());
    }
}
