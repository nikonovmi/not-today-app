package com.oohdev.oohreminder.core.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/***
    {
        "start": 0,
        "num_found": 629,
        "docs": [
            {...},
            {...},
            {...},
            ...
            {...}]
    }
***/

public class OpenLibSearch {
    @SerializedName("docs")
    public List<Entry> results = new ArrayList<>();


    public static class Entry {
        @SerializedName("title")
        public String title = null;

        @SerializedName("author_name")
        public List<String> authors = new ArrayList<>();

        @SerializedName("cover_i")
        public Integer coverId = null;
    }
}
