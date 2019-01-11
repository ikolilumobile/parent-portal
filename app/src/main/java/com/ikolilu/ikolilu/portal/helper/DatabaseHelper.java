package com.ikolilu.ikolilu.portal.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Genuis on 08/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // School Table
    public static final String TABLE_SCHOOOL= "school";
    public static final String COLUMN_SCHOOL_ID = "_id";
    public static final String COLUMN_SCHOOL_NAME = "school_name";
    public static final String COLUMN_SCHOOL_CODE = "school_code";

    // Ward Table
    public static final String TABLE_WARD = "ward";
    public static final String COLUMN_WARD_ID = "_id";
    public static final String COLUMN_WARD_NAME = "ward_name";
    public static final String COLUMN_WARD_CODE = "ward_code";
    public static final String COLUMN_WARD_SCHOOLNAME = "ward_schoolname";
    public static final String COLUMN_WARD_SCHOOLCODE = "ward_schoolcode";
    public static final String COLUMN_WARD_PROFILEPHOTO = "ward_profilephoto";
    public static final String COLUMN_WARD_TERM = "ward_term";
    public static final String COLUMN_WARD_CLASS = "ward_class";


    // Database
    private static final String DATABASE_NAME = "ikolilu.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement of the employees table creation
    private static final String SQL_CREATE_TABLE_SCHOOL = "CREATE TABLE IF NOT EXISTS " + TABLE_SCHOOOL + "("
            + COLUMN_SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SCHOOL_NAME + " TEXT NOT NULL, "
            + COLUMN_SCHOOL_CODE + " TEXT NOT NULL "
            +");";
    private static final String SQL_CREATE_TABLE_WARD = "CREATE TABLE " + TABLE_WARD + "("
            + COLUMN_WARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_WARD_NAME + " TEXT , "
            + COLUMN_WARD_CODE + " TEXT , "
            + COLUMN_WARD_SCHOOLNAME + " TEXT , "
            + COLUMN_WARD_SCHOOLCODE + " TEXT , "
            + COLUMN_WARD_PROFILEPHOTO + " TEXT, "
            + COLUMN_WARD_TERM + " TEXT ,"
            + COLUMN_WARD_CLASS + " TEXT"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SCHOOL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_WARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to "+ newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOOL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WARD);
        onCreate(sqLiteDatabase);
    }
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
