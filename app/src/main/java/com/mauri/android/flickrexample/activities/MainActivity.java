package com.mauri.android.flickrexample.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.adapters.FlickrImagesAdapter;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.MainActivityModule;
import com.mauri.android.flickrexample.models.Photo;
import com.mauri.android.flickrexample.presenters.MainActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.flickr_view)
    RecyclerView mFlickrView;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_center_title);
        ButterKnife.bind(this);
        mFlickrView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mFlickrView.setLayoutManager(layoutManager);
        FlickrExampleApp.get(this).getNetworkComponent().plus(new MainActivityModule(this)).inject(this);
        mainActivityPresenter.getRecentImages();
    }

    public void loadFlickrView(List<Photo> flickrImages){
        FlickrImagesAdapter flickrAdapter = new FlickrImagesAdapter(flickrImages,this);
        mFlickrView.setAdapter(flickrAdapter);
    }
}
