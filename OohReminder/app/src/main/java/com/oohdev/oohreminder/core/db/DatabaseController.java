package com.oohdev.oohreminder.core.db;

class DatabaseController {
    static final int DATABASE_VERSION = 4;
    static final String DATABASE = "oohreminder.db";
    static final String TABLES_CREATE_QUERIES[] = {
            BooksTable.CREATE_TABLE_QUERY, MoviesTable.CREATE_TABLE_QUERY};
    static final String TABLES_UPDATE_QUERIES[] = {
            BooksTable.UPDATE_QUERY, MoviesTable.UPDATE_QUERY};
}
