package edu.cvtc.dkappus.intervaltimer;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

public class IT_SpinnerAdapter extends CursorAdapter {

    private Cursor mCursor;
    private Context mContext;
    private int mNamePosition;
    private int mTimePosition;
    private int mIDPosition;

    class ViewsHolder {
        TextView text1, text2, text3;
    }

    public IT_SpinnerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mContext = context;
        this.mCursor = cursor;

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (mCursor != null) {
            mNamePosition =
                    mCursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME);
            mTimePosition =
                    mCursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME);
            mIDPosition =
                    mCursor.getColumnIndex(DatabaseContract.TaskInfoEntry._ID);
        }

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.spinner_layout,
                parent, false);
        ViewsHolder holder = new ViewsHolder();
        holder.text1 = (TextView) v.findViewById(R.id.text1);
        holder.text2 = (TextView) v.findViewById(R.id.text2);
        holder.text3 = (TextView) v.findViewById(R.id.text3);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewsHolder holder = (ViewsHolder) view.getTag();

        mNamePosition =
                cursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_NAME);
        mTimePosition =
                cursor.getColumnIndex(DatabaseContract.TaskInfoEntry.COLUMN_TASK_TIME);
        mIDPosition =
                cursor.getColumnIndex(DatabaseContract.TaskInfoEntry._ID);

        int text1 = cursor.getInt(mIDPosition);
        String text2 = cursor.getString(mNamePosition);
        String text3 = cursor.getString(mTimePosition);
        holder.text1.setText(String.valueOf(text1));
        holder.text2.setText(text2);
        holder.text3.setText(text3);

    }
}
