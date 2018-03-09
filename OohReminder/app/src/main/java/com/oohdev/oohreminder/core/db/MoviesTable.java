package com.oohdev.oohreminder.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.model.MovieModelComplete;

import java.util.ArrayList;
import java.util.List;

public class MoviesTable {
    static final String CREATE_TABLE_QUERY;
    static final String UPDATE_QUERY;
    private static final String TABLE = "movie_table";
    private static final String TITLE = "title";
    private static final String DIRECTOR = "director";
    private static final String DESCRIPTION = "description";
    private static final String TIMESTAMP = "timestamp_column";
    private static final String POSTER = "poster_column";
    private DatabaseHelper mDatabaseHelper;

    static {
        CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE + "(" + TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + TITLE + " Text, " + DIRECTOR + " Text, " + DESCRIPTION + " Text, " + POSTER + " TEXT);";
        UPDATE_QUERY = "DROP TABLE IF EXISTS " + TABLE + " ;";
    }

    public MoviesTable(@NonNull Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
    }

    public void insertMovie(@NonNull MovieModelComplete movie) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, movie.getTitle());
        contentValues.put(DIRECTOR, movie.getDirector());
        contentValues.put(DESCRIPTION, movie.getDescription());
        contentValues.put(POSTER, movie.getPosterUrl());
        db.insert(TABLE, null, contentValues);
    }

    @NonNull
    public List<MovieModelComplete> getMoviesOrderedByDate() {
        List<MovieModelComplete> movies = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        try(Cursor cursor = db.rawQuery("select * from " + TABLE + " order by " + TIMESTAMP +" desc;", null)) {
            MovieModelComplete movie;
            while (cursor.moveToNext()) {
                movie = new MovieModelComplete();
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                String director = cursor.getString(cursor.getColumnIndexOrThrow(DIRECTOR));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
                String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
                movie.setTitle(title);
                movie.setDirector(director);
                movie.setDescription(description);
                movie.setPosterUrl(poster);
                movies.add(movie);
            }
        }
        return movies;
    }

    public void removeMovie(String title) {
        mDatabaseHelper.getWritableDatabase().delete(TABLE, TITLE + "=\"" + title + "\"", null);
    }

}
