package com.oohdev.oohreminder.core.api.search.dagger;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.api.search.SearchProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {
    @NonNull
    private final SearchProvider mSearchProvider;

    public SearchModule(@NonNull SearchProvider searchProvider) {
        mSearchProvider = searchProvider;
    }

    @Provides
    @NonNull
    public SearchProvider provideSearch() {
        return mSearchProvider;
    }
}
