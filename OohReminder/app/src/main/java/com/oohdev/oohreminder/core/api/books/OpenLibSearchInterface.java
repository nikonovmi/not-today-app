package com.oohdev.oohreminder.core.api.books;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.model.OpenLibSearch;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface OpenLibSearchInterface {
    @NonNull
    @GET("search.json?")
    Observable<OpenLibSearch> findBooks(@Query("title") String title);
}
