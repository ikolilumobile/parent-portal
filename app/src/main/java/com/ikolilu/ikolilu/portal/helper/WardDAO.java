package com.ikolilu.ikolilu.portal.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ikolilu.ikolilu.portal.model.Ward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 28/05/2018.
 */

public class WardDAO {
    public static final String TAG = "WardDAO";
    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;
    private Context mContext;

    private String[] mAllColumns = {
            DatabaseHelper.COLUMN_WARD_ID,
            DatabaseHelper.COLUMN_WARD_NAME,
            DatabaseHelper.COLUMN_WARD_CODE,
            DatabaseHelper.COLUMN_WARD_SCHOOLNAME,
            DatabaseHelper.COLUMN_WARD_SCHOOLCODE,
            DatabaseHelper.COLUMN_WARD_PROFILEPHOTO,
            DatabaseHelper.COLUMN_WARD_TERM,
            DatabaseHelper.COLUMN_WARD_CLASS,
    };

    public WardDAO(Context context){
        this.mContext = context;
        mDbHelper = new DatabaseHelper(context);
        // open the database
        try {
            open();

            //drop/create Table..
            //clearTable();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Ward createWard(String wardName,
                           String wardCode,
                           String wardSchoolName,
                           String wardSchoolCode,
                           String wardProfilePhoto,
                           String wardTerm,
                           String wardClass)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_WARD_NAME, wardName);
        values.put(DatabaseHelper.COLUMN_WARD_CODE, wardCode);
        values.put(DatabaseHelper.COLUMN_WARD_SCHOOLNAME, wardSchoolName);
        values.put(DatabaseHelper.COLUMN_WARD_SCHOOLCODE, wardSchoolCode);
        values.put(DatabaseHelper.COLUMN_WARD_PROFILEPHOTO, wardProfilePhoto);
        values.put(DatabaseHelper.COLUMN_WARD_TERM, wardTerm);
        values.put(DatabaseHelper.COLUMN_WARD_CLASS, wardClass);

        Long insertId =  mDatabase.insert(DatabaseHelper.TABLE_WARD, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_WARD, mAllColumns,
                DatabaseHelper.COLUMN_WARD_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Ward newWard = cursorToWard(cursor);
        cursor.close();
        return newWard;
    }

    public Ward cursorToWard(Cursor cursor){
        Ward ward = new Ward();
        ward.setId(cursor.getInt(0));
        ward.setStudentName(cursor.getString(1));
        ward.setWardId(cursor.getString(2));
        ward.setSchoolName(cursor.getString(3));
        //ward.setImage();
        ward.setTerm(cursor.getString(5));
        ward.setwClass(cursor.getString(6));
        return ward;
    }

    public List<Ward> getAllWards(){
        List<Ward> listWard = new ArrayList<Ward>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_WARD, mAllColumns,
                null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Ward ward = cursorToWard(cursor);
                listWard.add(ward);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listWard;
    }

    public void clearTable(){
        SQLiteDatabase databaseHelper = mDbHelper.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + mDbHelper.TABLE_WARD;
        databaseHelper.execSQL(sql);
        mDbHelper.onCreate(databaseHelper);
    }

    public void dropDB(){
        //mDbHelper.onUpgrade(null, 1 , 1);
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        databaseHelper.onUpgrade(mDatabase, 1, 1);
    }
}
