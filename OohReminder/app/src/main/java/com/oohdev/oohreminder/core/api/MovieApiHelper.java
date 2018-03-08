package com.oohdev.oohreminder.core.api;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.model.MovieModelComplete;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCrew;

public class MovieApiHelper {
    private static final String APIKEY = "ce7de4425aba73430f65ae5ba7544419";
    private static final String POSTER_URL_PREFIX = "http://image.tmdb.org/t/p/w185";

    public static MovieModelComplete getMovieModel(@NonNull String title, @NonNull String defaultDirector, @NonNull String defaultDesc) {
        MovieModelComplete movieModelComplete = new MovieModelComplete();
        movieModelComplete.setTitle(title);
        movieModelComplete.setDirector(defaultDirector);
        movieModelComplete.setDescription(defaultDesc);
        movieModelComplete.setPosterUrl("");

        try {
            TmdbApi api = new TmdbApi(APIKEY);
            TmdbSearch tmdbSearch = api.getSearch();
            MovieResultsPage movieResultsPage = tmdbSearch.searchMovie(title, null, "", true, 0);
            List<MovieDb> results = movieResultsPage.getResults();

            if (results == null || results.isEmpty()) {
                return movieModelComplete;
            }

            MovieDb result = results.get(0);
            return getMovieModelById(movieModelComplete, result.getId(), api);
        } catch (Exception e) {
            return movieModelComplete;
        }
    }

    private static MovieModelComplete getMovieModelById(@NonNull MovieModelComplete movieModel, int id, @NonNull TmdbApi api) {
        MovieDb result = api.getMovies().getMovie(id, "", TmdbMovies.MovieMethod.credits);
        List<PersonCrew> crew = result.getCrew();

        if (crew != null) {
            for (int i = 0; i < crew.size(); i++) {
                PersonCrew personCrew = crew.get(i);
                if (personCrew.getJob().toLowerCase().trim().equals("director")) {
                    movieModel.setDirector(personCrew.getName());
                }
            }
        }
        if (result.getOverview() != null) {
            movieModel.setDescription(result.getOverview());
        }
        if (result.getPosterPath() != null) {
            movieModel.setPosterUrl(POSTER_URL_PREFIX + result.getPosterPath());
        }
        return movieModel;
    }
}
