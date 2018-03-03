package com.oohdev.oohreminder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.movies.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE = DatabaseValueClass.MOVIE_TABLE;
    private static final String TITLE = "title";
    private static final String DIRECTOR = "director";
    private static final String DESCRIPTION = "description";
    private static final String TIMESTAMP = "timestamp_column";
    private static final String POSTER = "poster_column";
    private static MovieDatabaseHelper mInstance = null;

    @NonNull
    public static MovieDatabaseHelper getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new MovieDatabaseHelper(context);
        }
        return mInstance;
    }

    public void insertMovie(@NonNull MovieModel movie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, movie.getTitle());
        contentValues.put(DIRECTOR, movie.getDirector());
        contentValues.put(DESCRIPTION, movie.getDescription());
        contentValues.put(POSTER, movie.getPosterUrl());
        db.insert(TABLE, null, contentValues);
    }

    @NonNull
    public List<MovieModel> getMoviesOrderedByDate() {
        List<MovieModel> movies = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        try(Cursor cursor = db.rawQuery("select * from " + TABLE + " order by " + TIMESTAMP +" desc;", null)) {
            MovieModel movieModel;
            while (cursor.moveToNext()) {
                movieModel = new MovieModel();
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                String director = cursor.getString(cursor.getColumnIndexOrThrow(DIRECTOR));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
                String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
                movieModel.setTitle(title);
                movieModel.setDirector(director);
                movieModel.setDescription(description);
                movieModel.setPosterUrl(poster);
                movies.add(movieModel);
            }
        }
        return movies;
    }

    private MovieDatabaseHelper(@NonNull Context context) {
        super(context, DatabaseValueClass.DATABASE, null, DatabaseValueClass.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String que = "CREATE TABLE " + TABLE + "(" + TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + TITLE + " Text, " + DIRECTOR + " Text, " + DESCRIPTION + " Text, " + POSTER + " TEXT);";
        sqLiteDatabase.execSQL(que);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE + " ;");
    }

    public void removeMovie(String title) {
        getWritableDatabase().delete(TABLE, TITLE + "=\"" + title + "\"", null);
    }
}
