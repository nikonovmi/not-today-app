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
    private static MovieDatabaseHelper mInstance = null;

    @NonNull
    public static MovieDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MovieDatabaseHelper(context);
        }
        return mInstance;
    }

    public void insertMovie(String title, String director, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(DIRECTOR, director);
        contentValues.put(DESCRIPTION, description);
        db.insert(TABLE, null, contentValues);
    }

    public List<MovieModel> getMovies() {
        List<MovieModel> movies = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try(Cursor cursor = db.rawQuery("select * from " + TABLE + " ;", null)) {
            MovieModel movieModel = null;
            while (cursor.moveToNext()) {
                movieModel = new MovieModel();
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                String director = cursor.getString(cursor.getColumnIndexOrThrow(DIRECTOR));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
                movieModel.setTitle(title);
                movieModel.setDirector(director);
                movieModel.setDescription(description);
                movies.add(movieModel);
            }
        }
        return movies;
    }

    private MovieDatabaseHelper(Context context) {
        super(context, DatabaseValueClass.DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String que = "CREATE TABLE " + TABLE + "(" + TITLE + " Text, " + DIRECTOR + " Text, " + DESCRIPTION + " Text);";
        sqLiteDatabase.execSQL(que);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE + " ;");
    }
}
