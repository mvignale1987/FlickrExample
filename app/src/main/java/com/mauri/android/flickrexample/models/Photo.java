package com.mauri.android.flickrexample.models;

/**
 * Created by mauri on 17/11/16.
 */

public class Photo {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private String url_c;
    private int ispublic;
    private int isfriend;
    private int isfamily;
    // for flickr.photos.getInfo
    private Owner full_owner;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public String getUrl_c() {
        return url_c;
    }

    public void setUrl_c(String url_c) {
        this.url_c = url_c;
    }

    public Owner getFull_owner() {
        return full_owner;
    }

    public void setFull_owner(Owner full_owner) {
        this.full_owner = full_owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
