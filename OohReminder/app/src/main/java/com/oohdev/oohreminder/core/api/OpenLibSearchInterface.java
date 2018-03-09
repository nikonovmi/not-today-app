package com.oohdev.oohreminder.core.api;

import com.oohdev.oohreminder.core.model.OpenLibSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface OpenLibSearchInterface {
    @GET("search.json?")
    Call<OpenLibSearch> findBooks(@Query("title") String title);
}
