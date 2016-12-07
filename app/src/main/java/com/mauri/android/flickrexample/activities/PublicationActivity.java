package com.mauri.android.flickrexample.activities;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mauri on 19/11/16.
 */

public class PublicationActivity extends BaseActivity {


    private static final String PHOTO_URL = "photo_url";
    private static final String PHOTO_ID = "photo_id";


    @BindView(R.id.activity_publication)
    ScrollView mScrollView;
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
    @BindView(R.id.image_info)
    LinearLayout mImageInfo;

    @Inject
    PublicationActivityPresenter mPublicationActivityPresenter;
    @Inject
    BindingUtils mBindingUtils;

    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;
    private Drawable colorDrawable;

    private Photo mPhoto;


    public static void newInstance(Context context, View v, String url, String photo_id) {
        Intent intent = new Intent(context, PublicationActivity.class);
        intent.putExtra(PHOTO_ID, photo_id)
                .putExtra(PHOTO_URL, url);
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);
        intent.putExtra("left", screenLocation[0]).
                putExtra("top", screenLocation[1]).
                putExtra("width", v.getWidth()).
                putExtra("height", v.getHeight());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
        colorDrawable = new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        mScrollView.setBackground(colorDrawable);

        final Bundle bundle = getIntent().getExtras();
        Glide.with(FlickrExampleApp.get(this)).load(getIntent().getStringExtra(PHOTO_URL)).into((mDetailImageView));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPublicationActivityPresenter.getPhotoInfo(getIntent().getStringExtra(PHOTO_ID));
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mDetailImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mDetailImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int thumbnailTop = bundle.getInt("top");
                    int thumbnailLeft = bundle.getInt("left");
                    int thumbnailWidth = bundle.getInt("width");
                    int thumbnailHeight = bundle.getInt("height");
                    int[] screenLocation = new int[2];
                    mDetailImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / mDetailImageView.getWidth();
                    mHeightScale = (float) thumbnailHeight / mDetailImageView.getHeight();

                    enterAnimation();
                    return true;
                }
            });
        }
    }

    public void loadPhotoInfo(Photo photo) {
        mPhoto = photo;
        mPhoto.setUrl_c(getIntent().getStringExtra(PHOTO_URL));
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
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d", Locale.getDefault());
        mPhotoDate.setText(sdf.format(photo.getPhoto_date()));

    }

    @OnClick(R.id.detail_image_view)
    void onImageClick(View view) {
        PhotoActivity.newInstance(this, mPhoto);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    public void enterAnimation() {
        mDetailImageView.setPivotX(0);
        mDetailImageView.setPivotY(0);
        mDetailImageView.setScaleX(mWidthScale);
        mDetailImageView.setScaleY(mHeightScale);
        mDetailImageView.setTranslationX(mLeftDelta);
        mDetailImageView.setTranslationY(mTopDelta);

        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        mDetailImageView.animate().setDuration(300).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(300);
        bgAnim.start();

    }

    public void exitAnimation(final Runnable endAction) {

        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(300);
        bgAnim.start();
        mImageInfo.animate().setDuration(300).alpha(0.0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                mImageInfo.setVisibility(View.GONE);
            }
        });

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        mDetailImageView.animate().setDuration(300).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);


    }

}
