package edu.cvtc.dkappus.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    private long START_TIME_IN_MILLIS;

    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean mTimerPause;
    private boolean mTimerReset;
    private boolean mTimerStart;

    private static final String TAG = ".TimerActivity.DEBUGGING";
    ArrayList<String[]> routine = new ArrayList<String[]>();
    public static final String ROUTINE_ID = "";
    private String rID;
    private DatabaseHelper mDbHelper;
    private TextView actTimer;
    private TextView currentTaskName;
    private int taskCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mDbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();

        rID = intent.getStringExtra(ROUTINE_ID);
        getRoutineData(Integer.valueOf(rID));
        currentTaskName = findViewById(R.id.current_task_textView);
        actTimer = findViewById(R.id.time_textView);
        mButtonStartPause = findViewById(R.id.btn_start_pause);
        mButtonReset = findViewById(R.id.btn_reset);
        currentTaskName.setText(routine.get(0)[0]);
        actTimer.setText(routine.get(0)[1]);
        taskCount = 0;

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();

        //timerTest();
    }

    private void convertTime() {
        currentTaskName.setText(routine.get(taskCount)[0]);
        actTimer.setText(routine.get(taskCount)[1]);
        String[] parts = routine.get(taskCount)[1].split(":");
        int hours = Integer.parseInt(parts[0]) * 60 * 60;
        int minutes = Integer.parseInt(parts[1]) * 60;
        int seconds = Integer.parseInt(parts[2]);
        int parsedTime = hours + minutes + seconds;
        START_TIME_IN_MILLIS = parsedTime * 1000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        taskCount++;
    }

    private void startTimer() {

        if (!mTimerReset && !mTimerPause) {
            convertTime();
        } else {
            mTimerPause = false;
            mTimerReset = false;
        }

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                if (taskCount != routine.size()) {
                    startTimer();
                } else {
                    mTimerRunning = false;
                    mButtonStartPause.setText("Start");
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerPause = true;
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
        mButtonReset.setText("Reset");
    }

    private void resetTimer() {
        mTimerReset = true;
        taskCount = 0;
        convertTime();
        updateCountDownText();
        mButtonReset.setVisibility(View.VISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        if (!mTimerStart) {
            convertTime();
            taskCount = 0;
            mTimerStart = true;
        }
        int hours = (int) ((mTimeLeftInMillis / (1000*60*60)) % 24);
        int minutes = (int) ((mTimeLeftInMillis / (1000*60)) % 60);
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minutes,seconds);

        actTimer.setText(timeLeftFormatted);
    }

//    private void timerTest() {
//        currentTaskName.setText(routine.get(taskCount)[0]);
//        actTimer.setText(routine.get(taskCount)[1]);
//        String[] parts = routine.get(taskCount)[1].split(":");
//        int hours = Integer.parseInt(parts[0]) * 60 * 60;
//        int minutes = Integer.parseInt(parts[1]) * 60;
//        int seconds = Integer.parseInt(parts[2]);
//        int parsedTime = hours + minutes + seconds;
//        inter = parsedTime;
//        taskCount++;
//
//        new CountDownTimer(parsedTime*1000, 1000) {
//
//            @Override
//            public void onTick(long l) {
//                inter--;
//                int hh = (int) (inter/(60*60));
//                int mm = (int) ((inter - (hh*60*60))/60);
//                int ss = (int) (inter - ((hh*60*60)+(mm*60)));
//                String h = "00" + String.valueOf(hh);
//                String m = "00" + String.valueOf(mm);
//                String s = "00" + String.valueOf(ss);
//                actTimer.setText(h.substring(h.length() - 2, h.length()) + ":" +
//                        m.substring(m.length() - 2, m.length()) +
//                        ":" + s.substring(s.length() - 2, s.length()));
//            }
//
//            @Override
//            public void onFinish() {
//                if (taskCount != routine.size()) {
//                    timerTest();
//                }
//            }
//        }.start();
//    }

    private void getRoutineData(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String routineQuery = "SELECT rt.routine_id as routine_id, t.task_name as task_name, " +
                "t." + DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME + " as " +
                DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME + ", " +
                "rt.routine_order_number as routine_order_number FROM " +
                DatabaseContract.RoutineTaskInfoEntry.TABLE3_NAME +
                " rt INNER JOIN " + DatabaseContract.TaskInfoEntry.TABLE1_NAME + " t ON rt." +
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_TASKID + " = t." +
                DatabaseContract.TaskInfoEntry._ID + " WHERE " +
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ROUTINEID +
                " = " + "'" + id + "' ORDER BY " +
                DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ORDERNUM + " ASC";
        Cursor rtVerify = db.rawQuery(routineQuery, null);
        Log.d(TAG, "getRoutineData: " + String.valueOf(rtVerify.getCount()));
        try {
            for (rtVerify.moveToFirst(); !rtVerify.isAfterLast(); rtVerify.moveToNext()) {
                int rtIDPos = rtVerify.getColumnIndex(
                        DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ROUTINEID);
                int rtID = rtVerify.getInt(rtIDPos);

                int tNamePos = rtVerify.getColumnIndex(
                        DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME);
                String tName = rtVerify.getString(tNamePos);

                int rtOrderPos = rtVerify.getColumnIndex(
                        DatabaseContract.RoutineTaskInfoEntry.COLUMN_ROUTINE_TASKS_ORDERNUM);
                int rtOrder = rtVerify.getInt(rtOrderPos);

                int tTimePos = rtVerify.getColumnIndex(
                        DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME);
                String tTime = rtVerify.getString(tTimePos);

                routine.add(new String[]{tName, tTime});
            }
        } finally {
            rtVerify.close();
        }



    }


}