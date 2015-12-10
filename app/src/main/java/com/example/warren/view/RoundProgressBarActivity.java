package com.example.warren.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.warren.view.diy.RoundProgressBar;

public class RoundProgressBarActivity extends AppCompatActivity {
    int mProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_round_progress_bar);
        init();
    }

    private void init() {

        final RoundProgressBar rpb_progress = (RoundProgressBar) findViewById(R.id.rpb_progress);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress < 100) {
                    mProgress++;
                    rpb_progress.setCurrent(mProgress);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
        ImageButton ib_left = (ImageButton) findViewById(R.id.ib_left);
        ib_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
