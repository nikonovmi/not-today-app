package com.oohdev.oohreminder.core;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.api.search.SearchDataObject;

public class BookDataObject extends SearchDataObject {
    @NonNull
    private String mTitle;
    @NonNull
    private String mAuthor;
    @NonNull
    private String mCoverUrl;

    public BookDataObject() {
        mTitle = "";
        mAuthor = "";
        mCoverUrl = "";
    }

    public BookDataObject(@NonNull String title, @NonNull String author) {
        mTitle = title;
        mAuthor = author;
        mCoverUrl = "";
    }

    public BookDataObject(@NonNull String title, @NonNull String author, @NonNull String coverUrl) {
        mTitle = title;
        mAuthor = author;
        mCoverUrl = coverUrl;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    @NonNull
    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(@NonNull String author) {
        mAuthor = author;
    }

    @NonNull
    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(@NonNull String coverUrl) {
        mCoverUrl = coverUrl;
    }

    @NonNull
    @Override
    public String getPrimaryDescription() {
        return getTitle();
    }

    @NonNull
    @Override
    public String getSecondaryDescription() {
        return getAuthor();
    }

    @NonNull
    @Override
    public String getImageUrl() {
        return getCoverUrl();
    }

    @Override
    @DrawableRes
    public int getDefaultImageId() {
        return R.drawable.unknown_book;
    }
}
