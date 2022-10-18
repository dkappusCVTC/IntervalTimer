package edu.cvtc.dkappus.intervaltimer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.cvtc.dkappus.intervaltimer.databinding.ActivityMainBinding;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Constants
    public static final String EXTRA_MESSAGE = "edu.cvtc.dkappus.intervaltimer.extra.MESSAGE";

    // Member Variables
    private DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mITLayoutManager;
    private IT_RecyclerViewAdapter mITRecyclerAdapter;

    ArrayList<IntervalTimerModel> intervalTimerModels = IntervalTimerModel.getInstance().intervalTimerModels;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //mRecyclerView = findViewById(R.id.it_RecyclerView);

        mDbHelper = new DatabaseHelper(this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateRoutine.class);
                startActivity(intent);
            }
        });

        setUpIntervalTimerModels();
        /*
        IT_RecyclerViewAdapter adapter =
                new IT_RecyclerViewAdapter(this, intervalTimerModels);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        initializeTest();

    }

    private void initializeDisplay() {
        DataManager.loadFromDatabase(mDbHelper);

        // Set a reference to your list of items layout
        mRecyclerView = findViewById(R.id.it_RecyclerView);
        mITLayoutManager = new LinearLayoutManager(this);


        // We do not have a cursor yet, so pass null  (NOT NULL CURRENTLY, OR IT BREAKS)
        mITRecyclerAdapter = new IT_RecyclerViewAdapter(this, intervalTimerModels);

        // Display the routines
        displayRoutines();
    }

    private void displayRoutines() {
        mRecyclerView.setLayoutManager(mITLayoutManager);
        mRecyclerView.setAdapter(mITRecyclerAdapter);
    }

    private void initializeTest() {
        // Testing Insert into DB
        /*boolean isInserted = mDbHelper.insertData("Test", "12:00:00");

        if (isInserted == true) {
            Toast.makeText(MainActivity.this,
                    "Data Inserted", Toast.LENGTH_SHORT).show();
            isInserted = mDbHelper.insertRoutineData("Walking", 1, 1);
        } else {
            Toast.makeText(MainActivity.this,
                    "Data Failed to Insert", Toast.LENGTH_SHORT).show();
        }

        if (isInserted == true) {
            Toast.makeText(MainActivity.this,
                    "Routine Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "Routine failed to Insert", Toast.LENGTH_SHORT).show();
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

    public ArrayList<IntervalTimerModel> getIntervalTimerModels() {
        return intervalTimerModels;
    }

    public void setIntervalTimerModels2(String name, String duration, String id) {
        intervalTimerModels.add(new IntervalTimerModel(name, duration, id));
    }

    private void setUpIntervalTimerModels() {
        if (intervalTimerModels.isEmpty()) {
            String[] intervalTimerNames =
                    getResources().getStringArray(R.array.interval_timer_routine_name);
            String[] intervalTimerTasks =
                    getResources().getStringArray(R.array.interval_timer_tasks);
            for (int i = 0; i < intervalTimerNames.length; i++) {
                intervalTimerModels.add(
                        new IntervalTimerModel(
                                intervalTimerNames[i], intervalTimerTasks[i], String.valueOf(i)));
            }

        }

        /*
        String[] intervalTimerNames =
                getResources().getStringArray(R.array.interval_timer_routine_name);
        String[] intervalTimerId = getResources().getStringArray(R.array.interval_timer_routine_id);
        String[] intervalTimerTasks = getResources().getStringArray(R.array.interval_timer_tasks);

        for (int i = 0; i < intervalTimerNames.length; i++) {
            intervalTimerModels.add(new IntervalTimerModel(intervalTimerNames[i],
                    intervalTimerTasks[i], intervalTimerId[i]));
        }
        */

    }

}