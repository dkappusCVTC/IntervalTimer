package edu.cvtc.dkappus.intervaltimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.cvtc.dkappus.intervaltimer.DatabaseContract;

public class CreateRoutine extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ".CreateRoutine.DEBUGGING";
    private Spinner taskSpinner;
    private Spinner routineSpinner;
    public static final int LOADER_TASKS = 0;
    public static final int LOADER_ROUTINES = 1;
    private TextView taskTvName;
    private TextView taskTimeLabel;
    private EditText taskName;
    private EditText taskTime;
    private EditText routineName;
    private boolean tSpinnerStart = false;
    private boolean rSpinnerStart = false;
    private ArrayList<TaskTable> tasks = new ArrayList<TaskTable>();
    private ArrayList<RoutineTable> routines = new ArrayList<RoutineTable>();
    private boolean mIsCreated = false;
    private DatabaseHelper mDbHelper;
    private IT_SpinnerAdapter spinnerAdapter;
    private IT_SpinnerRoutineAdapter spinnerRoutineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        mDbHelper = new DatabaseHelper(this);
        taskSpinner = (Spinner) findViewById(R.id.task_Spinner);
        routineSpinner = (Spinner) findViewById(R.id.routine_Spinner);
        spinnerAdapter = new IT_SpinnerAdapter(this, null);
        spinnerRoutineAdapter = new IT_SpinnerRoutineAdapter(this, null);
        taskSpinner.setAdapter(spinnerAdapter);
        routineSpinner.setAdapter(spinnerRoutineAdapter);
        taskTvName = findViewById(R.id.task_textView);
        taskName = findViewById(R.id.task_editText);
        taskTime = findViewById(R.id.duration_editTextTime);
        taskTimeLabel = findViewById(R.id.duration_textView);
        routineName = findViewById(R.id.routineName_editText);
        LoaderManager.getInstance(this).initLoader(LOADER_TASKS, null, this);
        LoaderManager.getInstance(this).initLoader(LOADER_ROUTINES, null, this);

        if (taskSpinner.getCount() == 0) {
            tSpinnerStart = true;
        }
        if (routineSpinner.getCount() == 0) {
            rSpinnerStart = true;
        }

        taskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (tSpinnerStart && (int) taskSpinner.getCount() > 1) {
                    tSpinnerStart = false;
                    taskSpinner.setSelection(1);
                }

                if (i == 0) {
                    taskTvName.setVisibility(View.VISIBLE);
                    taskName.setVisibility(View.VISIBLE);
                    taskTime.setVisibility(View.VISIBLE);
                    taskTimeLabel.setVisibility(View.VISIBLE);
                } else {
                    taskTvName.setVisibility(View.INVISIBLE);
                    taskName.setVisibility(View.INVISIBLE);
                    taskTime.setVisibility(View.INVISIBLE);
                    taskTimeLabel.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        routineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (rSpinnerStart && (int) routineSpinner.getCount() > 1) {
                    rSpinnerStart = false;
                    routineSpinner.setSelection(1);
                }

                if (i == 0) {
                    routineName.setVisibility(View.VISIBLE);
                } else {
                    routineName.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }


    public void saveTask(View view) {

        String name = taskName.getText().toString();
        String duration = taskTime.getText().toString();
        if (name.isEmpty() || duration.isEmpty()) {
            displayToast("Please fill out both fields.");
        } else {
            /**
             * TaskTable task = new TaskTable(tasks.size(), name, duration);
             *             tasks.add(task);
             */
            for (int i = 0; i < tasks.size(); i++ ) {
                System.out.println("Task " + tasks.get(i).getTaskID() + " " +
                        tasks.get(i).getTaskName() + " " + tasks.get(i).getTaskTime() +
                        " created!");
                displayToast("Task " + tasks.get(i).getTaskID() + " " +
                        tasks.get(i).getTaskName() + " " + tasks.get(i).getTaskTime() +
                        " created!");
            }
        }

    }

    public void saveRoutine(View view) {

        String tName;
        String tTime;
        String rName;
        Cursor tCursor;
        Cursor rCursor;
        int taskPosition = taskSpinner.getSelectedItemPosition();

        if (taskPosition == 0) {
            tName = taskName.getText().toString();
            tTime = taskTime.getText().toString();
        } else {
            tCursor = (Cursor) taskSpinner.getItemAtPosition(taskPosition);
            int tNamePosition =
                    tCursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME);
            tName = tCursor.getString(tNamePosition);
            int tTimePosition =
                    tCursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME);
            tTime = tCursor.getString(tTimePosition);
        }

        int routinePosition = routineSpinner.getSelectedItemPosition();
        if (routinePosition == 0) {
            rName = routineName.getText().toString();
        } else {
            rCursor = (Cursor) routineSpinner.getItemAtPosition(routinePosition);
            int rNamePosition =
                    rCursor.getColumnIndex(DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME);
            rName = rCursor.getString(rNamePosition);
        }

        if (tName == "" || tTime == "" || rName == "") {
            displayToast("Please fill out all fields");
            return;
        }

        if (taskPosition == 0) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String vTask = "SELECT _ID FROM " + DatabaseContract.TaskInfoEntry.TABLE1_NAME +
                    " WHERE " + DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME +
                    " = " + "'" + tName + "' AND " +
                    DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME + " = " + "'" + tTime + "'";
            Cursor tVerify = db.rawQuery(vTask, null);

            if (tVerify.getCount() > 0) {
                displayToast("A Task already exists with this name and time.  " +
                        "Please Enter/Select a different task.");
                tVerify.close();
                db.close();
                return;
            }
            tVerify.close();
            db.close();
        }

        if (routinePosition == 0) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String verifyQuery = "SELECT _ID FROM " + DatabaseContract.RoutineInfoEntry.TABLE2_NAME +
                    " WHERE " + DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME +
                    " = " + "'" + rName + "'";

            Cursor rVerify = db.rawQuery(verifyQuery, null);

            if (rVerify.getCount() > 0) {
                displayToast("A Routine already exists with this name.  " +
                        "Please Enter/Select a different routine.");
                rVerify.close();
                db.close();
                return;
            }
            rVerify.close();
            db.close();
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        boolean isInserted = false;
        if (routinePosition == 0) {
            isInserted = mDbHelper.insertRoutineData(rName);
            if (isInserted) {
                displayToast("Routine has been created");
            } else {
                displayToast("Routine creation has failed");
                db.close();
                return;
            }
        }
        if (taskPosition == 0) {
            isInserted = mDbHelper.insertTaskData(tName, tTime);
            if (isInserted) {
                displayToast("Task has been created");
            } else {
                displayToast("Task creation has failed");
                db.close();
                reloadLoader();
                return;
            }
        }

        // Extract ID numbers for the task and routine
        String vRout = "SELECT _ID FROM " + DatabaseContract.RoutineInfoEntry.TABLE2_NAME +
                " WHERE " + DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME +
                " = " + "'" + rName + "'";
        Cursor rVerify = db.rawQuery(vRout, null);
        int rIDPos = rVerify.getColumnIndex(DatabaseContract.RoutineInfoEntry._ID);
        rVerify.moveToFirst();
        int rID = rVerify.getInt(rIDPos);

        String vTask = "SELECT _ID FROM " + DatabaseContract.TaskInfoEntry.TABLE1_NAME +
                " WHERE " + DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME +
                " = " + "'" + tName + "' AND " +
                DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME + " = " + "'" + tTime + "'";
        Cursor tVerify = db.rawQuery(vTask, null);
        int tIDPos = tVerify.getColumnIndex(DatabaseContract.TaskInfoEntry._ID);
        tVerify.moveToFirst();
        int tID = tVerify.getInt(tIDPos);

        // Verify if there are any routine_Tasks that match  and set the order number
        String vRouteTask = "SELECT " +
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ORDERNUM +
                " FROM " + DatabaseContract.RoutineTaskInfoEntry.TABLE3_NAME +
                " WHERE " +
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ROUTINEID +
                " = " + "'" + rID + "'";
        Cursor rtVerify = db.rawQuery(vTask, null);
        int rtOrder = 0;

        if (rtVerify.getCount() > 0) {
            rtOrder = rtVerify.getCount();
        }

        isInserted = mDbHelper.insertRoutineTaskData(rID, tID, rtOrder);

        // CLose the Cursors
        rVerify.close();
        tVerify.close();
        rtVerify.close();

        if (isInserted) {
            displayToast("Adding Task to the Routine completed!");
            db.close();
            reloadLoader();
            return;
        } else {
            displayToast("Assigning Task to the Routine failed!");
            db.close();
            reloadLoader();
            return;
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create new cursor loader for Task dropdown
        CursorLoader loader = null;

        switch (id) {
            case LOADER_TASKS:
                loader = new CursorLoader(this) {
                    @Override
                    public Cursor loadInBackground() {
                        mIsCreated = true;
                        // Open your database in read mode.
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();

                        // Create a list of columns you want to return.
                        String[] taskColumns = {
                                DatabaseContract.TaskInfoEntry._ID,
                                DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME,
                                DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME};

                        // Create an order by field for sorting purposes
                        String taskOrderBy = DatabaseContract.TaskInfoEntry._ID;

                        return db.query(DatabaseContract.TaskInfoEntry.TABLE1_NAME,taskColumns,
                                null,null,null,null,taskOrderBy);
                    }
                };
                break;
            case LOADER_ROUTINES:
                loader = new CursorLoader(this) {
                    @Override
                    public Cursor loadInBackground() {
                        mIsCreated = true;
                        // Open your database in read mode.
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();

                        // Create a list of columns you want to return.
                        String[] routineColumns = {
                                DatabaseContract.RoutineInfoEntry._ID,
                                DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME};

                        // Create an order by field for sorting purposes
                        String routineOrderBy = DatabaseContract.RoutineInfoEntry._ID;

                        return db.query(
                                DatabaseContract.RoutineInfoEntry.TABLE2_NAME,routineColumns,
                                null,null,
                                null,null,routineOrderBy);
                    }
                };
                break;
            default:
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_TASKS:
                spinnerAdapter.swapCursor(data);
                mIsCreated = false;
                break;
            case LOADER_ROUTINES:
                spinnerRoutineAdapter.swapCursor(data);
                mIsCreated = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_TASKS:
                spinnerAdapter.swapCursor(null);
                break;
            case LOADER_ROUTINES:
                spinnerRoutineAdapter.swapCursor(null);
                break;
            default:
                break;
        }
    }

    public void reloadLoader() {
        LoaderManager.getInstance(this).initLoader(LOADER_TASKS, null, this);
        LoaderManager.getInstance(this).initLoader(LOADER_ROUTINES, null, this);
    }
}