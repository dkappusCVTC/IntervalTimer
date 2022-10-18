package edu.cvtc.dkappus.intervaltimer;

import java.util.ArrayList;

public class IntervalTimerModel {
    String routineName;
    String nbrTasks;
    String id;

    final ArrayList<IntervalTimerModel> intervalTimerModels = new ArrayList<>();

    private IntervalTimerModel() {};

    static IntervalTimerModel getInstance() {
        if (instance == null) {
            instance = new IntervalTimerModel();
        }
        return instance;
    }
    private static IntervalTimerModel instance;


    public IntervalTimerModel(String routineName, String nbrTasks, String id) {
        this.routineName = routineName;
        this.nbrTasks = nbrTasks;
        this.id = id;
    }

    public String getRoutineName() {
        return routineName;
    }

    public String getNbrTasks() {
        return nbrTasks;
    }

    public String getId() {
        return id;
    }

}
