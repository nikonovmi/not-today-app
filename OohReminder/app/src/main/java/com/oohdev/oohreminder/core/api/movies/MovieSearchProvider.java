package com.oohdev.oohreminder.core.api.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.oohdev.oohreminder.core.MovieDataObject;
import com.oohdev.oohreminder.core.api.SearchDataObject;
import com.oohdev.oohreminder.core.api.SearchFailure;
import com.oohdev.oohreminder.core.api.SearchProvider;
import com.oohdev.oohreminder.core.model.TMDBMovieCredits;
import com.oohdev.oohreminder.core.model.TMDBSearch;

import junit.framework.Assert;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieSearchProvider implements SearchProvider {
    private static final int MAX_MOVIES_IN_RESULT = 3;
    @Nullable
    private Disposable mSearchDisposable = null;

    @Override
    public void dismissPreviousAndRequestNew(@NonNull String searchQuery, @NonNull Callback callback) {
        unSubscribe();

        TmdbApiInterface tmdbApiInterface = TmdbApiClient.getRetrofitInstance().create(TmdbApiInterface.class);
        Observable<TMDBSearch.Entry> searchEntries = tmdbApiInterface.findMovies(TmdbApiClient.API_KEY, searchQuery)
                .flatMap(tmdbSearch -> Observable.fromIterable(tmdbSearch.results))
                .take(MAX_MOVIES_IN_RESULT);
        Observable<TMDBMovieCredits> movieCredits = searchEntries.flatMap(entry -> tmdbApiInterface.getCredits(entry.id, TmdbApiClient.API_KEY));

        mSearchDisposable = Observable.zip(searchEntries, movieCredits, this::buildMovieDataObject)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDataObjects -> callback.onSuccess(movieDataObjects, searchQuery),
                        throwable -> callback.onFailure(SearchFailure.API_ISSUES, new MovieDataObject(searchQuery)));
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
        return new MovieDataObject(title);
    }

    @NonNull
    private MovieDataObject buildMovieDataObject(@NonNull TMDBSearch.Entry searchEntry, @Nullable TMDBMovieCredits credits) {
        Assert.assertNotNull(searchEntry);
        Assert.assertNotNull(searchEntry.title);
        MovieDataObject result = new MovieDataObject();
        result.setTitle(searchEntry.title);
        if (searchEntry.overview != null) {
            result.setDescription(searchEntry.overview);
        }
        if (searchEntry.posterPath != null && !TextUtils.isEmpty(searchEntry.posterPath)) {
            result.setPosterUrl(TmdbApiClient.POSTER_URL_PREFIX + searchEntry.posterPath);
        }
        if (searchEntry.releaseDate != null && !TextUtils.isEmpty(searchEntry.releaseDate)) {
            result.setReleaseDate(searchEntry.releaseDate);
        }
        if (credits != null && credits.crew != null) {
            Observable.fromIterable(credits.crew)
                    .filter(crew -> crew.job.trim().toLowerCase().equals("director"))
                    .take(1)
                    .subscribe(crew -> result.setDirector(crew.name));
        }
        return result;
    }
}
