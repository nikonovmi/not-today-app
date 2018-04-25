package com.oohdev.oohreminder.core.api.books;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oohdev.oohreminder.core.BookDataObject;
import com.oohdev.oohreminder.core.api.SearchDataObject;
import com.oohdev.oohreminder.core.api.SearchFailure;
import com.oohdev.oohreminder.core.api.SearchProvider;
import com.oohdev.oohreminder.core.model.OpenLibSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.primitives.Ints.min;

public class BookSearchProvider implements SearchProvider {
    private static final int MAX_BOOKS_IN_RESULT = 3;
    @Nullable
    private Disposable mSearchDisposable = null;

    @Override
    public void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback) {
        unSubscribe();

        OpenLibSearchInterface searchInterface = OpenLibSearchAPIClient.getRetrofitInstance().create(OpenLibSearchInterface.class);
        mSearchDisposable = searchInterface.findBooks(searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(openLibSearch -> {
                    List<BookDataObject> searchResults = getBooksFromSearchModel(openLibSearch);
                    callback.onSuccess(searchResults, searchQuery);
                }, throwable -> {
                    BookDataObject plainBookDataObject = new BookDataObject(searchQuery);
                    callback.onFailure(SearchFailure.INTERNET_CONNECTION, plainBookDataObject);
                    // TODO pass real search failure, not always SearchFailure.INTERNET_CONNECTION
                });
    }

    @NonNull
    private List<BookDataObject> getBooksFromSearchModel(@NonNull OpenLibSearch openLibSearch) {
        List<BookDataObject> result = new ArrayList<>();
        if (openLibSearch.results == null) {
            return result;
        }
        for (int i = 0; i < min(MAX_BOOKS_IN_RESULT, openLibSearch.results.size()); i++) {
            OpenLibSearch.Entry entry = openLibSearch.results.get(i);
            BookDataObject book = new BookDataObject();
            if (entry.title != null) {
                book.setTitle(entry.title);
            }
            if (entry.coverId != null) {
                book.setCoverUrl(getCoverUrlFromCoverId(entry.coverId));
            }
            if (entry.authors != null && entry.authors.size() > 0) {
                book.setAuthor(entry.authors.get(0));
            }
            result.add(book);
        }
        return result;
    }

    @Override
    public void unSubscribe() {
        if (mSearchDisposable != null) {
            mSearchDisposable.dispose();
            mSearchDisposable = null;
        }
    }

    @NonNull
    @Override
    public SearchDataObject buildSearchDataObject(@NonNull String title) {
        return new BookDataObject(title);
    }


    @NonNull
    private static String getCoverUrlFromCoverId(int coverId) {
        return OpenLibSearchAPIClient.COVER_URL_PREFIX + Integer.toString(coverId) + OpenLibSearchAPIClient.COVER_URL_SUFFIX;
    }
}
