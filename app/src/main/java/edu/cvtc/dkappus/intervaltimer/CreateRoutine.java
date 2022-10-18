package edu.cvtc.dkappus.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import edu.cvtc.dkappus.intervaltimer.DatabaseContract;

public class CreateRoutine extends AppCompatActivity {

    ArrayList<IntervalTimerModel> intervalTimerModels = IntervalTimerModel.getInstance().intervalTimerModels;
    //IT_RecyclerViewAdapter adapter = new IT_RecyclerViewAdapter(this, intervalTimerModels);

    private EditText taskName;
    private EditText taskTime;
    private EditText routineName;
    int routineNbr = intervalTimerModels.size();
    private ArrayList<TaskTable> tasks = new ArrayList<TaskTable>();
    private ArrayList<RoutineTable> routines = new ArrayList<RoutineTable>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

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


        if (!name.isEmpty() && duration.isEmpty()) {
            TaskTable task = new TaskTable(tasks.size(), name, duration);
            tasks.add(task);
        }

        for (int i = 0; i < tasks.size(); i++) {
            RoutineTable routine =
                    new RoutineTable(routineNbr, routName, i, tasks.get(i).getTaskID());
            routines.add(routine);
        }
        System.out.println("Routine #: " + routineNbr);

        intervalTimerModels.add(new IntervalTimerModel(routName.toString(), duration.toString(), String.valueOf(routineNbr).toString()));
        //adapter.notifyItemInserted(routineNbr);
        routineNbr++;
        
        for (int i = 0; i < routines.size(); i++) {
            if (routines.get(i).getRoutineID() == 3) {
                System.out.println("Task ID#:" +
                        tasks.get(routines.get(i).getTaskID()).getTaskID());
            }

        }

    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}