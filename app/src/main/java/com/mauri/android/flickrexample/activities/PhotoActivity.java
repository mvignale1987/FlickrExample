package com.mauri.android.flickrexample.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.models.Photo;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mauri on 19/11/16.
 */

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.close_icon)
    ImageView mCloseIcon;
    @BindView(R.id.photo_username)
    TextView mUserName;
    @BindView(R.id.photo_location)
    TextView mLocation;
    @BindView(R.id.photo_comments)
    TextView mComments;
    @BindView(R.id.full_image)
    ImageView mFullImage;

    private static final String PHOTO = "photo";

    public static void newInstance(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PHOTO, Parcels.wrap(photo));
        context.startActivity(intent);
    }

    private Photo mPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        mPhoto = Parcels.unwrap(getIntent().getParcelableExtra(PHOTO));
        Glide.with(FlickrExampleApp.get(this)).load(mPhoto.getUrl_c()).into((mFullImage));
        mUserName.setText(mPhoto.getFull_owner().getUsername());
        mLocation.setText(mPhoto.getFull_owner().getLocation());
        mComments.setText(mPhoto.getComments() + " Comments");

    }

    @OnClick(R.id.close_icon)
    void closePhoto(){
        finish();
    }
}
