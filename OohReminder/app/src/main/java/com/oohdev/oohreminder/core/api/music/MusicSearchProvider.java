package com.oohdev.oohreminder.core.api.music;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.MovieDataObject;
import com.oohdev.oohreminder.core.api.SearchDataObject;
import com.oohdev.oohreminder.core.api.SearchProvider;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Music is not supported by now. This is a stub class.
 */

public class MusicSearchProvider implements SearchProvider {
    @Override
    public void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback) {
        callback.onSearchRequestSuccess(new ArrayList<SearchDataObject>(
                Arrays.asList(new MovieDataObject(searchQuery, "STUB DIR", "TEST DESC"))), "stub");
    }

    @Override
    public void dismissRequests() {

    }

    @NonNull
    @Override
    public SearchDataObject buildSearchDataObject(@NonNull String title) {
        return new MovieDataObject();
    }
}
