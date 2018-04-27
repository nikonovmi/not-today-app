package com.oohdev.oohreminder.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.MovieDataObject;

import java.util.ArrayList;
import java.util.List;

/*
    Table has no threading stuff because it is expected to be small
 */
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

    public void insertMovie(@NonNull MovieDataObject movie) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, movie.getTitle());
        contentValues.put(DIRECTOR, movie.getDirector());
        contentValues.put(DESCRIPTION, movie.getDescription());
        contentValues.put(POSTER, movie.getPosterUrl());
        db.insert(TABLE, null, contentValues);
    }

    @NonNull
    public List<MovieDataObject> getMoviesOrderedByDate() {
        List<MovieDataObject> movies = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("select * from " + TABLE + " order by " + TIMESTAMP + " desc;", null)) {
            MovieDataObject movie;
            while (cursor.moveToNext()) {
                movie = new MovieDataObject(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DIRECTOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setPosterUrl(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movies.add(movie);
            }
        }
        return movies;
    }

    public void removeMovie(String title) {
        mDatabaseHelper.getWritableDatabase().delete(TABLE, TITLE + "=\"" + title + "\"", null);
    }

}
