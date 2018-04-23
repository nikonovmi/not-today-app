package com.oohdev.oohreminder.core.api.search;

import android.support.annotation.NonNull;

import java.util.List;

public interface SearchProvider {
    void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback);

    void unSubscribe();

    interface Callback {
        void onSuccess(@NonNull List<SearchDataObject> searchResults, @NonNull SearchDataObject plainSearchQuery);
        void onFailure(@NonNull SearchFailure failure, @NonNull SearchDataObject plainSearchQuery);
    }
}
