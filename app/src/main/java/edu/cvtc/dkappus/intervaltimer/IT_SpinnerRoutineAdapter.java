package edu.cvtc.dkappus.intervaltimer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

public class IT_SpinnerRoutineAdapter extends CursorAdapter {

    private Cursor mCursor;
    private Context mContext;
    private int mNamePosition;
    private int mLastPosition;
    private int mIDPosition;
    private DatabaseHelper mDbHelper;

    class ViewsHolder {
        TextView text4, text5, text6;
    }

    public IT_SpinnerRoutineAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mContext = context;
        this.mCursor = cursor;

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (mCursor != null) {
            mNamePosition =
                    mCursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME);
            mLastPosition =
                    mCursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME);
            mIDPosition =
                    mCursor.getColumnIndex(DatabaseContract.TaskInfoEntry._ID);
        }

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.routine_spinner_layout,
                parent, false);
        IT_SpinnerRoutineAdapter.ViewsHolder holder = new ViewsHolder();
        holder.text4 = (TextView) v.findViewById(R.id.text4);
        holder.text5 = (TextView) v.findViewById(R.id.text5);
        holder.text6 = (TextView) v.findViewById(R.id.text6);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        IT_SpinnerRoutineAdapter.ViewsHolder holder =
                (IT_SpinnerRoutineAdapter.ViewsHolder) view.getTag();
        mCursor = cursor;
        mIDPosition =
                mCursor.getColumnIndex(DatabaseContract.RoutineInfoEntry._ID);
        mNamePosition =
                mCursor.getColumnIndex(
                        DatabaseContract.RoutineInfoEntry.COLUMN_ROUTINE_NAME);

        String tasksQuery = "SELECT COUNT(routine_id) as tasks FROM " +
                DatabaseContract.RoutineTaskInfoEntry.TABLE3_NAME +
                " WHERE routine_id = " + "'" + mCursor.getInt(mIDPosition) + "'";
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor tasksCursor = db.rawQuery(tasksQuery, null);
        int taskNum = tasksCursor.getColumnIndex("tasks");
        tasksCursor.moveToFirst();

        mLastPosition =
                tasksCursor.getInt(taskNum);
        tasksCursor.close();
        db.close();

        int text4 = cursor.getInt(mIDPosition);
        String text5 = cursor.getString(mNamePosition);
        String text6 = String.valueOf(mLastPosition) + " Tasks";
        holder.text4.setText(String.valueOf(text4));
        holder.text5.setText(text5);
        holder.text6.setText(text6);
    }
}

