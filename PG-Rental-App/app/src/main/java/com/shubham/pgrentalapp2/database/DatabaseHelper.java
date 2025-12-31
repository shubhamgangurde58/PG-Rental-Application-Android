package com.shubham.pgrentalapp2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name & Version
    private static final String DATABASE_NAME = "pg_rental.db";
    private static final int DATABASE_VERSION = 4;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Student Table (UPDATED)
        db.execSQL(
                "CREATE TABLE student (" +
                        "student_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "email TEXT UNIQUE," +
                        "password TEXT," +
                        "phone TEXT," +
                        "address TEXT," +
                        "city TEXT," +
                        "gender TEXT)"
        );


        // ================= OWNER TABLE =================
        db.execSQL(
                "CREATE TABLE owner (" +
                        "owner_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "pg_name TEXT," +
                        "email TEXT UNIQUE," +
                        "password TEXT," +
                        "phone TEXT," +
                        "address TEXT," +
                        "city TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE pg (" +
                        "pg_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "pg_name TEXT," +
                        "location TEXT," +
                        "rent TEXT," +
                        "type TEXT," +
                        "address TEXT," +
                        "description TEXT," +
                        "owner_name TEXT," +
                        "owner_phone TEXT," +
                        "owner_email TEXT," +
                        "image_uri TEXT," +        // ✅ IMAGE SUPPORT
                        "latitude REAL," +
                        "longitude REAL" +
                        ")"
        );

        // Booking Table
        db.execSQL(
                "CREATE TABLE booking (" +
                        "booking_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "pg_name TEXT," +
                        "rent TEXT," +
                        "pg_address TEXT," +
                        "pg_city TEXT," +
                        "student_name TEXT," +
                        "student_email TEXT," +
                        "student_phone TEXT," +
                        "owner_email TEXT," +
                        "status TEXT" +
                        ")"
        );

    }

    // Upgrade Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS owner");
        db.execSQL("DROP TABLE IF EXISTS pg");
        db.execSQL("DROP TABLE IF EXISTS booking");
        onCreate(db);
    }


}
