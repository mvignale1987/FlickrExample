package com.mauri.android.flickrexample.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    private static final int CARD_VIEW = 1;
    private static final int GRID_VIEW = 3;

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
        mFlickrView.setLayoutManager(new GridLayoutManager(this, GRID_VIEW));
        FlickrExampleApp.get(this).getNetworkComponent().plus(new MainActivityModule(this)).inject(this);
        mainActivityPresenter.getRecentImages();
    }

    public void loadFlickrView(List<Photo> flickrImages) {
        FlickrImagesAdapter flickrAdapter = new FlickrImagesAdapter(flickrImages, this);
        mFlickrView.setAdapter(flickrAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_name) {
            toggleSpanCount(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toggleSpanCount(MenuItem item) {
        int columnCount = ((GridLayoutManager) mFlickrView.getLayoutManager()).getSpanCount();
        if (columnCount == GRID_VIEW) {
            ((GridLayoutManager) mFlickrView.getLayoutManager()).setSpanCount(CARD_VIEW);
            item.setIcon(R.drawable.grid_icon);

        } else {
            ((GridLayoutManager) mFlickrView.getLayoutManager()).setSpanCount(GRID_VIEW);
            item.setIcon(R.drawable.card_icon);
        }
    }
}
