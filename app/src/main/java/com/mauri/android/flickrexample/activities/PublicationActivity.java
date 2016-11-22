package com.mauri.android.flickrexample.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.PublicationActivityModule;
import com.mauri.android.flickrexample.models.Owner;
import com.mauri.android.flickrexample.models.Photo;
import com.mauri.android.flickrexample.presenters.PublicationActivityPresenter;
import com.mauri.android.flickrexample.utils.BindingUtils;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mauri on 19/11/16.
 */

public class PublicationActivity extends BaseActivity {


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
    PublicationActivityPresenter mPublicationActivityPresenter;
    @Inject
    BindingUtils mBindingUtils;

    private Photo mPhoto;

    public static void newInstance(Context context, String url, String photo_id) {
        Intent intent = new Intent(context, PublicationActivity.class);
        intent.putExtra(PHOTO_ID, photo_id);
        intent.putExtra(PHOTO_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
        ButterKnife.bind(this);
        FlickrExampleApp.get(this).getNetworkComponent().plus(new PublicationActivityModule(this)).inject(this);
        ActionBar.LayoutParams layoutParams = ((ActionBar.LayoutParams) getSupportActionBar().getCustomView().getLayoutParams());
        layoutParams.rightMargin = Math.round(getResources().getDimension(R.dimen.title_padding));

        Glide.with(FlickrExampleApp.get(this)).load(getIntent().getStringExtra(PHOTO_URL)).into((mDetailImageView));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPublicationActivityPresenter.getPhotoInfo(getIntent().getStringExtra(PHOTO_ID));
    }

    public void loadPhotoInfo(Photo photo) {
        mPhoto = photo;
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
        PhotoActivity.newInstance(this,getIntent().getStringExtra(PHOTO_URL),mUsername.getText().toString(),mPhotoLocation.getText().toString(),mPhoto.getComments());
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