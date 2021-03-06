package com.mauri.android.flickrexample.presenters;

import com.mauri.android.flickrexample.activities.MainActivity;
import com.mauri.android.flickrexample.interactors.GetRecentPhotosInteractor;
import com.mauri.android.flickrexample.interactors.SearchPhotosInteractor;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

/**
 * Created by mauri on 17/11/16.
 */

public class MainActivityPresenter implements SearchPhotosInteractor.SearchPhotoListener, GetRecentPhotosInteractor.RecentPhotosListener{

    private static final int LAST_CALLED_RECENT = 100;
    private static final int LAST_CALLED_SEARCH = 101;


    private MainActivity mainActivity;
    private GetRecentPhotosInteractor mGetRecentPhotosInteractor;
    private SearchPhotosInteractor mSearchPhotosInteractor;
    private int mCurrentPage;
    private String lastSearchedTerm = "";
    private int mLastCalledService = 0;

    public MainActivityPresenter(MainActivity view, GetRecentPhotosInteractor getRecentPhotosInteractor, SearchPhotosInteractor searchPhotosInteractor) {
        this.mCurrentPage = 1;
        this.mainActivity = view;
        this.mGetRecentPhotosInteractor = getRecentPhotosInteractor;
        this.mSearchPhotosInteractor = searchPhotosInteractor;

    }

    private void getRecentImages() { mGetRecentPhotosInteractor.getData(mCurrentPage); }

    private void searchPhotos() {
        mSearchPhotosInteractor.getData(mCurrentPage,lastSearchedTerm);
    }

    public void resetRecentImages() {
        mCurrentPage = 1;
        getRecentImages();
    }

    public void getLastPaginatedService() {
        if (mLastCalledService == LAST_CALLED_RECENT)
            getRecentImages();
        else
            searchPhotos();

    }

    public void searchImages(String searchText) {
        mCurrentPage = 1;
        lastSearchedTerm = searchText;
        searchPhotos();
    }

    @Override
    public void onErrorResponse(Throwable e) {
        mainActivity.showError(e.getLocalizedMessage());
    }

    @Override
    public void onGetRecentResponse(GetRecentResponse getRecentResponse) {
        mLastCalledService = LAST_CALLED_RECENT;
        mCurrentPage++;
        mainActivity.loadFlickrView(getRecentResponse.getPhotos().getPhoto(), getRecentResponse.getPhotos().getPage());
    }

    @Override
    public void onSearchPhotoResponse(GetRecentResponse getRecentResponse) {
        mLastCalledService = LAST_CALLED_SEARCH;
        mCurrentPage++;
        mainActivity.loadFlickrView(getRecentResponse.getPhotos().getPhoto(), getRecentResponse.getPhotos().getPage());
    }
}
