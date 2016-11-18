package com.mauri.android.flickrexample.network;

import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by mauri on 17/11/16.
 */

public interface FlickrApi {

    @GET("?method=flickr.photos.getRecent")
    Observable<GetRecentResponse> getRecentPhotos();
}
