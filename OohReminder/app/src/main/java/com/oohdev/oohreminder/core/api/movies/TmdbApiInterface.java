package com.oohdev.oohreminder.core.api.movies;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.model.TMDBMovieCredits;
import com.oohdev.oohreminder.core.model.TMDBSearch;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface TmdbApiInterface {
    @NonNull
    @GET("search/movie?")
    Observable<TMDBSearch> findMovies(@Query("api_key") String apiKey, @Query("query") String query);

    @NonNull
    @GET("movie/{id}/credits?")
    Observable<TMDBMovieCredits> getCredits(@Path("id") int id, @Query("api_key") String apiKey);
}
