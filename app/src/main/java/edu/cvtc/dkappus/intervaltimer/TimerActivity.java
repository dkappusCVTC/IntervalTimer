package edu.cvtc.dkappus.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = ".TimerActivity.DEBUGGING";
    ArrayList<IntervalTimerModel> intervalTimerModels =
            IntervalTimerModel.getInstance().intervalTimerModels;

    public static final String ROUTINE_ID = "";
    private List<TaskTable> taskTable = DataManager.getInstance().getTasks();

    private String rID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();

        rID = intent.getStringExtra(ROUTINE_ID);

        TextView currentTaskName = findViewById(R.id.current_task_textView);
        TextView timer = findViewById(R.id.time_textView);

        for (int i = 0; i < taskTable.size(); i++) {
            if (String.valueOf(taskTable.get(i).getTaskID()).equals(rID)) {
                currentTaskName.setText(taskTable.get(i).getTaskName());
                timer.setText(taskTable.get(i).getTaskTime());
                break;
            }
        }

    }


}