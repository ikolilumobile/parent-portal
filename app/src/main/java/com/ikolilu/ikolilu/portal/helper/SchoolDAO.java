package com.ikolilu.ikolilu.portal.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ikolilu.ikolilu.portal.model.School;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/05/2018.
 */

public class SchoolDAO {
    public static final String TAG = "SchoolDAO";
    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;

    private String[] mAllColumns = { DatabaseHelper.COLUMN_SCHOOL_ID,
            DatabaseHelper.COLUMN_SCHOOL_NAME,
            DatabaseHelper.COLUMN_SCHOOL_CODE
    };

    public SchoolDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public School createSchool(String schoolName, String schoolCode){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SCHOOL_NAME, schoolName);
        values.put(DatabaseHelper.COLUMN_SCHOOL_CODE, schoolCode);

        Long insertId =  mDatabase.insert(DatabaseHelper.TABLE_SCHOOOL, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_SCHOOOL, mAllColumns,
                DatabaseHelper.COLUMN_SCHOOL_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        School newSchool = cursorToSchool(cursor);
        cursor.close();
        return newSchool;
    }

    private School cursorToSchool(Cursor cursor) {
        School school = new School();
        school.setId(cursor.getInt(0));
        school.setSchoolName(cursor.getString(1));
        school.setSchoolCode(cursor.getString(2));
        return school;
    }

    public void deleteSchool(School school){
        long id  = school.getId();
        SchoolDAO schoolDAO = new SchoolDAO(mContext);
        //
    }

    public List<School> getAllSchool(){
        List<School> listSchool = new ArrayList<School>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_SCHOOOL, mAllColumns,
                null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                School school = cursorToSchool(cursor);
                listSchool.add(school);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listSchool;
    }

    public School getSchoolById(long id) {
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_SCHOOOL, mAllColumns,
                mDbHelper.COLUMN_SCHOOL_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        School company = cursorToSchool(cursor);
        return company;
    }

    public Cursor getRow(){
         SQLiteDatabase databaseHelper = mDbHelper.getWritableDatabase();
         Cursor res = databaseHelper.rawQuery("SELECT * FROM " + mDbHelper.TABLE_SCHOOOL, null);
         return res;
    }

    public void clearTable(){
        SQLiteDatabase databaseHelper = mDbHelper.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + mDbHelper.TABLE_WARD;
        databaseHelper.execSQL(sql);
        mDbHelper.onCreate(databaseHelper);
    }

    public Cursor getSchoolByName(String schoolName){
        SQLiteDatabase databaseHelper = mDbHelper.getWritableDatabase();
        Cursor res = databaseHelper.rawQuery("SELECT * FROM " + mDbHelper.TABLE_SCHOOOL
                + " WHERE " + mDbHelper.COLUMN_SCHOOL_NAME + " = '"+schoolName+"' " , null);
        return res;
    }

    public Cursor getSchoolBysId(String schoolcode){
        SQLiteDatabase databaseHelper = mDbHelper.getWritableDatabase();
        Cursor res = databaseHelper.rawQuery("SELECT * FROM " + mDbHelper.TABLE_SCHOOOL
                + " WHERE " + mDbHelper.COLUMN_SCHOOL_CODE + " = '"+schoolcode+"' " , null);
        return res;
    }

    public void dropDB(){
        //mDbHelper.onUpgrade(null, 1 , 1);
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        databaseHelper.onUpgrade(mDatabase, 1, 1);
    }
}
