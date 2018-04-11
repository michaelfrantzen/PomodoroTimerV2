package com.example.michael.pomodorotimerv2;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Michael on 3/28/2018.
 */

public class ProfileDir{
    private static final String TAG = "ProfileDir";
    private static final String FILENAME = "profiles.json";

    private ArrayList<Profile> mProfiles;
    private ProfileIntentJSONSerializer mSerializer;

    private static ProfileDir sProfileDir;
    private Context mAppContext;

    public ProfileDir(Context mAppContext) {
        this.mAppContext = mAppContext;
        mSerializer = new ProfileIntentJSONSerializer(mAppContext, FILENAME);

        try {
            mProfiles  = mSerializer.loadProfiles();
        } catch (Exception e){
            mProfiles = new ArrayList<Profile>();
            Log.e(TAG, "Error loading Profiles: ", e);
        }
    }

    public void addProfile(Profile p){
        mProfiles.add(p);
    }

    public ArrayList<Profile> getmProfiles() {
        return mProfiles;
    }

    public boolean saveProfiles(){
        try {
            mSerializer.saveProfiles(mProfiles);
            Log.d(TAG, "profiles saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving Profiles: ", e);
            return false;
        }
    }

    public void deleteProfile(int postiton){
        try {
            mSerializer.deleteProfile(mProfiles, postiton);
        }catch (Exception e){
            Log.e(TAG, "Error saving Profiles: ", e);
        }
    }


}
