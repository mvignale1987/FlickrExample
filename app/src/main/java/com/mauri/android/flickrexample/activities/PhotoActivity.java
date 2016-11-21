package com.mauri.android.flickrexample.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.app.Constants;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PhotoActivityModule;
import com.mauri.android.flickrexample.models.Owner;
import com.mauri.android.flickrexample.models.Photo;
import com.mauri.android.flickrexample.presenters.PhotoActivityPresenter;
import com.mauri.android.flickrexample.utils.BindingUtils;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mauri on 19/11/16.
 */

public class PhotoActivity extends BaseActivity {


    private static final String PHOTO_URL = "photo_url";
    private static final String PHOTO_ID = "photo_id";

    @BindView(R.id.user_profile_icon)
    ImageView mUserIcon;
    @BindView(R.id.detail_image_view)
    ImageView mDetailImageView;
    @BindView(R.id.photo_username)
    TextView mUsername;
    @BindView(R.id.photo_location)
    TextView mPhotoLocation;
    @BindView(R.id.photo_title_and_description)
    TextView mPhotoTitleDescription;
    @BindView(R.id.photo_date)
    TextView mPhotoDate;

    @Inject
    PhotoActivityPresenter mPhotoActivityPresenter;
    @Inject
    BindingUtils mBindingUtils;


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
        Glide.with(FlickrExampleApp.get(this)).load(getIntent().getStringExtra(PHOTO_URL)).into((mDetailImageView));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPhotoActivityPresenter.getPhotoInfo(getIntent().getStringExtra(PHOTO_ID));
    }

    public void loadPhotoInfo(Photo photo) {
        Owner owner = photo.getFull_owner();
        mUsername.setText(owner.getUsername());
        if (!TextUtils.isEmpty(owner.getLocation()))
            mPhotoLocation.setText(owner.getLocation());
        else
            mPhotoLocation.setText(R.string.location_placeholder);
        if ("0".equals(owner.getIconserver())) {
            Glide.with(FlickrExampleApp.get(this)).load(R.drawable.buddyicon).asBitmap().centerCrop().into(mBindingUtils.getRoundedBitmapImageView(mUserIcon));
        } else {
            String iconUrl = String.format("http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg", owner.getIconfarm(), owner.getIconserver(), owner.getNsid());
            Glide.with(FlickrExampleApp.get(this)).load(iconUrl).asBitmap().centerCrop().into(mBindingUtils.getRoundedBitmapImageView(mUserIcon));
        }
        mPhotoTitleDescription.setText(photo.getTitle() + "\n" + photo.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
        mPhotoDate.setText(sdf.format(photo.getPhoto_date()));

    }

    @OnClick(R.id.detail_image_view)
    void onImageClick(View view) {
        Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
