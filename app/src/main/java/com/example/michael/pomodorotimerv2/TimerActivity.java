package com.example.michael.pomodorotimerv2;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class TimerActivity extends Activity {

    private static final String TAG = "TimerActivity";
    private String profileName;
    private ProfileDir profileDir;

    private TextView mWorkCountDownText;
    private Button mStartButton;
    private TextView mRestCountDownText;
    private ImageButton mExitButton;
    private ImageButton mResetButton;
    private TextView mProfileName;

    private TextView mIntervalText;
    private int interval;
    private int start_interval;

    private boolean workTimerRunning;
    private CountDownTimer workCountDownTimer;
    private long workTimeLeftInMilliSeconds;
    private float workTimeAmount;
    private boolean workIsFinished;

    private boolean restTimerRunning;
    private CountDownTimer restCountDownTimer;
    private long restTimeLeftInMilliSeconds;
    private float restTimeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_timer);
        Intent i = getIntent();
        profileName = i.getStringExtra("profileName");

        mProfileName = (TextView)findViewById(R.id.profile_title);
        mProfileName.setText(profileName);

        mExitButton = (ImageButton) findViewById(R.id.exit_button);
        mResetButton = (ImageButton)findViewById(R.id.reset_button);


        interval = Integer.parseInt(i.getStringExtra("intervalAmount"));
        start_interval = interval;
        mIntervalText = (TextView) findViewById(R.id.interval_countdown);
        mIntervalText.setText(""+interval);

        workTimeAmount = Float.parseFloat(i.getStringExtra("timeAmount"));
        workTimeLeftInMilliSeconds = (long) (workTimeAmount * 60000);
        mWorkCountDownText = (TextView)findViewById(R.id.work_countdown);
        workIsFinished = false;

        mRestCountDownText = (TextView)findViewById(R.id.rest_countdown);
        restTimeAmount = Float.parseFloat(i.getStringExtra("restAmount"));
        restTimeLeftInMilliSeconds = (long) (restTimeAmount * 60000);


        mStartButton = (Button)findViewById(R.id.start_button);

        workUpdateTimer();
        restUpdateTimer();


        mStartButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Start button clicked");
                startStop();
            }
        });

        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning() == true){
                    startStop();
                }
                Log.d(TAG, "Exit Button Clicked");
                Intent i = new Intent(TimerActivity.this, ProfileActivity.class);
                TimerActivity.this.startActivity(i);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning() == true){
                    startStop();
                }
                workIsFinished = false;
                workResetTimer();
                restResetTimer();
                workUpdateTimer();
                restUpdateTimer();
            }
        });


    }

    public void startStop(){
        Log.d(TAG, "startStop() called");
        if(workIsFinished){
            if (restTimerRunning){
                restStopTimer();
            } else {
                restStartTimer();
            }
        } else {
            if(workTimerRunning){
                workStopTimer();
            }else{
                workStartTimer();
            }
        }
    }

    public void workStopTimer(){
        Log.d(TAG, "workStopTimer() called");
        workCountDownTimer.cancel();
        workTimerRunning = false;
        mStartButton.setText("Start");
    }

    public void workStartTimer(){
        Log.d(TAG, "workStartTimer() called");
        workCountDownTimer = new CountDownTimer(this.workTimeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                workTimeLeftInMilliSeconds = millisUntilFinished;
                workUpdateTimer();
            }

            @Override
            public void onFinish() {
                Toast.makeText(TimerActivity.this, R.string.interval_complete, Toast.LENGTH_SHORT).show();
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.beep);
                mp.start();
                workIsFinished = true;
                mStartButton.setText("Start");
                workTimerRunning = false;
            }
        }.start();
        workTimerRunning = true;
        mStartButton.setText("PAUSE");
    }

    public void workUpdateTimer(){
        Log.d(TAG, "workUpdateTimer() called");
        int minutes = (int) workTimeLeftInMilliSeconds/60000;
        int seconds = (int) workTimeLeftInMilliSeconds%60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        mWorkCountDownText.setText(timeLeftText);
    }

    public void workResetTimer(){
        Log.d(TAG, "workResetTimer() called");
        workTimeLeftInMilliSeconds = (long) (workTimeAmount * 60000);
    }


    public void restStopTimer(){
        Log.d(TAG, "restStopTimer() called");
        restCountDownTimer.cancel();
        restTimerRunning = false;
        mStartButton.setText("Start");
    }

    public void restStartTimer(){
        Log.d(TAG, "restStartTimer() called");
        restCountDownTimer = new CountDownTimer(this.restTimeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                restTimeLeftInMilliSeconds = millisUntilFinished;
                restUpdateTimer();
            }

            @Override
            public void onFinish() {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.beep);
                mp.start();
                decrementInterval();
                workResetTimer();
                workUpdateTimer();
                restResetTimer();
                restUpdateTimer();
            }
        }.start();
        restTimerRunning = true;
        mStartButton.setText("PAUSE");
    }

    public void restUpdateTimer(){
        Log.d(TAG, "restUpdateTimer() called");
        int minutes = (int) (restTimeLeftInMilliSeconds/60000);
        int seconds = (int) (restTimeLeftInMilliSeconds%60000 / 1000);

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        mRestCountDownText.setText(timeLeftText);
    }

    public void restResetTimer(){
        Log.d(TAG, "restResetTimer() called");
        restTimeLeftInMilliSeconds = (long) (restTimeAmount * 60000);
    }

    public void decrementInterval(){
        Log.d(TAG, "decrementInterval() called");
        interval--;
        if (interval != 0){
            Toast.makeText(TimerActivity.this, R.string.interval_complete, Toast.LENGTH_SHORT).show();
        }
        if (interval == 0){
            Toast.makeText(TimerActivity.this,R.string.timer_complete,Toast.LENGTH_LONG).show();
            interval = start_interval;
        }
        mIntervalText.setText(""+interval);
        mStartButton.setText("Restart");
        workIsFinished = false;
        restTimerRunning = false;
    }

    public boolean isTimerRunning(){
        if (workTimerRunning == true){
            return true;
        } else if (restTimerRunning == true){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
