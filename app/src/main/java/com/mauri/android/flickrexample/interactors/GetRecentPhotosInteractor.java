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

public class GetRecentPhotosInteractor implements Observer<GetRecentResponse> {

    private FlickrApi flickrApi;
    private MainActivityPresenter mPresenter;

    public GetRecentPhotosInteractor(FlickrApi flickrApi){
        this.flickrApi = flickrApi;
    }

    public void getData(int currentPage){
        flickrApi.getRecentPhotos(currentPage, "url_c")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    // TODO: this should be a generic Presenter, a Service is an atomic use case from business logic, it should be called from anywhere
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
        mPresenter.onGetRecentResponse(getRecentResponse);
    }
}
