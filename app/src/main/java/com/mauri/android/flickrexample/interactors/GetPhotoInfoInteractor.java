package com.mauri.android.flickrexample.interactors;

import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;
import com.mauri.android.flickrexample.presenters.MainActivityPresenter;
import com.mauri.android.flickrexample.presenters.PublicationActivityPresenter;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mauri on 21/11/16.
 */

public class GetPhotoInfoInteractor implements Observer<GetPhotoInfoResponse> {

    private FlickrApi flickrApi;
    private PublicationActivityPresenter mPresenter;

    @Inject
    public GetPhotoInfoInteractor(FlickrApi flickrApi){
        this.flickrApi = flickrApi;
    }

    public void getData(String photo_id){
        flickrApi.getPhotoInfo(photo_id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    // TODO: this should be a generic Presenter, a Service is an atomic use case from business logic, it can be called from anywhere
    public void setPresenter(PublicationActivityPresenter publicationActivityPresenter){
        this.mPresenter = publicationActivityPresenter;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mPresenter.onErrorResponse(e);
    }

    @Override
    public void onNext(GetPhotoInfoResponse getPhotoInfoResponse) {
        mPresenter.onGetPhotoResponse(getPhotoInfoResponse);
    }
}
