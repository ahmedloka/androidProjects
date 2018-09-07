package com.gamecodeschool.braintrainer;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    Boolean mGameActive = true ;

    Button mResult1;
    Button mResult2;
    Button mResult3;
    Button mResult4;
    Button mPlay;


    TextView mResult;
    TextView mProplem;
    TextView mtxtScore;
    TextView mTimer;

    int mScore = 1;
    int mTry = 1;

    int mLocationOfCorrectAnswers ;

    CountDownTimer mCountDownTimer;

    ConstraintLayout mConstraintLayout;

    int mX ;
    int mY ;
    ArrayList<Integer>mR = new ArrayList<Integer>();
    int mWR ;

    public void btnResult(View view) {


        mTry ++ ;
        if (view.getTag().toString().equals(Integer.toString(mLocationOfCorrectAnswers))){
            mScore ++ ;
            mResult.setText("Correct");
            mResult.setBackgroundColor(Color.GREEN);

        }else {
            mResult.setText("Wrong !");
            mResult.setBackgroundColor(Color.RED);
        }
        mtxtScore.setText(Integer.toString( mScore)+" / "+Integer.toString( mTry));

        if (mResult.getText().equals(String.valueOf("Correct"))){
            playNewGame();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mResult1 = (Button) findViewById(R.id.btnResult1);
        mResult2 = (Button) findViewById(R.id.btnResult2);
        mResult3 = (Button) findViewById(R.id.btnResult3);
        mResult4 = (Button) findViewById(R.id.btnResult4);
        mPlay = (Button) findViewById(R.id.btnPlay);

        mConstraintLayout = (ConstraintLayout)findViewById(R.id.constraintVisibility);

        mResult = (TextView) findViewById(R.id.txtResult);
        mProplem = (TextView) findViewById(R.id.txtPro);
        mtxtScore = (TextView) findViewById(R.id.txtScore);


    }

    public void btnPlay(View view) {


            playNewGame();

            mCountDownTimer = new CountDownTimer(31000, 1000 + 200) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimer = (TextView) findViewById(R.id.txtTimer);
                    mTimer.setText(Long.toString(millisUntilFinished / 1000) + " s");
                }


                @Override
                public void onFinish() {
                    mTimer.setText("0");
                    mPlay.setVisibility(View.VISIBLE);
                    mPlay.setText("Play Again");
                    mResult.setText("Your Score : " + mtxtScore.getText().toString());
                    mResult.setBackgroundColor(Color.YELLOW);
                    mtxtScore.setText("1 / 1");
                    mScore = 1 ;
                    mTry = 1 ;

                    mConstraintLayout.setVisibility(View.INVISIBLE);
                }
            }.start();
        mResult.setText("");
        mConstraintLayout.setVisibility(View.VISIBLE);

    }
        public void playNewGame(){

            mPlay.setVisibility(View.INVISIBLE);


            Random random = new Random();
            mX = random.nextInt(80);
            mY = random.nextInt(80);

            mProplem.setText(String.valueOf( mX + " + " + mY));

            mLocationOfCorrectAnswers = random.nextInt(4);

            for (int i = 0; i <4; i++) {
                if (i == mLocationOfCorrectAnswers){
                    mR.add(mX + mY) ;
                }else {
                    mWR = random.nextInt(140);
                    while(mWR == mX + mY){

                        mWR = random.nextInt(140);
                    }
                    mR.add(mWR);
                }

            }
            mResult1.setText(Integer.toString(mR.get(0)));
            mResult2.setText(Integer.toString(mR.get(1)));
            mResult3.setText(Integer.toString(mR.get(2)));
            mResult4.setText(Integer.toString(mR.get(3)));

            mR.clear();

        }
    }

