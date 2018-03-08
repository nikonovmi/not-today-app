package com.oohdev.oohreminder.core.model;

public class MovieModelComplete {
    private String mTitle;
    private String mDirector;
    private String mDescription;
    private String mPosterUrl;

    public String getTitle() {
        return mTitle;
    }

    public String getDirector() {
        return mDirector;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }
}
