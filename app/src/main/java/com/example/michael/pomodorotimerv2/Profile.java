package com.example.michael.pomodorotimerv2;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class Profile extends Object{
    private static final String JSON_NAME = "name";
    private static final String JSON_WORK = "work";
    private static final String JSON_REST = "rest";
    private static final String JSON_INTERVAL = "interval";

    private String name;
    private float workTime;
    private float restTime;
    private int interval;

    public Profile(String name, float wortkTime, float restTime, int interval) {
        this.name = name;
        this.workTime = wortkTime;
        this.restTime = restTime;
        this.interval = interval;
    }

    public Profile(JSONObject json) throws JSONException{
        name = json.getString(JSON_NAME);
        workTime = Float.parseFloat(json.getString(JSON_WORK));
        restTime = Float.parseFloat(json.getString(JSON_REST));
        interval = json.getInt(JSON_INTERVAL);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, name);
        json.put(JSON_WORK, workTime);
        json.put(JSON_REST, restTime);
        json.put(JSON_INTERVAL, interval);
        return json;
    }

    public Profile(String name, String workTime, String restTime, String interval){
        this.name = name;
        this.workTime = Float.parseFloat(workTime);
        this.restTime = Float.parseFloat(restTime);
        this.interval = Integer.parseInt(interval);
    }

    public String getName() {
        return name;
    }

    public float getWorkTime() {
        return workTime;
    }

    public float getRestTime() {
        return restTime;
    }

    public int getInterval(){
        return interval;
    }

    public String getInfo(){
        return name + " " + workTime + " " + restTime + " " + interval;
    }

    @Override
    public String toString() {
        return name;
    }
}
