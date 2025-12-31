package com.shubham.pgrentalapp2.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shubham.pgrentalapp2.database.DatabaseHelper;
import com.shubham.pgrentalapp2.model.BookingModel;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    // ================= ADD BOOKING =================
    public static void addBooking(Context context, BookingModel booking) {

        SQLiteDatabase db =
                new DatabaseHelper(context).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("pg_name", booking.getPgName());
        cv.put("rent", booking.getRent());
        cv.put("pg_address", booking.getPgAddress()); // ✅ STORED
        cv.put("pg_city", booking.getPgCity());       // ✅ STORED
        cv.put("student_name", booking.getStudentName());
        cv.put("student_email", booking.getStudentEmail());
        cv.put("student_phone", booking.getStudentPhone());
        cv.put("owner_email", booking.getOwnerEmail());
        cv.put("status", booking.getStatus());

        db.insert("booking", null, cv);
        db.close();
    }

    // ================= DUPLICATE CHECK =================
    public static boolean hasBooking(
            Context context,
            String studentEmail,
            String pgName
    ) {
        SQLiteDatabase db =
                new DatabaseHelper(context).getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT booking_id FROM booking WHERE student_email=? AND pg_name=?",
                new String[]{studentEmail, pgName}
        );

        boolean exists = c.moveToFirst();

        c.close();
        db.close();
        return exists;
    }

    // ================= STUDENT BOOKINGS =================
    public static List<BookingModel> getBookingsByStudent(
            Context context,
            String studentEmail
    ) {
        List<BookingModel> list = new ArrayList<>();

        SQLiteDatabase db =
                new DatabaseHelper(context).getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM booking WHERE student_email=?",
                new String[]{studentEmail}
        );

        while (c.moveToNext()) {
            list.add(cursorToModel(c));
        }

        c.close();
        db.close();
        return list;
    }

    // ================= OWNER BOOKINGS =================
    public static List<BookingModel> getBookingsForOwner(
            Context context,
            String ownerEmail
    ) {
        List<BookingModel> list = new ArrayList<>();

        SQLiteDatabase db =
                new DatabaseHelper(context).getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM booking WHERE owner_email=?",
                new String[]{ownerEmail}
        );

        while (c.moveToNext()) {
            list.add(cursorToModel(c));
        }

        c.close();
        db.close();
        return list;
    }

    // ================= UPDATE STATUS =================
    public static void updateStatus(
            Context context,
            String studentEmail,
            String pgName,
            String status
    ) {
        SQLiteDatabase db =
                new DatabaseHelper(context).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("status", status);

        db.update(
                "booking",
                cv,
                "student_email=? AND pg_name=?",
                new String[]{studentEmail, pgName}
        );

        db.close();
    }

    // ================= DELETE BOOKING =================
    public static boolean deleteBooking(
            Context context,
            String studentEmail,
            String pgName
    ) {
        SQLiteDatabase db =
                new DatabaseHelper(context).getWritableDatabase();

        int rows = db.delete(
                "booking",
                "student_email=? AND pg_name=?",
                new String[]{studentEmail, pgName}
        );

        db.close();
        return rows > 0;
    }

    // ================= CURSOR → MODEL =================
    private static BookingModel cursorToModel(Cursor c) {

        return new BookingModel(
                c.getString(c.getColumnIndexOrThrow("pg_name")),
                c.getString(c.getColumnIndexOrThrow("rent")),
                c.getString(c.getColumnIndexOrThrow("pg_address")), // ✅ READ
                c.getString(c.getColumnIndexOrThrow("pg_city")),    // ✅ READ
                c.getString(c.getColumnIndexOrThrow("student_name")),
                c.getString(c.getColumnIndexOrThrow("student_email")),
                c.getString(c.getColumnIndexOrThrow("student_phone")),
                c.getString(c.getColumnIndexOrThrow("owner_email")),
                c.getString(c.getColumnIndexOrThrow("status"))
        );
    }
}
