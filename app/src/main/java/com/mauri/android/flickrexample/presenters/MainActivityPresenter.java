package com.mauri.android.flickrexample.presenters;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mauri on 17/11/16.
 */

public class MainActivityPresenter implements Observer<GetRecentResponse> {

    private MainActivity mainActivity;
    private FlickrApi flickrApi;
    private int mCurrentPage;

    public MainActivityPresenter(MainActivity view, FlickrApi flickrApi){
        this.flickrApi = flickrApi;
        this.mainActivity = view;
        this.mCurrentPage = 1;
    }

    public void getRecentImages(){
        flickrApi.getRecentPhotos(mCurrentPage,"url_c").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void resetRecentImages(){
        mCurrentPage = 1;
        getRecentImages();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Timber.d("response");
    }

    @Override
    public void onNext(GetRecentResponse getRecentResponse) {
        mCurrentPage++;
        mainActivity.loadFlickrView(getRecentResponse.getPhotos().getPhoto(),getRecentResponse.getPhotos().getPage());
    }
}
