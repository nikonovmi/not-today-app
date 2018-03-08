package com.oohdev.oohreminder.core.model;

public class BookModelComplete {
    private String mTitle;
    private String mAuthor;
    private String mCoverUrl;

    public BookModelComplete() {
        mTitle = "";
        mAuthor = "";
        mCoverUrl = "";
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }
}
