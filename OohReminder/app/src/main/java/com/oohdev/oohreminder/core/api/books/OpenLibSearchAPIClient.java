package com.oohdev.oohreminder.core.api.books;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class OpenLibSearchAPIClient {
    static final String COVER_URL_PREFIX = "http://covers.openlibrary.org/b/id/";
    static final String COVER_URL_SUFFIX = "-M.jpg";
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://openlibrary.org/";

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
