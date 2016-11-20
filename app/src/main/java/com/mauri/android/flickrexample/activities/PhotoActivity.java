package com.mauri.android.flickrexample.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PhotoActivityModule;
import com.mauri.android.flickrexample.models.Photo;
import com.mauri.android.flickrexample.presenters.PhotoActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by mauri on 19/11/16.
 */

public class PhotoActivity extends BaseActivity {


    private static final String PHOTO_URL = "photo_url";
    private static final String PHOTO_ID = "photo_id";

    @BindView(R.id.detail_image_view)
    ImageView mDetailImageView;
    @Inject
    PhotoActivityPresenter mPhotoActivityPresenter;


    public static void newInstance(Context context, String url, String photo_id) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PHOTO_ID, photo_id);
        intent.putExtra(PHOTO_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        FlickrExampleApp.get(this).getNetworkComponent().plus(new PhotoActivityModule(this)).inject(this);
        Glide.with(this).load(getIntent().getStringExtra(PHOTO_URL)).into((mDetailImageView));
        mPhotoActivityPresenter.getPhotoInfo(getIntent().getStringExtra(PHOTO_ID));
    }

    public void loadPhotoInfo(Photo photo){
        Timber.d("response");
    }
}
