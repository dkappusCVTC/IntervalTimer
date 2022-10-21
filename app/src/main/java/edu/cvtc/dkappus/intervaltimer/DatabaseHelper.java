package edu.cvtc.dkappus.intervaltimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import edu.cvtc.dkappus.intervaltimer.DatabaseContract;

/**
 * Class for handling SQLite db.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constants to hold database name and version
    private Context context = new MainActivity();
    public static final String DATABASE_NAME = "IntervalTimer_dkappus.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create db tables
        db.execSQL(DatabaseContract.TaskInfoEntry.SQL_CREATE_TABLE1);
        db.execSQL(DatabaseContract.TaskInfoEntry.SQL_CREATE_INDEX1_TABLE1);
        db.execSQL(DatabaseContract.RoutineInfoEntry.SQL_CREATE_TABLE2);
        db.execSQL(DatabaseContract.RoutineInfoEntry.SQL_CREATE_INDEX1_TABLE2);
        db.execSQL(DatabaseContract.RoutineTaskInfoEntry.SQL_CREATE_TABLE3);
        db.execSQL(DatabaseContract.RoutineTaskInfoEntry.SQL_CREATE_INDEX1_TABLE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + RoutineDataInfoEntry.TABLE2_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DataInfoEntry.TABLE1_NAME);
//        onCreate(db);

    }

    public boolean insertTaskData(String name, String taskTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME, name);
        contentValues.put(
                DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME, taskTime);
        long result = db.insert(
                DatabaseContract.TaskInfoEntry.TABLE1_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertRoutineData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME, name);
        long result = db.insert(
                DatabaseContract.RoutineInfoEntry.TABLE2_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertRoutineTaskData(int routineID, int taskID, int orderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ROUTINEID, routineID);
        contentValues.put(
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_TASKID, taskID);
        contentValues.put(
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ORDERNUM, orderID);
        long result = db.insert(DatabaseContract.RoutineTaskInfoEntry.TABLE3_NAME,
                null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean populateBlankDatabase() {
        boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();

        String routineQ = "SELECT * FROM " + DatabaseContract.RoutineInfoEntry.TABLE2_NAME;

        Cursor rVerify = db.rawQuery(routineQ, null);
        
        if (rVerify.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.RoutineInfoEntry._ID, 0);
            contentValues.put(
                    DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME, "Create a Routine");
            long result = db.insert(DatabaseContract.RoutineInfoEntry.TABLE2_NAME, 
                    null, contentValues);
            if (result == -1) {
                success = false;
            }
            rVerify.close();
        }

        String taskQ = "SELECT * FROM " + DatabaseContract.TaskInfoEntry.TABLE1_NAME;

        Cursor tVerify = db.rawQuery(taskQ, null);

        if (tVerify.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.TaskInfoEntry._ID, 0);
            contentValues.put(
                    DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME, "Create a Task");
            contentValues.put(
                    DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME, " ");
            long result = db.insert(DatabaseContract.TaskInfoEntry.TABLE1_NAME,
                    null, contentValues);
            if (result == -1) {
                success = false;
            }
        }
        return success;
    }
}
