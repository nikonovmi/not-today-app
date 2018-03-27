package com.oohdev.oohreminder.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oohdev.oohreminder.R;

import junit.framework.Assert;

public class SearchActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private SearchBarView mSearchBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set a Toolbar instead of ActionBar.
        mToolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
        Assert.assertNotNull(getSupportActionBar());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSearchBar = mToolbar.findViewById(R.id.search_bar);
        mSearchBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
