package com.gamecodeschool.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView ;
    Button mBtnStart ;
    Button mBtnStop ;
    SeekBar mSeekBar ;
    CountDownTimer timer ;


    public void start(final View view){

         timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(final long millisUntilFinished) {

                    mSeekBar = (SeekBar)findViewById(R.id.seekBar);
                    int maxCounter = (int) millisUntilFinished/1000 ;
                    mSeekBar.setMax(maxCounter);
                    mSeekBar.setProgress(maxCounter);
                    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            mTextView = (TextView) findViewById(R.id.textView);
                            mTextView.setText("00" + " : " + Long.toString(millisUntilFinished / 1000));
                            mBtnStart = (Button) findViewById(R.id.btnStart);
                            mBtnStart.setVisibility(View.INVISIBLE);
                            mBtnStop = (Button) findViewById(R.id.btnStop);
                            mBtnStop.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
            }

            @Override
            public void onFinish() {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                mediaPlayer.start();
                mBtnStop.setVisibility(View.INVISIBLE);
                mBtnStart.setVisibility(View.VISIBLE);

               mTextView.setText("");
            }
        }.start();
    }
    public void stop (View view){


        timer.cancel();
        mBtnStop.setVisibility(View.INVISIBLE);
        mBtnStart.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
