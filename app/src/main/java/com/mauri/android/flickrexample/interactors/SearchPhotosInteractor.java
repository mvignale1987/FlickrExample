package com.mauri.android.flickrexample.interactors;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;
import com.mauri.android.flickrexample.presenters.MainActivityPresenter;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mauri on 21/11/16.
 */

public class SearchPhotosInteractor implements Observer<GetRecentResponse> {

    private FlickrApi flickrApi;
    private SearchPhotoListener mListener;

    public interface SearchPhotoListener{
        void onSearchPhotoResponse(GetRecentResponse getRecentResponse);
        void onErrorResponse(Throwable e);
    }

    public SearchPhotosInteractor(FlickrApi flickrApi){
        this.flickrApi = flickrApi;
    }

    public void getData(int page,String text){
        flickrApi.searchPhotos(page,text, "url_c")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void setListener(SearchPhotoListener searchPhotoListener){
        this.mListener = searchPhotoListener;
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
        mListener.onSearchPhotoResponse(getRecentResponse);
    }
}
