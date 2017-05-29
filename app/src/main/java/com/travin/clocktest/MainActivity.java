package com.travin.clocktest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnTimerOne, btnTimerTwo;
    private Button btnPause, btnReset;
    private Handler handler = new Handler();
    private Runnable runnable;
    private long milisRemaining ;
    private int playerActive;
    CountDownTimer playerTimer = null;
    private boolean isPaused = false, isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTimerOne = (Button) findViewById(R.id.btnTimerOne);
        btnTimerTwo = (Button) findViewById(R.id.btnTimerTwo);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnTimerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                playerActive = 1;
                countDownPlayerStart(playerActive);
            }
        });

        btnTimerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                playerActive = 2;
                countDownPlayerStart(playerActive);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPaused){
                    btnPause.setText("Pause");
                    isPaused = false;
                    countDownPlayerStart(playerActive);
                }else{
                    playerTimer.cancel();
                    btnPause.setText("Resume");
                    isPaused = true;
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }


    private void reset(){
        milisRemaining = 180000;
        if(isRunning) playerTimer.cancel();
        btnTimerOne.setText("03:00");
        btnTimerOne.setTextColor(Color.BLACK);
        btnTimerOne.setEnabled(true);
        btnTimerTwo.setText("03:00");
        btnTimerTwo.setTextColor(Color.BLACK);
        btnTimerTwo.setEnabled(true);
    }

    private void countDownPlayerStart(final int player){
        playerTimer = new CountDownTimer(milisRemaining, 1) {

            public void onTick(long millisUntilFinished) {
                isRunning = true;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                long changeColorTimeLimit = 30000;
                int color = (millisUntilFinished <= changeColorTimeLimit)?Color.RED : Color.BLACK;

                long showMillis = 10000;
                String textMillis = (millisUntilFinished <= showMillis)?":"+String.valueOf(millisUntilFinished % 1000): "";

                Button trigger = (player == 1)?btnTimerTwo:btnTimerOne;
                Button disabled = (player == 1)?btnTimerOne:btnTimerTwo;

                disabled.setEnabled(false);

                trigger.setTextColor(color);
                trigger.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds)
                        + textMillis
                );

                milisRemaining = millisUntilFinished;
            }

            public void onFinish() {
                reset();
                isRunning = false;
            }
        };

        playerTimer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
