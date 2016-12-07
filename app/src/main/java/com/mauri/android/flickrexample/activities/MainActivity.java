package com.mauri.android.flickrexample.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int CARD_VIEW = 1;
    private static final int GRID_VIEW = 3;

    @BindView(R.id.flickr_view)
    RecyclerView mFlickrView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.button_search)
    Button mSearchButton;
    @BindView(R.id.search_photos)
    EditText mSearchField;

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
        mainActivityPresenter.resetRecentImages();
        mSwipeLayout.setOnRefreshListener(() -> mainActivityPresenter.resetRecentImages());

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
                            mainActivityPresenter.getLastPaginatedService();
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
        flickrAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.button_search)
    public void onClick(){
        String searchTerm = mSearchField.getText().toString();
        if (!TextUtils.isEmpty(searchTerm)){
            mSwipeLayout.setRefreshing(true);
            mainActivityPresenter.searchImages(searchTerm);
            mSearchField.clearFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchField.getWindowToken(),0);
        } else {
            showError("You must search for a term!!");
        }

    }
}
