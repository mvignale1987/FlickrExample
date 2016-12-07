package com.mauri.android.flickrexample.network.responses;

import com.mauri.android.flickrexample.models.Photos;

/**
 * Created by mauri on 17/11/16.
 */

public class GetRecentResponse {
    private String stat;
    private Photos photos;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
