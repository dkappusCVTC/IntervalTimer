package edu.cvtc.dkappus.intervaltimer;

import android.os.Parcel;
import android.os.Parcelable;

public class RoutineTable implements Parcelable {

    //Attributes
    private int mRoutineID;
    private String mRoutineName;

    public RoutineTable(String name) {
        mRoutineName = name;
    }

    public RoutineTable(int routineID, String routineName) {
        mRoutineID = routineID;
        mRoutineName = routineName;
    }

    protected RoutineTable(Parcel in) {
        mRoutineID = in.readInt();
        mRoutineName = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mRoutineID);
        parcel.writeString(mRoutineName);
    }
}
