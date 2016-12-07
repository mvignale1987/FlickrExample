package com.mauri.android.flickrexample.interactors;

import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mauri on 21/11/16.
 */

public class GetPhotoInfoInteractor implements Observer<GetPhotoInfoResponse> {

    private FlickrApi flickrApi;
    private GetPhotoInfoListener mGetPhotoInfoListener;

    public interface GetPhotoInfoListener{
        void onGetPhotoResponse(GetPhotoInfoResponse getPhotoInfoResponse);
        void onErrorResponse(Throwable e);
    }

    public GetPhotoInfoInteractor(FlickrApi flickrApi){
        this.flickrApi = flickrApi;
    }

    public void getData(String photo_id){
        flickrApi.getPhotoInfo(photo_id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void setListener(GetPhotoInfoListener getPhotoInfoListener){
        this.mGetPhotoInfoListener = getPhotoInfoListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mGetPhotoInfoListener.onErrorResponse(e);
    }

    @Override
    public void onNext(GetPhotoInfoResponse getPhotoInfoResponse) {
        mGetPhotoInfoListener.onGetPhotoResponse(getPhotoInfoResponse);
    }
}
