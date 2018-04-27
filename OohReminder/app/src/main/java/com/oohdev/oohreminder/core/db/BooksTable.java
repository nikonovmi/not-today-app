package com.oohdev.oohreminder.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.BookDataObject;

import java.util.ArrayList;
import java.util.List;

/*
    Table has no threading stuff because it is expected to be small
 */
public class BooksTable {
    static final String CREATE_TABLE_QUERY;
    static final String UPDATE_QUERY;
    private static final String TABLE = "book_table";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String TIMESTAMP = "timestamp_column";
    private static final String COVER_URL = "cover";
    private DatabaseHelper mDatabaseHelper;

    static {
        CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE + "(" + TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + TITLE + " Text, " + AUTHOR + " Text, " + COVER_URL + " TEXT);";
        UPDATE_QUERY = "DROP TABLE IF EXISTS " + TABLE + " ;";
    }

    public BooksTable(@NonNull Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
    }

    public void addBook(@NonNull BookDataObject book) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, book.getTitle());
        contentValues.put(AUTHOR, book.getAuthor());
        contentValues.put(COVER_URL, book.getCoverUrl());
        db.insert(TABLE, null, contentValues);
    }

    @NonNull
    public List<BookDataObject> getBooksOrderedByDate() {
        List<BookDataObject> books = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("select * from " + TABLE + " order by " + TIMESTAMP + " desc;", null)) {
            while (cursor.moveToNext()) {
                books.add(new BookDataObject(
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COVER_URL))));
            }
        }
        return books;
    }

    public void removeBook(String title) {
        mDatabaseHelper.getWritableDatabase().delete(TABLE, TITLE + "=\"" + title + "\"", null);
    }
}
