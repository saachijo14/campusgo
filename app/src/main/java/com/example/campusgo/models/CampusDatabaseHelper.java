package com.example.campusgo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CampusDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CampusGo.db";
    private static final int DATABASE_VERSION = 1;

    public CampusDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE buildings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "category TEXT," +
                "timing TEXT," +
                "description TEXT)";

        db.execSQL(createTable);

        // Sample campus data
        insertBuilding(db, "Main Building", "Academic",
                "9:00 AM - 6:00 PM",
                "Main academic building of the campus.");

        insertBuilding(db, "Central Library", "Study",
                "8:00 AM - 8:00 PM",
                "Central library with books and study facilities.");

        insertBuilding(db, "Campus Canteen", "Food",
                "9:00 AM - 6:00 PM",
                "Campus canteen serving snacks and meals.");
    }

    private void insertBuilding(SQLiteDatabase db,
                                String name,
                                String category,
                                String timing,
                                String description) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("category", category);
        values.put("timing", timing);
        values.put("description", description);

        db.insert("buildings", null, values);
    }

    public Cursor getBuildings() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM buildings",
                null
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS buildings");
        onCreate(db);
    }
}