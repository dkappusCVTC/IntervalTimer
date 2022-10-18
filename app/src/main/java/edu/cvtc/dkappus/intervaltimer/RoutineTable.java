package edu.cvtc.dkappus.intervaltimer;

import android.os.Parcel;
import android.os.Parcelable;

public class RoutineTable implements Parcelable {

    //Attributes
    private int mRoutineID;
    private String mRoutineName;
    private int mOrderNumber;
    private int mTaskID;

    public RoutineTable(String name, int orderNumber, int taskID) {
        mRoutineName = name;
        mOrderNumber = orderNumber;
        mTaskID = taskID;
    }

    public RoutineTable(int routineID, String routineName, int orderNumber, int taskID) {
        mRoutineID = routineID;
        mRoutineName = routineName;
        mOrderNumber = orderNumber;
        mTaskID = taskID;
    }

    protected RoutineTable(Parcel in) {
        mRoutineID = in.readInt();
        mRoutineName = in.readString();
        mOrderNumber = in.readInt();
        mTaskID = in.readInt();
    }

    public static final Creator<RoutineTable> CREATOR = new Creator<RoutineTable>() {
        @Override
        public RoutineTable createFromParcel(Parcel in) {
            return new RoutineTable(in);
        }

        @Override
        public RoutineTable[] newArray(int size) {
            return new RoutineTable[size];
        }
    };

    public int getRoutineID() {
        return mRoutineID;
    }

    public void setRoutineID(int routineID) {
        mRoutineID = routineID;
    }

    public String getRoutineName() {
        return mRoutineName;
    }

    public void setRoutineName(String routineName) {
        mRoutineName = routineName;
    }

    public int getSequenceNumber() {
        return mOrderNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        mOrderNumber = sequenceNumber;
    }

    public int getTaskID() {
        return mTaskID;
    }

    public void setTaskID(int taskID) {
        mTaskID = taskID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mRoutineID);
        parcel.writeString(mRoutineName);
        parcel.writeInt(mOrderNumber);
        parcel.writeInt(mTaskID);
    }
}
