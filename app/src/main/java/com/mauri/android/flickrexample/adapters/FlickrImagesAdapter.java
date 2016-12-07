package com.mauri.android.flickrexample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.activities.PublicationActivity;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.models.Photo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mauri on 17/11/16.
 */

public class FlickrImagesAdapter extends RecyclerView.Adapter<FlickrImagesAdapter.ViewHolder> {

    private List<Photo> flickrImages;
    private Context context;

    public FlickrImagesAdapter(List<Photo> flickrImages, Context ctx) {
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
        holder.progress_bar.setVisibility(View.VISIBLE);
        holder.bindPhoto(flickrImages.get(position));
    }

    @Override
    public int getItemCount() {
        return flickrImages.size();
    }

    public void clear() {
        flickrImages.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Photo> list) {
        flickrImages.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.flickr_image)
        ImageView flickr_image;
        @BindView(R.id.progressBar)
        ProgressBar progress_bar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindPhoto(final Photo photo) {
            Glide.with(FlickrExampleApp.get(context))
                    .load(photo.getUrl_c())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progress_bar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progress_bar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.notfound)
                    .into(flickr_image);
            flickr_image.setOnClickListener(view -> PublicationActivity.newInstance(context, view, photo.getUrl_c(), photo.getId()));
        }
    }
}
