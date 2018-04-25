package com.oohdev.oohreminder.core.api.music;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.MovieDataObject;
import com.oohdev.oohreminder.core.api.SearchDataObject;
import com.oohdev.oohreminder.core.api.SearchProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicSearchProvider implements SearchProvider {
    @Override
    public void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback) {
        callback.onSuccess(new ArrayList<SearchDataObject>(Arrays.asList(new MovieDataObject(searchQuery, "TEST DIR", "TEST DESC"))), "");
    }

    @Override
    public void unSubscribe() {

    }

    @NonNull
    @Override
    public SearchDataObject buildSearchDataObject(@NonNull String title) {
        return null;
    }
}
