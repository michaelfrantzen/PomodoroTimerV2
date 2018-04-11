package com.example.michael.pomodorotimerv2;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    ArrayList<Profile> profiles = null;
    private ProfileDir profileDir;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        profileDir = new ProfileDir(this.getApplicationContext());
        profiles = profileDir.getmProfiles();
        super.onCreate(savedInstanceState);

        if(profiles.size() == 0){

            Log.d(TAG, "noprofile layout set");
            setContentView(R.layout.empty_profile);
            Button mStartButton = (Button) findViewById(R.id.empty_start);
            mStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ProfileActivity.this, BeginActivity.class);
                    ProfileActivity.this.startActivity(i);
                }
            });
        } else {
            setContentView(R.layout.activity_profile);
            Log.d(TAG, "Activity_profile layout set");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            final MyCustomerAdapter adapter = new MyCustomerAdapter(profiles, this, profileDir);
            final ListView listView = (ListView)findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, " Floating Action Bar CLicked");
                    Intent i = new Intent(ProfileActivity.this, BeginActivity.class);
                    ProfileActivity.this.startActivity(i);
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            setContentView(R.layout.about);
        }

        return super.onOptionsItemSelected(item);
    }



}
