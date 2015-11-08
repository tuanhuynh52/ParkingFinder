package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tuan Huynh on 10/31/2015.
 * DatabaseHelper class helps store user information when a new one is registered in the system.
 * The database is used via SQlite
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /*
    * Database version 1
     */
    public static final int DB_VERSION = 1;
    /*
    *Create a databse named login.db
     */
    public static final String DB_NAME = "login.db";
    /*
    *Table name in databse is users
     */
    public static final String TABLE_NAME = "Users";
    /*
    * Colum id will be primary key
     */
    public static final String ID_COLUMN = "id";
    /*
    *Email colum
     */
    public static final String EMAIL_COLUM = "Email";
    /*
    username column
     */
    public static final String UNAME_COLUMN = "username";
    /*
    *password column
     */
    public static final String PWD_COLUMN = "password";

    /*
    Commands to create table with necessary columns
     */
    private static final String TABLE_CREATE = "create table users (id integer primary key not null , "
            + "Email text not null, username text not null, password text not null);";
    /*
    * SQLite databse
     */
    SQLiteDatabase db;

    /**
     * passing context to database
     * @param context context of database
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * execute SQLite to create a table
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    /**
     * upgrade a new database if table exists
     * @param db database
     * @param oldVersion of database
     * @param newVersion of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    /**
     * insert new user for registration
     * @param user new user
     */
    public void insert(User user) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from Users";
        Cursor cursor = db.rawQuery(query, null);
        int countID = cursor.getCount();

        values.put(ID_COLUMN, countID);
        values.put(UNAME_COLUMN, user.getUsername());
        values.put(EMAIL_COLUM, user.getEmail());
        values.put(PWD_COLUMN, user.getPassword());

        db.insert(TABLE_NAME, null, values);
    }

    /**
     * look up password which must be equivalent to a particular username for login
     * @param username username
     * @return password which is equivalent to that username
     */
    public String searchPassword(String username) {

        db = this.getReadableDatabase();
        String query = "select username, password from " +TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String uName, pwd;
        pwd = "Not Found";
        if (cursor.moveToFirst()){
            do {
                uName = cursor.getString(0);
                if (uName.equals(username)){
                    pwd = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return pwd;
    }

    /**
     * make sure email is registered once for an account
     * @param emailStr string email
     * @return email of registration
     */
    public String searchEmail(String emailStr) {

        db = this.getReadableDatabase();
        String query = "select Email from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String email = "";
        if(cursor.moveToFirst()) {
            do {
                email = cursor.getString(0);
                if (email.equals(emailStr)){
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return email;
    }
}
