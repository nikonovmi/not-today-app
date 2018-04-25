package com.oohdev.oohreminder.core.api.dagger;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.api.SearchProvider;

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
