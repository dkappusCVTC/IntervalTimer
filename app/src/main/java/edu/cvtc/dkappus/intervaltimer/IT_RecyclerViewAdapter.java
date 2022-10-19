package edu.cvtc.dkappus.intervaltimer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IT_RecyclerViewAdapter extends RecyclerView.Adapter<IT_RecyclerViewAdapter.MyViewHolder> {
    // Member variables

    private final Context mContext;
    private final LayoutInflater mInflater;
    private Cursor mCursor;
    private int mITNamePosition;
    private int mITTaskPosition;
    private int mITIDPosition;

    public IT_RecyclerViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        this.mInflater = LayoutInflater.from(context);

        // Used to get the positions of the columns we
        // are interested in.
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (mCursor != null) {
            // Get column indexes from mCursor
            mITNamePosition = mCursor.getColumnIndex("routine_name");
            mITTaskPosition = mCursor.getColumnIndex("tasks");
            mITIDPosition = mCursor.getColumnIndex("ID");
        }
    }

    public void changeCursor(Cursor cursor) {
        // If the cursor is open, close it
        if (mCursor != null) {
            mCursor.close();
        }

        // Create a new cursor based upon
        // the object passed in.
        mCursor = cursor;

        // Get the positions of the columns in
        // your cursor.
        populateColumnPositions();

        // Tell the activity that the data set
        // has changed.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    // Uses the information from the inner class
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Move the cursor to the correct row
        mCursor.moveToPosition(position);

        // Get the actual values
        String taskName =
                mCursor.getString(mITNamePosition);
        int taskTime =
                mCursor.getInt(mITTaskPosition);
        int taskID =
                mCursor.getInt(mITIDPosition);

        // Pass the information into the holder
        holder.tvName.setText(taskName);
        holder.tvTasks.setText(String.valueOf(taskTime));
        holder.tvId.setText(String.valueOf(taskID));
    }

    @Override
    public int getItemCount() {
        // If the cursor is null, return 0. Otherwise
        // return the count of records in it.
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        // Member variables for inner class
        public final TextView tvName;
        public final TextView tvTasks;
        public final TextView tvId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.card_tv_Routine_Name);
            tvTasks = itemView.findViewById(R.id.card_tv_Tasks);
            tvId = itemView.findViewById(R.id.card_tv_Id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TimerActivity.class);
                    intent.putExtra(TimerActivity.ROUTINE_ID, tvId.getText());
                    mContext.startActivity(intent);

                }

            });
        }
    }
}
