package edu.cvtc.dkappus.intervaltimer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    /*
     * Class used to communicate with the database.  It is used to load and save information to
     * the database
     */

    // Member Attributes
    private static DataManager ourInstance = null;
    private List<TaskTable> mTasks = new ArrayList<>();
    private List<RoutineTable> mRoutines = new ArrayList<>();

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    // Return a list of tasks and list of routines
    public List<TaskTable> getTasks() { return mTasks;}
    public List<RoutineTable> getRoutines() { return mRoutines;}

    private static void loadDataFromDatabase(Cursor taskCursor, Cursor routineCursor) {
        /*
         * Retrieve the field positions in the database.
         * The positions of fields may change over time as the database grows so
         * you want to use your constants to reference where those positions are in
         * the table.
         */

        int listTaskNamePosition =
                taskCursor.getColumnIndex(DatabaseContract.DataInfoEntry.COLUMN_TASK_NAME);
        int listTaskTimePosition =
                taskCursor.getColumnIndex(DatabaseContract.DataInfoEntry.COLUMN_TASK_TIME);
        int taskIdPosition =
                taskCursor.getColumnIndex(DatabaseContract.DataInfoEntry._ID);

        int listRoutineNamePosition =
                routineCursor.getColumnIndex(
                        DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_NAME);
        int listRoutineOrderPosition =
                routineCursor.getColumnIndex(
                        DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_ORDER);
        int listRoutineTaskIdPosition =
                routineCursor.getColumnIndex(
                        DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_TASKID);
        int routineIdPosition =
                routineCursor.getColumnIndex(DatabaseContract.RoutineDataInfoEntry._ID);


        /*
        Create an instance of the DataManager and use the DataManager
        to clear any information from the array lists.
         */
        DataManager dm = getInstance();
        dm.mTasks.clear();
        dm.mRoutines.clear();

        /*
        Loop through the cursor rows and add new task/routine object to
        your array lists.
         */
        while (taskCursor.moveToNext()) {
            String listName =
                    taskCursor.getString(listTaskNamePosition);
            String listTime =
                    taskCursor.getString(listTaskTimePosition);
            int id = taskCursor.getInt(taskIdPosition);

            TaskTable list = new TaskTable(id, listName, listTime);

            dm.mTasks.add(list);
        }

        // close the task cursor to prevent memory leaks
        taskCursor.close();

        while (routineCursor.moveToNext()) {
            String listName =
                    routineCursor.getString(listRoutineNamePosition);
            int listOrder =
                    routineCursor.getInt(listRoutineOrderPosition);
            int listTaskId =
                    routineCursor.getInt(listRoutineTaskIdPosition);
            int id = routineCursor.getInt(routineIdPosition);

            RoutineTable list = new RoutineTable(id, listName, listOrder, listTaskId);

            dm.mRoutines.add(list);
        }

        // close the task cursor to prevent memory leaks
        routineCursor.close();
    }

    // Method to populate Cursor object before calling the loadDataFromDatabase Method
    public static void loadFromDatabase(DatabaseHelper dbHelper) {
        // Open the database in read mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Create a list of columns to return from task table
        String[] taskColumns = {
                DatabaseContract.DataInfoEntry._ID,
                DatabaseContract.DataInfoEntry.COLUMN_TASK_NAME,
                DatabaseContract.DataInfoEntry.COLUMN_TASK_TIME};

        // Create a list of columns to return from routine table
        String[] routineColumns = {
                DatabaseContract.RoutineDataInfoEntry._ID,
                DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_NAME,
                DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_ORDER,
                DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_TASKID};

        // Create an order by field for sorting purposes
        String taskOrderBy = DatabaseContract.DataInfoEntry.COLUMN_TASK_NAME;
        String routineOrderBy = DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_NAME +
                ", " + DatabaseContract.RoutineDataInfoEntry.COLUMN_ROUTINE_ORDER;

        // Populate the cursor with the results of the queries
        final Cursor taskCursor = db.query(DatabaseContract.DataInfoEntry.TABLE1_NAME,
                taskColumns,
                null, null, null, null,
                taskOrderBy);

        final Cursor routineCursor = db.query(DatabaseContract.RoutineDataInfoEntry.TABLE2_NAME,
                routineColumns,
                null,null,null,null,
                routineOrderBy);

        // Call the method to load the array lists
        loadDataFromDatabase(taskCursor, routineCursor);
    }

}
