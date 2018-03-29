package com.example.michael.pomodorotimerv2;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class TimerActivity extends Activity {

    private static final String TAG = "TimerActivity";
    private String profileName;

    private TextView mWorkCountDownText;
    private Button mStartButton;
    private TextView mRestCountDownText;
    private ImageButton mExitButton;

    private TextView mIntervalText;
    private int interval;

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

        mExitButton = (ImageButton) findViewById(R.id.exit_button);

        interval = Integer.parseInt(i.getStringExtra("intervalAmount"));
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
                Log.d(TAG, "Exit Button Clicked");
                Intent i = new Intent(TimerActivity.this, ProfileActivity.class);
                TimerActivity.this.startActivity(i);
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
        mIntervalText.setText(""+interval);
        mStartButton.setText("Restart");
        workIsFinished = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
