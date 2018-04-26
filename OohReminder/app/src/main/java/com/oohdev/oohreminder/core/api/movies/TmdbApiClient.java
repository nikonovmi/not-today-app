package com.oohdev.oohreminder.core.api.movies;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class TmdbApiClient {
    public static final String POSTER_URL_PREFIX = "http://image.tmdb.org/t/p/w185";
    public static final String API_KEY = "ce7de4425aba73430f65ae5ba7544419";
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
