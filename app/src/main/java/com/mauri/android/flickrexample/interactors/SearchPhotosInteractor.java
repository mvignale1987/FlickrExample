package com.mauri.android.flickrexample.interactors;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.network.FlickrApi;
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
    private MainActivityPresenter mPresenter;

    public SearchPhotosInteractor(FlickrApi flickrApi){
        this.flickrApi = flickrApi;
    }

    public void getData(int page,String text){
        flickrApi.searchPhotos(page,text, "url_c")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    // TODO: this should be a generic Presenter, a Service is an atomic use case from business logic, it can be called from anywhere
    public void setPresenter(MainActivityPresenter mainActivityPresenter){
        this.mPresenter = mainActivityPresenter;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mPresenter.onErrorResponse(e);
    }

    @Override
    public void onNext(GetRecentResponse getRecentResponse) {
        mPresenter.onSearchPhotoResponse(getRecentResponse);
    }
}
