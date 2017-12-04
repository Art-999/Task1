package com.example.arturmusayelyan.task1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by artur.musayelyan on 04/12/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "person_info.db";
    private static final String CREATE_QUERY = "create table " + PersonDbBaseInfo.PersonEntry.TABLE_NAME + "(" + PersonDbBaseInfo.PersonEntry.FIRSTNAME + " text," +
            PersonDbBaseInfo.PersonEntry.LASTNAME + " text," + PersonDbBaseInfo.PersonEntry.USERNAME + " text," + PersonDbBaseInfo.PersonEntry.PASSWORD + " text," + PersonDbBaseInfo.PersonEntry.PARENTUSERNAME + " text);";


    public DatabaseOperations(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("Database operations", "Database created...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putPersonToDB(DatabaseOperations databaseOperations, String firstName, String lastName, String userName, String password, String parentUserName) {
        SQLiteDatabase sqLiteDatabase = databaseOperations.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonDbBaseInfo.PersonEntry.FIRSTNAME, firstName);
        contentValues.put(PersonDbBaseInfo.PersonEntry.LASTNAME, lastName);
        contentValues.put(PersonDbBaseInfo.PersonEntry.USERNAME, userName);
        contentValues.put(PersonDbBaseInfo.PersonEntry.PASSWORD, password);
        contentValues.put(PersonDbBaseInfo.PersonEntry.PARENTUSERNAME, parentUserName);
        sqLiteDatabase.insert(PersonDbBaseInfo.PersonEntry.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "One Row Inserted.....");
    }
}
