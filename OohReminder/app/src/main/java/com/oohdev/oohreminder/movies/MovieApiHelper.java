package com.oohdev.oohreminder.movies;

import android.support.annotation.NonNull;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCrew;

public class MovieApiHelper {
    private static final String APIKEY = "ce7de4425aba73430f65ae5ba7544419";

    public static MovieModel getMovieModel(@NonNull String title, @NonNull String defaultDirector, @NonNull String defaultDesc) {
        MovieModel movieModel = new MovieModel();
        movieModel.title = title;
        movieModel.director = defaultDirector;
        movieModel.description = defaultDesc;

        try {
            TmdbApi api = new TmdbApi(APIKEY);
            TmdbSearch tmdbSearch = api.getSearch();
            MovieResultsPage movieResultsPage = tmdbSearch.searchMovie(title, null, "", true, 0);
            List<MovieDb> results = movieResultsPage.getResults();

            if (results == null || results.isEmpty()) {
                return movieModel;
            }

            MovieDb result = results.get(0);
            return getMovieModelById(movieModel, result.getId(), api);
        } catch (Exception e) {
            return movieModel;
        }
    }

    private static MovieModel getMovieModelById(@NonNull MovieModel movieModel, int id, @NonNull TmdbApi api) {
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
            movieModel.description = result.getOverview();
        }
        return movieModel;
    }
}
