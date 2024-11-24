package com.example.goeco_amazon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovementDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MovementData.db";
    private static final String TABLE_NAME = "MovementRecords";
    private static final int DATABASE_VERSION = 1;

    public MovementDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "movementType TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(double latitude, double longitude, String movementType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("movementType", movementType);
        db.insert(TABLE_NAME, null, values);
    }
}

