package com.oohdev.oohreminder.core.model;

public class MovieModel {
    private String title;
    private String director;
    private String description;
    private String posterUrl;

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
