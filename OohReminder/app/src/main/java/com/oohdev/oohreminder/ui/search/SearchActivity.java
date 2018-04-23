package com.oohdev.oohreminder.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.api.search.SearchDataObject;
import com.oohdev.oohreminder.core.api.search.SearchFailure;
import com.oohdev.oohreminder.core.api.search.SearchProvider;
import com.oohdev.oohreminder.ui.App;

import junit.framework.Assert;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchActivity extends AppCompatActivity implements SearchProvider.Callback {
    @Inject
    SearchProvider mSearchProvider;

    private Toolbar mToolbar;
    private SearchBarView mSearchBar;
    private EditText mSearchQueryEditText;
    private SearchRecyclerAdapter mSearchRecyclerAdapter;
    private RecyclerView mSearchRecyclerView;
    private Observable<String> mSearchQueryObservable;

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
                .filter(charSequence -> charSequence.length() > 2)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString);

        // Set up the RecyclerView
        mSearchRecyclerView = findViewById(R.id.search_recycler);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecyclerAdapter = new SearchRecyclerAdapter(this);
        mSearchRecyclerView.setAdapter(mSearchRecyclerAdapter);

    }

    private void clearQuery() {
        mSearchRecyclerAdapter.clear();
        mSearchQueryEditText.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribeQueryChanges();

    }

    private void subscribeQueryChanges() {
        mSearchQueryObservable.subscribe(newText -> mSearchProvider.dismissPreviousAndRequestNew(newText, this));
    }

    @Override
    protected void onPause() {
        mSearchProvider.unSubscribe();
        super.onPause();
    }

    @Override
    public void onSuccess(@NonNull List<SearchDataObject> searchResults, @NonNull SearchDataObject plainSearchQuery) {
        searchResults.add(plainSearchQuery);
        mSearchRecyclerAdapter.clear();
        mSearchRecyclerAdapter.addItems(searchResults);
    }

    @Override
    public void onFailure(@NonNull SearchFailure failure, @NonNull SearchDataObject plainSearchQuery) {
        Toast.makeText(this, failure.toString(), Toast.LENGTH_LONG).show();
        mSearchRecyclerAdapter.clear();
        mSearchRecyclerAdapter.addItems(Collections.singletonList(plainSearchQuery));
    }

}
