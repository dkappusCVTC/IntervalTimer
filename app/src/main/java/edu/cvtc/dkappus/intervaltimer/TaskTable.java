package edu.cvtc.dkappus.intervaltimer;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskTable implements Parcelable {

    // Attributes
    private int taskID;
    private String taskName;
    private String taskTime;


    // Overload Constructor
    public TaskTable(int taskID, String taskName, String taskTime) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskTime = taskTime;
    }

    protected TaskTable(Parcel in) {
        taskID = in.readInt();
        taskName = in.readString();
        taskTime = in.readString();
    }

    public static final Creator<TaskTable> CREATOR = new Creator<TaskTable>() {
        @Override
        public TaskTable createFromParcel(Parcel in) {
            return new TaskTable(in);
        }

        @Override
        public TaskTable[] newArray(int size) {
            return new TaskTable[size];
        }
    };

    public int getTaskID() {
        return taskID;
    }


    public String getTaskName() {
        return taskName;
    }


    public String getTaskTime() {
        return taskTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(taskID);
        parcel.writeString(taskName);
        parcel.writeString(taskTime);
    }
}
