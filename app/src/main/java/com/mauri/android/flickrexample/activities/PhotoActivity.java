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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mauri on 19/11/16.
 */

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.close_icon)
    ImageView mCloseIcon;

    @BindView(R.id.full_image)
    ImageView mFullImage;

    private static final String PHOTO_URL = "photo_url";
    private static final String PHOTO_ID = "photo_id";

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, PhotoActivity.class);
//        intent.putExtra(PHOTO_ID, photo_id);
//        intent.putExtra(PHOTO_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close_icon)
    void closePhoto(){
        finish();
    }
}
