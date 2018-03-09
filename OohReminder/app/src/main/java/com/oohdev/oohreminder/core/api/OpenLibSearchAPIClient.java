package com.oohdev.oohreminder.core.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class OpenLibSearchAPIClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://openlibrary.org/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
