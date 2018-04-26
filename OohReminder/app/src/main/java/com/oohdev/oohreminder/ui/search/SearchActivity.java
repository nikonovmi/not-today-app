package com.oohdev.oohreminder.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.api.SearchDataObject;
import com.oohdev.oohreminder.core.api.SearchFailure;
import com.oohdev.oohreminder.core.api.SearchProvider;
import com.oohdev.oohreminder.ui.App;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchActivity extends AppCompatActivity implements SearchProvider.Callback {
    public static String SEARCH_RESULT_KEY = "SEARCH RESULT KEY";

    @Inject
    SearchProvider mSearchProvider;

    private Toolbar mToolbar;
    private SearchBarView mSearchBar;
    private EditText mSearchQueryEditText;
    private SearchRecyclerAdapter mSearchRecyclerAdapter;
    private RecyclerView mSearchRecyclerView;
    private Observable<String> mSearchQueryObservable;
    private FrameLayout mRecyclerFrame;
    private FrameLayout mProgressFrame;
    private ContentLoadingProgressBar mProgressBar;
    private SearchRecyclerAdapter.AdapterOnClickListener mAdapterOnClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Dagger
        App.get().getSearchComponent().inject(this);

        // Set a Toolbar instead of ActionBar.
        mToolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
        Assert.assertNotNull(getSupportActionBar());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Set up searchBar
        mSearchBar = mToolbar.findViewById(R.id.search_bar);
        mSearchQueryEditText = mSearchBar.findViewById(R.id.search_bar_input);
        mSearchBar.setOnBackClickListener(v -> onBackPressed());
        mSearchBar.setOnClearClickListener(v -> clearQuery());
        mSearchQueryObservable = RxTextView.textChanges(mSearchQueryEditText)
                .debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString);

        // Set up the RecyclerView
        mAdapterOnClickListener = this::finishWithResult;
        mSearchRecyclerView = findViewById(R.id.search_recycler);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecyclerAdapter = new SearchRecyclerAdapter(this, mAdapterOnClickListener);
        mSearchRecyclerView.setAdapter(mSearchRecyclerAdapter);

        mRecyclerFrame = findViewById(R.id.search_recycler_frame);
        mProgressFrame = findViewById(R.id.search_progress_frame);
        mProgressBar = findViewById(R.id.search_progress_bar);
    }

    private void clearQuery() {
        mSearchRecyclerAdapter.clear();
        hideProgressBar();
        mSearchQueryEditText.setText("");
    }

    private void finishWithResult(@NonNull SearchDataObject searchDataObject) {
        Intent intent = new Intent();
        intent.putExtra(SEARCH_RESULT_KEY, searchDataObject);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressBar();
        subscribeQueryChanges();
    }

    private void subscribeQueryChanges() {
        mSearchQueryObservable.subscribe(newText -> {
            mSearchRecyclerAdapter.clear();
            if (newText.trim().length() < 5) {
                hideProgressBar();
                if (newText.trim().length() > 2) {
                    mSearchRecyclerAdapter.addItems(Collections.singletonList(mSearchProvider.buildSearchDataObject(newText)));
                }
            } else {
                showProgressBar();
                mSearchProvider.dismissPreviousAndRequestNew(newText, this);
            }
        });
    }

    @Override
    protected void onPause() {
        mSearchProvider.unSubscribe();
        super.onPause();
    }

    @Override
    public void onSuccess(@NonNull List<? extends SearchDataObject> searchResults, @NonNull String searchQuery) {
        mSearchRecyclerAdapter.clear();
        List<SearchDataObject> searchDataObjects = new ArrayList<>(searchResults);
        searchDataObjects.add(mSearchProvider.buildSearchDataObject(searchQuery));
        mSearchRecyclerAdapter.addItems(searchDataObjects);
        hideProgressBar();
    }

    @Override
    public void onFailure(@NonNull SearchFailure failure, @NonNull SearchDataObject plainSearchQuery) {
        mSearchRecyclerAdapter.clear();
        mSearchRecyclerAdapter.addItems(Collections.singletonList(plainSearchQuery));
        //TODO understand failure
        hideProgressBar();
    }

    private void hideProgressBar() {
        mRecyclerFrame.setVisibility(View.VISIBLE);
        mProgressFrame.setVisibility(View.GONE);
        mProgressBar.hide();
    }

    private void showProgressBar() {
        mRecyclerFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.VISIBLE);
        mProgressBar.show();
    }
}
