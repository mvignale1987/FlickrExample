package com.mauri.android.flickrexample.interactors;

import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mauri on 21/11/16.
 */

public class GetRecentPhotosInteractor implements Observer<GetRecentResponse> {

    private FlickrApi flickrApi;
    private RecentPhotosListener mListener;

    public interface RecentPhotosListener{
        void onGetRecentResponse(GetRecentResponse getRecentResponse);
        void onErrorResponse(Throwable e);
    }


    public GetRecentPhotosInteractor(FlickrApi flickrApi){
        this.flickrApi = flickrApi;
    }

    public void getData(int currentPage){
        flickrApi.getRecentPhotos(currentPage, "url_c")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void setListener(RecentPhotosListener recentPhotosListener){
        this.mListener = recentPhotosListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mListener.onErrorResponse(e);
    }

    @Override
    public void onNext(GetRecentResponse getRecentResponse) {
        mListener.onGetRecentResponse(getRecentResponse);
    }
}
