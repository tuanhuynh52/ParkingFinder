package com.example.tuanhuynh.parkingfinder.model.DestinationDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Saved location Database
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
    private MySavedParkingLocation mySavedParkingLocation;

    /**
     * Constructor saved location database
     * @param context context
     */
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
     * Delete selected location
     * @param uName username
     * @param location location
     */
    public void deleteLocation(String uName, String location) {
        String whereClause = UNAME_COLUMN + "=? AND "+ LOCATION_NAME_COLUMN + " =? ";
        String[] args = new String[]{uName, location};
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, whereClause, args);

    }

    /**
     * close this database
     */
    public void closeDB() {
        sqLiteDatabase.close();
    }

    /**
     * check if data is existed before inserting new data into database using combination of
     * primary key username and location name
     * @param username username
     * @param locationName location name
     * @return true or false
     */
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

    /**
     * Retrieve all data columns from sqlite database that equivalent to its username
     *
     * @param username username
     * @return list
     */
    public ArrayList<MySavedParkingLocation> getAllDataByUName(String username){

        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<MySavedParkingLocation> data = new ArrayList<MySavedParkingLocation>();

        String query = "select * from " + TABLE_NAME + " where "+UNAME_COLUMN+ " = ?";
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{username});
        if (c.moveToFirst()){
            do {
                String locationName = c.getString(c.getColumnIndex(LOCATION_NAME_COLUMN));
                String address = c.getString(c.getColumnIndex(ADDRESS_COLUMN));
                String type = c.getString(c.getColumnIndex(TYPE_COLUMN));
                String price = c.getString(c.getColumnIndex(PRICE_COLUMN));
                String description = c.getString(c.getColumnIndex(DESCRIPTION_COLUMN));

                mySavedParkingLocation = new MySavedParkingLocation(locationName, address,
                        type, price, description);

                data.add(mySavedParkingLocation);

            } while (c.moveToNext());
        }
        c.close();
        sqLiteDatabase.close();

        return data;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE " + TABLE_NAME +
                "("+UNAME_COLUMN+ " text not null, " +
                LOCATION_NAME_COLUMN+ " text, " +
                ADDRESS_COLUMN + " text, " +
                TYPE_COLUMN + " text, "+
                PRICE_COLUMN+" text, "+
                DESCRIPTION_COLUMN+ " text, " +
                "primary key("+UNAME_COLUMN+", "+LOCATION_NAME_COLUMN+"));" ;
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

}
