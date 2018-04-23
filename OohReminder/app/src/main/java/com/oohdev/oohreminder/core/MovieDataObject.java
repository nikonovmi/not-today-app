package com.oohdev.oohreminder.core;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.api.search.SearchDataObject;

public class MovieDataObject extends SearchDataObject {
    @NonNull
    private String mTitle;
    @NonNull
    private String mDirector;
    @NonNull
    private String mDescription;
    @NonNull
    private String mPosterUrl;

    public MovieDataObject() {
        mTitle = "";
        mDirector = "";
        mDescription = "";
        mPosterUrl = "";
    }

    public MovieDataObject(@NonNull String title, @NonNull String director, @NonNull String description) {
        mTitle = title;
        mDirector = director;
        mDescription = description;
        mPosterUrl = "";
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDirector() {
        return mDirector;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public void setDirector(@NonNull String director) {
        mDirector = director;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    public void setPosterUrl(@NonNull String posterUrl) {
        mPosterUrl = posterUrl;
    }

    @NonNull
    @Override
    public String getPrimaryDescription() {
        return getTitle();
    }

    @NonNull
    @Override
    public String getSecondaryDescription() {
        return getDescription();
    }

    @NonNull
    @Override
    public String getImageUrl() {
        return mPosterUrl;
    }

    @Override
    @DrawableRes
    public int getDefaultImageId() {
        return R.drawable.unknown_movie;
    }
}
