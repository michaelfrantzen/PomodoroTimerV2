package com.example.michael.pomodorotimerv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";
    private ImageButton mExitButton;
    private ImageView mDaisy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        Log.d(TAG, "onCreate() called");

        mDaisy = (ImageView)findViewById(R.id.daisy);

        mExitButton = (ImageButton)findViewById(R.id.about_exit_button);

        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this, ProfileActivity.class);
                AboutActivity.this.startActivity(i);
            }
        });
    }
}
