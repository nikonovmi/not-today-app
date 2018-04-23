package com.oohdev.oohreminder.core.api.search;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.BookDataObject;

import java.util.ArrayList;

public class BookSearchProvider implements SearchProvider {
    @Override
    public void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback) {
        callback.onFailure(SearchFailure.CANCELED, new BookDataObject("Harry Potter", "Huy poimi kto"));
    }

    @Override
    public void unSubscribe() {

    }
}
