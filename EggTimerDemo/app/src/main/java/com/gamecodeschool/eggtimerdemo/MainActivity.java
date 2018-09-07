package com.gamecodeschool.eggtimerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeSeekBar ;
    TextView timerTextView ;
    Boolean conterIsActive = false;
    Button btnControlTime ;
    CountDownTimer timer ;
    public void updateTimer (int secondLeft){
        int minutes = secondLeft / 60 ;
        //if progress = 67 { 67/60 = 1.116 -- hna5od L rakn l sa7i7 3shan dh int yb2a l min = 67 / 60 = 1 }
        int seconds = secondLeft - minutes*60 ;
        // w hykon 67 - 1*60 = 7
        Log.i("i am here",""+secondLeft);
        String firstString = Integer.toString(minutes).toString() ;
        String secondString = Integer.toString(seconds).toString() ;


        if (seconds <= 9){
            secondString = "0"+secondString ;
        }
        timerTextView.setText( firstString+ " : "+secondString );
    }
    public void resetTimer(){
        timerTextView.setText("00:30");
        timeSeekBar.setProgress(30);
        timer.cancel();
        btnControlTime.setText("Go !");
        timeSeekBar.setEnabled(true);
        conterIsActive = false ;
    }
    public void controlTime (View view) {

        if (conterIsActive == false) {

            conterIsActive = true;
            timeSeekBar.setEnabled(false);
            btnControlTime.setText("reset ");
            timer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        }else {
                resetTimer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnControlTime = (Button)findViewById(R.id.button);
        timeSeekBar = (SeekBar)findViewById(R.id.seekBar);
        timerTextView = (TextView)findViewById(R.id.textView);
        timeSeekBar.setMax(30);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
                }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
