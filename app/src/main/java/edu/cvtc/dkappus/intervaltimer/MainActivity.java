package edu.cvtc.dkappus.intervaltimer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
//import androidx.navigation.ui.AppBarConfiguration;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cvtc.dkappus.intervaltimer.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Constants
    private static final String TAG = ".MainActivity.DEBUGGING";
    public static final int LOADER_TASKS = 0;
    public static final int LOADER_ROUTINES = 1;
    public static final String EXTRA_MESSAGE = "edu.cvtc.dkappus.intervaltimer.extra.MESSAGE";

    // Member Variables
    private DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mITLayoutManager;
    private IT_RecyclerViewAdapter mITRecyclerAdapter;
    private boolean mIsCreated = false;

    //ArrayList<IntervalTimerModel> intervalTimerModels = IntervalTimerModel.getInstance().intervalTimerModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //private AppBarConfiguration appBarConfiguration;
        edu.cvtc.dkappus.intervaltimer.databinding.ActivityMainBinding binding =
                ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        mDbHelper = new DatabaseHelper(this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateRoutine.class);
                startActivity(intent);
            }
        });

        //setUpIntervalTimerModels();
        /*
        IT_RecyclerViewAdapter adapter =
                new IT_RecyclerViewAdapter(this, intervalTimerModels);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        initializeTest();

    }

    private void initializeDisplay() {
        DataManager.loadFromDatabase(mDbHelper);
        boolean dataSet = mDbHelper.populateBlankDatabase();
        if (!dataSet) {
            Toast.makeText(this, "Data not set", Toast.LENGTH_SHORT).show();
        }

        // Set a reference to your list of items layout
        mRecyclerView = findViewById(R.id.it_RecyclerView);
        mITLayoutManager = new LinearLayoutManager(this);


        // We do not have a cursor yet, so pass null  (MAY BREAK IF NULL)
        mITRecyclerAdapter = new IT_RecyclerViewAdapter(this, null);

        // Display the routines
        displayRoutines();
    }

    private void displayRoutines() {
        mRecyclerView.setLayoutManager(mITLayoutManager);
        mRecyclerView.setAdapter(mITRecyclerAdapter);
    }

    private void initializeTest() {

        // Testing Joining
        /*String jQuery = "SELECT COUNT(rt.routine_id) as tasks FROM " + DatabaseContract.RoutineInfoEntry.TABLE2_NAME +
                " r INNER JOIN " + DatabaseContract.RoutineTaskInfoEntry.TABLE3_NAME + " rt ON r._ID = rt.routine_id";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor jCursor = db.rawQuery(jQuery, null);
        int rIdPos = jCursor.getColumnIndex("ID");
        int rNamePos = jCursor.getColumnIndex("routine_name");
        int rTaskPos = jCursor.getColumnIndex("tasks");
        jCursor.moveToFirst();
        Log.d(TAG, "initializeTest: Columns:" + jCursor.getInt(rIdPos) + " " + jCursor.getString(rNamePos) + " " + jCursor.getInt(rTaskPos));*/
        // Testing Insert into DB
        /*int taskID = 0;
        String taskName = "Create a new Task";
        String taskTime = " ";
        String routineName = "Walking";

        boolean isInserted = mDbHelper.insertTaskData(taskID, taskName, taskTime);

        if (isInserted == true) {
            Toast.makeText(MainActivity.this,
                    "Data Inserted", Toast.LENGTH_SHORT).show();
            //isInserted = mDbHelper.insertRoutineData("Walking");
            if (isInserted == true) {
                //Toast.makeText(MainActivity.this,
                  //      "Routine Inserted", Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                String rNameQuery = "SELECT _ID FROM " + DatabaseContract.RoutineInfoEntry.TABLE2_NAME +
                        " WHERE " + DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME +
                        " = " + "'" + routineName + "'";
                String tIDQuery = "SELECT _ID FROM " + DatabaseContract.TaskInfoEntry.TABLE1_NAME +
                        " WHERE " + DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME +
                        " = " + "'" + taskName + "'";

                Cursor rCursor = db.rawQuery(rNameQuery, null);
                int rPos = rCursor.getColumnIndex(DatabaseContract.RoutineInfoEntry._ID);
                rCursor.moveToFirst();
                Cursor tCursor = db.rawQuery(tIDQuery, null);
                int tPos = tCursor.getColumnIndex(DatabaseContract.TaskInfoEntry._ID);
                tCursor.moveToFirst();
                isInserted = mDbHelper.insertRoutineTaskData(rCursor.getInt(rPos), tCursor.getInt(tPos), 0);
                rCursor.close();
                tCursor.close();
                db.close();

                if (isInserted == true) {
                    Toast.makeText(MainActivity.this,
                            "Routine Task Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Routine task failed to Insert", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this,
                        "Routine failed to Insert", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this,
                    "Data Failed to Insert", Toast.LENGTH_SHORT).show();
        }*/

        // End Testing
        initializeDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                return true;
            case R.id.action_create:
                Intent createIntent = new Intent(MainActivity.this, CreateRoutine.class);
                startActivity(createIntent);
                return true;
            case R.id.action_timer:
                Intent timerIntent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(timerIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // User restartLoader instead of initLoader to make sure
        // you re-query the database each time the activity is
        // loaded in the app.
        LoaderManager.getInstance(this).restartLoader(LOADER_TASKS, null,this);
        LoaderManager.getInstance(this).restartLoader(LOADER_ROUTINES, null,this);
    }

    /*
    public ArrayList<IntervalTimerModel> getIntervalTimerModels() {
        return intervalTimerModels;
    }

    public void setIntervalTimerModels2(String name, String duration, String id) {
        intervalTimerModels.add(new IntervalTimerModel(name, duration, id));
    }
    */

//    private void setUpIntervalTimerModels() {
//        if (intervalTimerModels.isEmpty()) {
//            String[] intervalTimerNames =
//                    getResources().getStringArray(R.array.interval_timer_routine_name);
//            String[] intervalTimerTasks =
//                    getResources().getStringArray(R.array.interval_timer_tasks);
//            for (int i = 0; i < intervalTimerNames.length; i++) {
//                intervalTimerModels.add(
//                        new IntervalTimerModel(
//                                intervalTimerNames[i], intervalTimerTasks[i], String.valueOf(i)));
//            }
//
//        }
//
//        /*
//        String[] intervalTimerNames =
//                getResources().getStringArray(R.array.interval_timer_routine_name);
//        String[] intervalTimerId = getResources().getStringArray(R.array.interval_timer_routine_id);
//        String[] intervalTimerTasks = getResources().getStringArray(R.array.interval_timer_tasks);
//
//        for (int i = 0; i < intervalTimerNames.length; i++) {
//            intervalTimerModels.add(new IntervalTimerModel(intervalTimerNames[i],
//                    intervalTimerTasks[i], intervalTimerId[i]));
//        }
//        */
//
//    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create new cursor loader
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
                        /*String[] taskColumns = {
                                DatabaseContract.TaskInfoEntry._ID,
                                DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME,
                                DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME};*/

                        // RecyclerView query
                        String rQuery = "SELECT r._ID as ID, r.routine_name as routine_name, " +
                                "COUNT(rt.routine_id) as tasks FROM " +
                                DatabaseContract.RoutineInfoEntry.TABLE2_NAME +
                                " r INNER JOIN " +
                                DatabaseContract.RoutineTaskInfoEntry.TABLE3_NAME +
                                " rt ON r._ID = rt.routine_id GROUP BY r._ID, r.routine_name";

                        // Create an order by field for sorting purposes
                        String taskOrderBy = DatabaseContract.TaskInfoEntry._ID;

                        return db.rawQuery(rQuery, null);
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

                        // Create a list of columns to return from routine table
                        String[] routineColumns = {
                                DatabaseContract.RoutineInfoEntry._ID,
                                DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME};

                        // Create an order by field for sorting purposes
                        String routineOrderBy = DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME;

                        return db.query(DatabaseContract.RoutineInfoEntry.TABLE2_NAME,
                                routineColumns,
                                null,null,null,null,
                                routineOrderBy);
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
                // Associate the cursor with your RecyclerAdapter
                mITRecyclerAdapter.changeCursor(data);
                mIsCreated = false;
                break;
            case LOADER_ROUTINES:
                // Associate the cursor with your RecyclerAdapter
                //mITRecyclerAdapter.changeCursor(data);
                break;
            default:
                break;
        }
//        if (loader.getId() == LOADER_TASKS && mIsCreated) {
//            // Associate the cursor with your RecyclerAdapter
//            mITRecyclerAdapter.changeCursor(data);
//            mIsCreated = false;
//        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == LOADER_TASKS || loader.getId() == LOADER_ROUTINES) {
            // Change the cursor to null (cleanup)
            mITRecyclerAdapter.changeCursor(null);
        }
    }
}