package com.mauri.android.flickrexample.network.responses;

import com.mauri.android.flickrexample.models.Photo;

/**
 * Created by mauri on 17/11/16.
 */

public class GetPhotoInfoResponse {
    private String stat;
    private Photo photo;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
