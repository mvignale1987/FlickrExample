package com.mauri.android.flickrexample.presenters;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.interactors.GetRecentPhotosInteractor;
import com.mauri.android.flickrexample.interactors.SearchPhotosInteractor;
import com.mauri.android.flickrexample.network.FlickrApi;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mauri on 17/11/16.
 */

public class MainActivityPresenter {

    private MainActivity mainActivity;
    private GetRecentPhotosInteractor mGetRecentPhotosInteractor;
    private SearchPhotosInteractor mSearchPhotosInteractor;
    private int mCurrentPage;

    public MainActivityPresenter(MainActivity view, GetRecentPhotosInteractor getRecentPhotosInteractor, SearchPhotosInteractor searchPhotosInteractor) {
        this.mCurrentPage = 1;
        this.mainActivity = view;
        this.mGetRecentPhotosInteractor = getRecentPhotosInteractor;
        this.mSearchPhotosInteractor = searchPhotosInteractor;
        mGetRecentPhotosInteractor.setPresenter(this);
        mSearchPhotosInteractor.setPresenter(this);

    }

    public void getRecentImages() {
        mGetRecentPhotosInteractor.getData(mCurrentPage);
    }

    private void searchPhotos(String searchText) {
        mSearchPhotosInteractor.getData(searchText);
    }

    public void resetRecentImages() {
        mCurrentPage = 1;
        getRecentImages();
    }


    public void searchImages(String searchText) {
        mCurrentPage = 1;
        searchPhotos(searchText);
    }

    public void onErrorResponse(Throwable e) {
        Timber.d("response");
    }


    public void onGetRecentResponse(GetRecentResponse getRecentResponse){
        mCurrentPage++;
        mainActivity.loadFlickrView(getRecentResponse.getPhotos().getPhoto(), getRecentResponse.getPhotos().getPage());
    }

    public void onSearchPhotoResponse(GetRecentResponse getRecentResponse){
        mainActivity.loadFlickrView(getRecentResponse.getPhotos().getPhoto(), getRecentResponse.getPhotos().getPage());
    }
}
