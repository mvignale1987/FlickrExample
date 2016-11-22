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

    private static final String PHOTO_URL = "photo_url";
    private static final String PHOTO_USERNAME = "photo_username";
    private static final String PHOTO_LOCATION = "photo_location";
    private static final String PHOTO_COMMENTS = "photo_comments";

    public static void newInstance(Context context, String url, String username, String location, int comments) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PHOTO_URL, url);
        intent.putExtra(PHOTO_USERNAME, username);
        intent.putExtra(PHOTO_LOCATION, location);
        intent.putExtra(PHOTO_COMMENTS, comments);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        Glide.with(FlickrExampleApp.get(this)).load(getIntent().getStringExtra(PHOTO_URL)).into((mFullImage));
        mUserName.setText(getIntent().getStringExtra(PHOTO_USERNAME));
        mLocation.setText(getIntent().getStringExtra(PHOTO_LOCATION));
        mComments.setText(getIntent().getIntExtra(PHOTO_COMMENTS, 0)+ " Comments");

    }

    @OnClick(R.id.close_icon)
    void closePhoto(){
        finish();
    }
}
