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
    public static final String ID_COLUMN = "id";
    public static final String UNAME_COLUMN = "username";
    public static final String LOCATION_NAME_COLUMN = "Location_Name";
    public static final String ADDRESS_COLUMN = "Address";
    public static final String DISTANCE_COLUMN = "Distance";
    public static final String TYPE_COLUMN = "Parking_Type";
    public static final String START_COLUMN ="Start_Time";
    public static final String END_COLUMN = "End_Time";
    public static final String SPOT_COLUMN = "Spots";
    public static final String PRICE_COLUMN = "Price";
    public static final String DIRECTION_COLUMN = "Directions";
    public static final String DESCRIPTION_COLUMN = "Descriptions";

    private SQLiteDatabase sqLiteDatabase;

    public MySavedParkingLocation mySavedParkingLocation;

    public User user;

    public ParkingLocationDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mySavedParkingLocation = new MySavedParkingLocation();
        user = new User();
    }

    /**
     * insert information of a parking into loggedin account
     *
     */
    public void addData(){

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from" + TABLE_NAME ;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        int countID = c.getCount();

        values.put(ID_COLUMN, countID);
        values.put(UNAME_COLUMN, user.getUsername());
        values.put(LOCATION_NAME_COLUMN, mySavedParkingLocation.getLocation_name());
        values.put(ADDRESS_COLUMN, mySavedParkingLocation.getAddress());
        values.put(DISTANCE_COLUMN, mySavedParkingLocation.getDistance());
        values.put(TYPE_COLUMN, mySavedParkingLocation.getType());
        values.put(START_COLUMN, mySavedParkingLocation.getStart_time());
        values.put(END_COLUMN, mySavedParkingLocation.getEnd_time());
        values.put(SPOT_COLUMN, mySavedParkingLocation.getSpots());
        values.put(PRICE_COLUMN, mySavedParkingLocation.getPrice());
        values.put(DIRECTION_COLUMN, mySavedParkingLocation.getDirections());
        values.put(DESCRIPTION_COLUMN, mySavedParkingLocation.getDescription());

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
                "("+ID_COLUMN+ " integer, "+
                UNAME_COLUMN+ " text not null, " +
                LOCATION_NAME_COLUMN+ " text not null, " +
                ADDRESS_COLUMN + " text not null, " +
                DISTANCE_COLUMN+ " integer not null, " +
                TYPE_COLUMN + " text, "+
                START_COLUMN + " text, "+
                END_COLUMN+ " text, "+
                SPOT_COLUMN+" INTEGER, "+
                PRICE_COLUMN+" text, "+
                DIRECTION_COLUMN+ " text, "+
                DESCRIPTION_COLUMN+ " text" +
                "primary key("+UNAME_COLUMN+", "+LOCATION_NAME_COLUMN+")";
        sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    class ParkingLocationDBHelper extends SQLiteOpenHelper {

        private static final String CREATE_PARKING_LOCATION_SQL = "CREATE TABLE IF NOT EXISTS ";

        public ParkingLocationDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PARKING_LOCATION_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
