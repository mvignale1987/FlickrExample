package com.mauri.android.flickrexample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mauri.android.flickrexample.app.FlickrExampleApp;

import javax.inject.Inject;

/**
 * Created by mauri on 20/11/16.
 */
public class BindingUtils {

    private Context ctx;

    @Inject
    public BindingUtils(FlickrExampleApp flickrExampleApp){
        this.ctx = flickrExampleApp.getApplicationContext();
    }


    public BitmapImageViewTarget getRoundedBitmapImageView(final ImageView imageView){
        return new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        };
    }
}
