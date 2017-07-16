package com.algo.transact.AppConfig;

/**
 * Created by sandeep on 10/7/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.algo.transact.login.UserDetails;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "futurin_db";

    // Login table name
    private static final String TABLE_USER = "user_info";

    // Login Table Columns names

    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE_NUM = "mobile_num";
    private static final String KEY_COUNTRY_CODE = "country_code";
    private static final String KEY_LOGGED_IN_USING = "logged_in_using";
    private static final String KEY_DISPLAY_NAME = "display_name";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_FAMILY_NAME = "family_name";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_MOBILE_NUM + " TEXT UNIQUE,"
                + KEY_COUNTRY_CODE + " TEXT"
                + KEY_LOGGED_IN_USING + " TEXT"
                + KEY_DISPLAY_NAME + " TEXT"
                + KEY_FIRST_NAME + " TEXT"
                + KEY_FAMILY_NAME + " TEXT"
                + KEY_CREATED_AT + " TEXT"
                + KEY_UPDATED_AT + " TEXT"+
                ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(UserDetails newUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, newUser.emailID); // Created At
        values.put(KEY_MOBILE_NUM, newUser.mobNo); // Created At
        values.put(KEY_COUNTRY_CODE, newUser.countryCode); // Created At
        values.put(KEY_LOGGED_IN_USING, newUser.loggedInUsingToSting(newUser.loggedInUsing)); // Created At
        values.put(KEY_DISPLAY_NAME, newUser.displayName); // Created At
        values.put(KEY_FIRST_NAME, newUser.firstName); // Created At
        values.put(KEY_FAMILY_NAME, newUser.familyName); // Created At
        values.put(KEY_CREATED_AT, newUser.createdAt); // Created At
        values.put(KEY_UPDATED_AT, newUser.updatedAt); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put(KEY_EMAIL, cursor.getString(1));
            user.put(KEY_MOBILE_NUM, cursor.getString(2));
            user.put(KEY_COUNTRY_CODE, cursor.getString(3));
            user.put(KEY_LOGGED_IN_USING, cursor.getString(4));
            user.put(KEY_DISPLAY_NAME, cursor.getString(5));
            user.put(KEY_FIRST_NAME, cursor.getString(6));
            user.put(KEY_FAMILY_NAME, cursor.getString(7));
            user.put(KEY_CREATED_AT, cursor.getString(8));
            user.put(KEY_UPDATED_AT, cursor.getString(9));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}