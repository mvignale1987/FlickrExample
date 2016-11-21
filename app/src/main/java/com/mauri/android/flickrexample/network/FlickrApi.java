package com.mauri.android.flickrexample.network;

import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;
import com.mauri.android.flickrexample.network.responses.GetRecentResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mauri on 17/11/16.
 */

public interface FlickrApi {

    @GET("?method=flickr.photos.getRecent")
    Observable<GetRecentResponse> getRecentPhotos(@Query("page") int page, @Query("extras") String url_type);

    @GET("?method=flickr.photos.getInfo")
    Observable<GetPhotoInfoResponse> getPhotoInfo(@Query("photo_id") String id);

}
