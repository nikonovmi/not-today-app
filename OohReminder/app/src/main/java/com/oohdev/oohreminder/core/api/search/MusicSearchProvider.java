package com.oohdev.oohreminder.core.api.search;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.MovieDataObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicSearchProvider implements SearchProvider {
    @Override
    public void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback) {
        callback.onSuccess(new ArrayList<SearchDataObject>(Arrays.asList(new MovieDataObject(searchQuery, "TEST DIR", "TEST DESC"))), null);
    }

    @Override
    public void unSubscribe() {

    }
}
