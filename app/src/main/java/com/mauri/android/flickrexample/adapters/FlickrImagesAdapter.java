package com.mauri.android.flickrexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.models.Photo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mauri on 17/11/16.
 */

public class FlickrImagesAdapter extends RecyclerView.Adapter<FlickrImagesAdapter.ViewHolder>{

    private List<Photo> flickrImages;
    private Context context;
    public FlickrImagesAdapter(List<Photo> flickrImages, Context ctx){
        this.flickrImages = flickrImages;
        this.context = ctx;
    }

    @Override
    public FlickrImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImagesAdapter.ViewHolder holder, int position) {
        Photo image = flickrImages.get(position);
        String url = String.format("https://farm%s.static.flickr.com/%s/%s_%s_c.jpg", image.getFarm(), image.getServer(), image.getId(), image.getSecret());
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.flickr_image);
    }

    @Override
    public int getItemCount() {
        return flickrImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.flickr_image)
        ImageView flickr_image;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
