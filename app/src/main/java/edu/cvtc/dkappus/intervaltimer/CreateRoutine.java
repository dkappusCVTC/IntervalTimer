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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import edu.cvtc.dkappus.intervaltimer.DatabaseContract;

public class CreateRoutine extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ".CreateRoutine.DEBUGGING";
    //ArrayList<IntervalTimerModel> intervalTimerModels = IntervalTimerModel.getInstance().intervalTimerModels;
    //IT_RecyclerViewAdapter adapter = new IT_RecyclerViewAdapter(this, intervalTimerModels);
    private Spinner taskSpinner;
    private Spinner routineSpinner;
    public static final int LOADER_TASKS = 0;
    public static final int LOADER_ROUTINES = 1;
    private EditText taskName;
    private EditText taskTime;
    private EditText routineName;
    //int routineNbr = intervalTimerModels.size();
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
        LoaderManager.getInstance(this).initLoader(LOADER_TASKS, null, this);
        LoaderManager.getInstance(this).initLoader(LOADER_ROUTINES, null, this);


        //LoaderManager.getInstance(this).restartLoader(LOADER_TASKS, null, this);
        /*
        Spinner taskSpinner = (Spinner) findViewById(R.id.task_Spinner);
        Spinner routineSpinner = (Spinner) findViewById(R.id.routine_Spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateRoutine.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tasks));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(myAdapter);*/

        taskName = findViewById(R.id.task_editText);
        taskTime = findViewById(R.id.duration_editTextTime);
        routineName = findViewById(R.id.routineName_editText);



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

        String name = taskName.getText().toString();
        String duration = taskTime.getText().toString();
        String routName = routineName.getText().toString();


//        if (!name.isEmpty() && duration.isEmpty()) {
//            TaskTable task = new TaskTable(tasks.size(), name, duration);
//            tasks.add(task);
//        }
//
//        for (int i = 0; i < tasks.size(); i++) {
//            RoutineTable routine =
//                    new RoutineTable(routineNbr, routName, i, tasks.get(i).getTaskID());
//            routines.add(routine);
//        }
//        System.out.println("Routine #: " + routineNbr);
//
//        intervalTimerModels.add(new IntervalTimerModel(routName.toString(), duration.toString(), String.valueOf(routineNbr).toString()));
//        //adapter.notifyItemInserted(routineNbr);
//        routineNbr++;
//
//        for (int i = 0; i < routines.size(); i++) {
//            if (routines.get(i).getRoutineID() == 3) {
//                System.out.println("Task ID#:" +
//                        tasks.get(routines.get(i).getTaskID()).getTaskID());
//            }
//
//        }

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
}