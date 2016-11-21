package com.mauri.android.flickrexample.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mauri.android.flickrexample.R;
import com.mauri.android.flickrexample.adapters.FlickrImagesAdapter;
import com.mauri.android.flickrexample.app.FlickrExampleApp;
import com.mauri.android.flickrexample.app.dependencyinjection.modules.MainActivityModule;
import com.mauri.android.flickrexample.models.Photo;
import com.mauri.android.flickrexample.presenters.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int CARD_VIEW = 1;
    private static final int GRID_VIEW = 3;

    @BindView(R.id.flickr_view)
    RecyclerView mFlickrView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    @Inject
    MainActivityPresenter mainActivityPresenter;
    private FlickrImagesAdapter flickrAdapter;

    private GridLayoutManager mGridLayoutManager;
    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FlickrExampleApp.get(this).getNetworkComponent().plus(new MainActivityModule(this)).inject(this);
        // Hack to center the title with ActionBar buttons
        ActionBar.LayoutParams layoutParams = ((ActionBar.LayoutParams) getSupportActionBar().getCustomView().getLayoutParams());
        layoutParams.leftMargin = Math.round(getResources().getDimension(R.dimen.title_padding));
        mLoading = true;
        mainActivityPresenter.getRecentImages();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainActivityPresenter.resetRecentImages();
            }
        });

        mFlickrView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, GRID_VIEW);
        mFlickrView.setLayoutManager(mGridLayoutManager);
        flickrAdapter = new FlickrImagesAdapter(new ArrayList<Photo>(), this);
        mFlickrView.setAdapter(flickrAdapter);

        mFlickrView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int mVisibleItemCount = mGridLayoutManager.getChildCount();
                    int mTotalItemCount = mGridLayoutManager.getItemCount();
                    int mPastVisibleItems = mGridLayoutManager.findFirstVisibleItemPosition();


                    if (!mLoading) {
                        if ((mVisibleItemCount + mPastVisibleItems) >= mTotalItemCount) {
                            mLoading = true;
                            mSwipeLayout.setRefreshing(true);
                            mainActivityPresenter.getRecentImages();
                        }
                    }
                }
            }
        });
    }

    public void loadFlickrView(List<Photo> flickrImages, int page) {
        mLoading = false;
        if (page == 1) flickrAdapter.clear();
        flickrAdapter.addAll(flickrImages);
        if (mSwipeLayout.isRefreshing())
            mSwipeLayout.setRefreshing(false);

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
