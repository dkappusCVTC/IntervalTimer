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

    public TaskTable(String taskName, String taskTime) {
        this.taskName = taskName;
        this.taskTime = taskTime;
    }

    public int getTaskID() {
        return taskID;
    }


    public String getTaskName() {
        return taskName;
    }


    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskName(String tName) { this.taskName = tName;}

    public void setTaskTime(String tTime) {this.taskTime = tTime;}

    private String getCompareKey() {return this.taskName + "|" + this.taskTime;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskTable that = (TaskTable) o;
        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {return getCompareKey().hashCode();}

    @Override
    public String toString() {return getCompareKey();}

    protected TaskTable(Parcel parcel) {
        setTaskName(parcel.readString());
        setTaskTime(parcel.readString());
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(taskName);
        parcel.writeString(taskTime);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskTable> CREATOR = new Creator<TaskTable>() {

        @Override
        public TaskTable createFromParcel(Parcel parcel) {
            return new TaskTable(parcel);
        }

        @Override
        public TaskTable[] newArray(int size) {
            return new TaskTable[size];
        }
    };


}
