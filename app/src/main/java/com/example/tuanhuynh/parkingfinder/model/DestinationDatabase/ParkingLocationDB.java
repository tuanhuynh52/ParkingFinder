package com.example.tuanhuynh.parkingfinder.model.DestinationDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tuanhuynh.parkingfinder.controller.ParkingLocation;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.User;

/**
 * Created by Tuan Huynh on 11/23/2015.
 */
public class ParkingLocationDB extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "MyPlaceDB.db";
    public static final String TABLE_NAME = "MyDestinations";
    public static final String UNAME_COLUMN = "username";
    public static final String LOCATION_NAME_COLUMN = "Location_Name";
    public static final String ADDRESS_COLUMN = "Address";
    public static final String TYPE_COLUMN = "Parking_Type";
    public static final String PRICE_COLUMN = "Price";
    public static final String DESCRIPTION_COLUMN = "Descriptions";

    private SQLiteDatabase sqLiteDatabase;

    public ParkingLocationDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * insert information of a parking into loggedin account
     *
     */
    public void addData(String savedUsername, String savedLocationName, String savedAddress,
                        String savedType, String savedPrice, String savedDescription){

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UNAME_COLUMN, savedUsername);
        values.put(LOCATION_NAME_COLUMN, savedLocationName);
        values.put(ADDRESS_COLUMN, savedAddress);
        values.put(TYPE_COLUMN, savedType);
        values.put(PRICE_COLUMN, savedPrice);
        values.put(DESCRIPTION_COLUMN, savedDescription);

        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    /**
     * close this database
     */
    public void closeDB() {
        sqLiteDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE " + TABLE_NAME +
                "("+UNAME_COLUMN+ " text not null, " +
                LOCATION_NAME_COLUMN+ " text not null, " +
                ADDRESS_COLUMN + " text not null, " +
                TYPE_COLUMN + " text not null, "+
                PRICE_COLUMN+" text not null, "+
                DESCRIPTION_COLUMN+ " text not null, " +
                "primary key("+UNAME_COLUMN+", "+LOCATION_NAME_COLUMN+"));" ;
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public boolean isDataExisted(String username, String locationName){
        sqLiteDatabase = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where "+ UNAME_COLUMN+ " = ? and "
                +LOCATION_NAME_COLUMN+ " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(Query, new String[]{username, locationName});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
