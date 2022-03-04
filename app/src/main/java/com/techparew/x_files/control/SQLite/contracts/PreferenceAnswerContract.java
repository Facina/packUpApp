package com.techparew.x_files.control.SQLite.contracts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class PreferenceAnswerContract {


        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private PreferenceAnswerContract() {}

        /* Inner class that defines the table contents */
        public static class PreferenceAnswer implements BaseColumns {
            public static final String TABLE_NAME = "preferenceAnswer";
            public static final String COLUMN_ID_PREFERENCE_ANSWER = "idPreferenceAnswer";
            public static final String COLUMN_ID_ACCOUNT= "idAccount";
            public static final String COLUMN_ID_PREFERENCE_QUESTION = "idPreferenceQuestion";
            public static final String COLUMN_ANSWER = "answer";
        }


    private static final String SQL_CREATE_PREFERENCE_ANSWER =
            "CREATE TABLE " + PreferenceAnswer.TABLE_NAME + " (" +
                    PreferenceAnswer.COLUMN_ID_PREFERENCE_ANSWER + " INTEGER PRIMARY KEY," +
                    PreferenceAnswer.COLUMN_ID_ACCOUNT + " INTEGER,"+
                    PreferenceAnswer.COLUMN_ID_PREFERENCE_QUESTION + " INTEGER,"+
                    PreferenceAnswer.COLUMN_ANSWER+ " TEXT)";

    private static final String SQL_DELETE_PREFERENCE_ANSWER =
            "DROP TABLE IF EXISTS " + PreferenceAnswer.TABLE_NAME;




    public static class PreferenceAnswerDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Preferemce.db";

        public PreferenceAnswerDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_PREFERENCE_ANSWER);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_PREFERENCE_ANSWER);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }

//
//    PreferenceAnswerDbHelper dbHelper = new PreferenceAnswerDbHelper(getContext());
//    // Gets the data repository in write mode
//    SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//    // Create a new map of values, where column names are the keys
//    ContentValues values = new ContentValues();
//    values.put(PreferenceAnswer., title);
//    values.put(PreferenceAnswer.COLUMN_NAME_SUBTITLE, subtitle);
//
//    // Insert the new row, returning the primary key value of the new row
//    long newRowId = db.insert(PreferenceAnswer.TABLE_NAME, null, values);


}
