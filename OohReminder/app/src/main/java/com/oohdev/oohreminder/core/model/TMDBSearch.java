package com.oohdev.oohreminder.core.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * https://developers.themoviedb.org/3/search/search-movies
 */

public class TMDBSearch {
    @SerializedName("results")
    public List<Entry> results = new ArrayList<>();

    public static class Entry {
        @SerializedName("id")
        public Integer id = null;

        @SerializedName("poster_path")
        public String posterPath = null;

        @SerializedName("overview")
        public String overview = null;

        @SerializedName("title")
        public String title = "";

        @SerializedName("release_date")
        public String releaseDate = null;
    }
}
