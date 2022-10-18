package edu.cvtc.dkappus.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    ArrayList<IntervalTimerModel> intervalTimerModels =
            IntervalTimerModel.getInstance().intervalTimerModels;

    public static final String ROUTINE_ID = "";

    private String rID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();

        rID = intent.getStringExtra(ROUTINE_ID);

        TextView currentTaskName = findViewById(R.id.current_task_textView);
        TextView timer = findViewById(R.id.time_textView);

        currentTaskName.setText(intervalTimerModels.get(Integer.parseInt(rID)).getRoutineName());
        timer.setText(intervalTimerModels.get(Integer.parseInt(rID)).getNbrTasks());



    }


}