package com.example.lab_15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "qlsinhvien.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tbllop";
    public static final String COLUMN_MALOP = "malop";
    public static final String COLUMN_TENLOP = "tenlop";
    public static final String COLUMN_SISO = "siso";

    // SQL statement to create the table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_MALOP + " TEXT PRIMARY KEY," +
                    COLUMN_TENLOP + " TEXT," +
                    COLUMN_SISO + " INTEGER)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    // --- CRUD Operations ---

    // Insert a new class record
    public long insertClass(String maLop, String tenLop, int siSo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MALOP, maLop);
        values.put(COLUMN_TENLOP, tenLop);
        values.put(COLUMN_SISO, siSo);

        // Inserting Row
        long result = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return result; // returns -1 if insert failed, row ID otherwise
    }

    // Update an existing class record
    public int updateClass(String maLop, String tenLop, int siSo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENLOP, tenLop);
        values.put(COLUMN_SISO, siSo);

        // Updating row
        int result = db.update(TABLE_NAME, values, COLUMN_MALOP + " = ?",
                new String[]{maLop});
        db.close();
        return result; // returns number of rows affected
    }

    // Delete a class record
    public int deleteClass(String maLop) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_MALOP + " = ?",
                new String[]{maLop});
        db.close();
        return result; // returns number of rows affected
    }

    // Get all class records (for Query)
    public List<ClassModel> getAllClasses() {
        List<ClassModel> classList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ClassModel cls = new ClassModel();
                cls.setMaLop(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MALOP)));
                cls.setTenLop(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TENLOP)));
                cls.setSiSo(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SISO)));
                classList.add(cls);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return classList;
    }

    // Get a single class record by MaLop
    public ClassModel getClass(String maLop) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_MALOP,
                        COLUMN_TENLOP, COLUMN_SISO}, COLUMN_MALOP + "=?",
                new String[]{maLop}, null, null, null, null);

        ClassModel cls = null;
        if (cursor != null && cursor.moveToFirst()) {
            cls = new ClassModel(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MALOP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TENLOP)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SISO))
            );
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return cls;
    }
}
