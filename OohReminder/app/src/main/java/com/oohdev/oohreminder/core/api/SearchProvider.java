package com.oohdev.oohreminder.core.api;

import android.support.annotation.NonNull;

import java.util.List;

public interface SearchProvider {
    void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback);

    void unSubscribe();

    @NonNull
    SearchDataObject buildSearchDataObject(@NonNull String title);

    interface Callback {
        void onSuccess(@NonNull List<? extends SearchDataObject> searchResults, @NonNull String searchQuery);
        void onFailure(@NonNull SearchFailure failure, @NonNull SearchDataObject plainSearchQuery);
    }
}
