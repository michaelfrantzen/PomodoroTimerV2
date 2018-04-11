package com.example.michael.pomodorotimerv2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;



public class ProfileIntentJSONSerializer extends Object {

    private Context mContext;
    private String mFileName;

    public ProfileIntentJSONSerializer(Context mContext, String mFileName) {
        this.mContext = mContext;
        this.mFileName = mFileName;
    }

    public ArrayList<Profile> loadProfiles() throws IOException, JSONException{
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        BufferedReader reader = null;
        try{
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++){
                profiles.add(new Profile(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){

        } finally {
            if (reader != null){
                reader.close();
            }
        }
        return profiles;
    }

    public void saveProfiles(ArrayList<Profile> profiles)
    throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for (Profile p : profiles){
            array.put(p.toJSON());
        }

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    public void deleteProfile(ArrayList<Profile> profiles, int positon)
    throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for (Profile p : profiles){
            array.put(p.toJSON());
        }
        array.remove(positon);
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }
}

