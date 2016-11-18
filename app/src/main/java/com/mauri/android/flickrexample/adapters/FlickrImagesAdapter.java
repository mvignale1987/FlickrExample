package com.mauri.android.flickrexample.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mauri.android.flickrexample.models.Photo;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by mauri on 17/11/16.
 */

public class FlickrImagesAdapter extends RecyclerView.Adapter<FlickrImagesAdapter.ViewHolder>{

    private List<Photo> flickrImages;

    public FlickrImagesAdapter(List<Photo> flickrImages){
        this.flickrImages = flickrImages;
    }

    @Override
    public FlickrImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FlickrImagesAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return flickrImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
