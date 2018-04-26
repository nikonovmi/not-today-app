package com.oohdev.oohreminder.core.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * https://developers.themoviedb.org/3/movies/get-movie-credits
 */

public class TMDBMovieCredits {

    @SerializedName("crew")
    public List<Crew> crew = new ArrayList<>();

    public static class Crew {
        @SerializedName("name")
        public String name = null;

        @SerializedName("job")
        public String job = null;

    }
}
