package edu.cvtc.dkappus.intervaltimer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IT_RecyclerViewAdapter extends RecyclerView.Adapter<IT_RecyclerViewAdapter.MyViewHolder> {
    // Member variables
    Context mContext;
    ArrayList<IntervalTimerModel> mIntervalTimerModels;

    public IT_RecyclerViewAdapter(Context context, ArrayList<IntervalTimerModel> intervalTimerModels) {
        this.mContext = context;
        this.mIntervalTimerModels = intervalTimerModels;
    }

    @NonNull
    @Override
    public IT_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);

        return new IT_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IT_RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.tvName.setText(mIntervalTimerModels.get(position).getRoutineName());
        holder.tvTasks.setText(mIntervalTimerModels.get(position).getNbrTasks());
        holder.tvId.setText(mIntervalTimerModels.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mIntervalTimerModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvTasks, tvId;

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
