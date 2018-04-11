package com.example.michael.pomodorotimerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BeginActivity extends AppCompatActivity {

    private static final String TAG = "BeginActivity";

    private Button mBeginButton;
    private EditText mTimeAmount;
    private  EditText mRestAmount;
    private  EditText mIntervalAmount;
    private EditText mProfileName;

    private Profile currentProfile;
    private ProfileDir profileDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_begin);

        profileDir = new ProfileDir(this.getApplicationContext());

        mProfileName = (EditText)findViewById(R.id.profile_name);

        mTimeAmount = (EditText) findViewById(R.id.time_amount);

        mRestAmount = (EditText) findViewById(R.id.rest_amount);

        mIntervalAmount = (EditText) findViewById(R.id.interval_amount);

        mBeginButton = (Button)findViewById(R.id.begin_button);
        mBeginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(BeginActivity.this, "One or more fields is empty", Toast.LENGTH_SHORT);
                if (mProfileName.getText().toString().isEmpty()|
                        mTimeAmount.getText().toString().isEmpty()|
                        mRestAmount.getText().toString().isEmpty()|
                        mIntervalAmount.getText().toString().isEmpty()) {
                    toast.show();
                } else {
                    Log.d(TAG, "begin button pressed");
                    currentProfile = new Profile(mProfileName.getText().toString(), mTimeAmount.getText().toString(),
                            mRestAmount.getText().toString(), mIntervalAmount.getText().toString());
                    profileDir.addProfile(currentProfile);
                    profileDir.saveProfiles();
                    Intent i = new Intent(BeginActivity.this, TimerActivity.class);
                    i.putExtra("profileName", mProfileName.getText().toString());
                    i.putExtra("timeAmount", mTimeAmount.getText().toString());
                    i.putExtra("restAmount", mRestAmount.getText().toString());
                    i.putExtra("intervalAmount", mIntervalAmount.getText().toString());
                    BeginActivity.this.startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }



}
