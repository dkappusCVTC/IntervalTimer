package edu.cvtc.dkappus.intervaltimer;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {}

    public static final class TaskInfoEntry implements BaseColumns {
        // Constants to hold the name of the tables and fields
        // Table 1
        public static final String TABLE1_NAME = "task";
        public static final String COLUMN_TASK_NAME = "task_name";
        public static final String COLUMN_TASK_TIME = "task_time";

        // Constants to hold values for an index name and index value based upon task or routine
        // name
        public static final String INDEX1_TABLE1 = TABLE1_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1_TABLE1 = "CREATE INDEX " +
                INDEX1_TABLE1 + " ON " + TABLE1_NAME + "(" + COLUMN_TASK_NAME + ")";

        // Constants to create the tables using table name, fields and id
        // SQL Statement is stored in the constant
        public static final String SQL_CREATE_TABLE1 = "CREATE TABLE " + TABLE1_NAME +
                " (" + _ID + " INTEGER PRIMARY KEY, " + COLUMN_TASK_NAME + " TEXT NOT NULL, " +
                COLUMN_TASK_TIME + " TEXT)";

    }

    public static final class RoutineInfoEntry implements BaseColumns {
        //Table 2
        public static final String TABLE2_NAME = "routine";
        public static final String COLUMN_ROUTINE_NAME = "routine_name";

        public static final String INDEX1_TABLE2 = TABLE2_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1_TABLE2 = "CREATE INDEX " +
                INDEX1_TABLE2 + " ON " + TABLE2_NAME + "(" + COLUMN_ROUTINE_NAME + ")";

        public static final String SQL_CREATE_TABLE2 = "CREATE TABLE " + TABLE2_NAME +
                " (" + _ID + " INTEGER PRIMARY KEY, " + COLUMN_ROUTINE_NAME + " TEXT NOT NULL)";
    }

    public static final class RoutineTaskInfoEntry implements BaseColumns {
        //Table 3
        public static final String TABLE3_NAME = "routineTasks";
        public static final String COLUMN_ROUTINE_TASKS_ROUTINEID = "routine_id";
        public static final String COLUMN_ROUTINE_TASKS_TASKID = "task_id";
        public static final String COLUMN_ROUTINE_TASKS_ORDERNUM = "routine_order_number";

        public static final String INDEX1_TABLE3 = TABLE3_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1_TABLE3 = "CREATE INDEX " +
                INDEX1_TABLE3 + " ON " + TABLE3_NAME + "(" + COLUMN_ROUTINE_TASKS_ROUTINEID + ")";

        public static final String SQL_CREATE_TABLE3 = "CREATE TABLE " + TABLE3_NAME +
                " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ROUTINE_TASKS_ROUTINEID + " INTEGER NOT NULL, " +
                COLUMN_ROUTINE_TASKS_TASKID + " INTEGER NOT NULL, " +
                COLUMN_ROUTINE_TASKS_ORDERNUM + " INTEGER NOT NULL)";
    }
}
