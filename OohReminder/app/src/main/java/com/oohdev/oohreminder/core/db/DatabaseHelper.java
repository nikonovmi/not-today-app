package com.oohdev.oohreminder.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;

    @NonNull
    static DatabaseHelper getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    private DatabaseHelper(@NonNull Context context) {
        super(context, DatabaseController.DATABASE, null, DatabaseController.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (String query : DatabaseController.TABLES_CREATE_QUERIES) {
            sqLiteDatabase.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        for (String query : DatabaseController.TABLES_UPDATE_QUERIES) {
            sqLiteDatabase.execSQL(query);
        }
    }

}
