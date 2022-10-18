package edu.cvtc.dkappus.intervaltimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import edu.cvtc.dkappus.intervaltimer.DatabaseContract.DataInfoEntry;
import edu.cvtc.dkappus.intervaltimer.DatabaseContract.RoutineDataInfoEntry;

/**
 * Class for handling SQLite db.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constants to hold database name and version
    public static final String DATABASE_NAME = "IntervalTimer_dkappus.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create db tables
        db.execSQL(DataInfoEntry.SQL_CREATE_TABLE1);
        db.execSQL(DataInfoEntry.SQL_CREATE_INDEX1_TABLE1);
        db.execSQL(RoutineDataInfoEntry.SQL_CREATE_TABLE2);
        db.execSQL(RoutineDataInfoEntry.SQL_CREATE_INDEX1_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + RoutineDataInfoEntry.TABLE2_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DataInfoEntry.TABLE1_NAME);
//        onCreate(db);

    }

    public boolean insertData(String name, String taskTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataInfoEntry.COLUMN_TASK_NAME, name);
        contentValues.put(DataInfoEntry.COLUMN_TASK_TIME, taskTime);
        long result = db.insert(DataInfoEntry.TABLE1_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertRoutineData(String name, int order, int taskID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RoutineDataInfoEntry.COLUMN_ROUTINE_NAME, name);
        contentValues.put(RoutineDataInfoEntry.COLUMN_ROUTINE_ORDER, order);
        contentValues.put(RoutineDataInfoEntry.COLUMN_ROUTINE_TASKID, taskID);
        long result = db.insert(RoutineDataInfoEntry.TABLE2_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
