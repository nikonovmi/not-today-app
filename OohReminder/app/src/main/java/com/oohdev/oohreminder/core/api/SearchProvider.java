package com.oohdev.oohreminder.core.api;

import android.support.annotation.NonNull;

import java.util.List;

public interface SearchProvider {
    void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback);

    void dismissRequests();

    @NonNull
    SearchDataObject buildSearchDataObject(@NonNull String title);

    interface Callback {
        void onSearchRequestSuccess(@NonNull List<? extends SearchDataObject> searchResults, @NonNull String searchQuery);
        void onSearchRequestFailure(@NonNull SearchFailure failure, @NonNull SearchDataObject plainSearchQuery);
    }
}
