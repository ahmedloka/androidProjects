package com.gamecodeschool.gameconnect;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow , 1 = red
    int activePlayer = 0 ;
    boolean gameIsActive = true ;
    int []gameState = {2,2,2,2,2,2,2,2,2};
    int [][]winningPoitions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public void dropIn (View view) {


        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {
            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer ;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360f).setDuration(300);

            for(int [] winningPoitions : winningPoitions){
                if(gameState[winningPoitions[0]] == gameState[winningPoitions[1]] &&
                        gameState[winningPoitions[1]] == gameState[winningPoitions[2]]
                        && gameState[winningPoitions[0]] != 2){

                    Log.i("error","check" +gameState[winningPoitions[0]]);
                    //someone has won
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    linearLayout.setVisibility(View.VISIBLE);
                    TextView txt = (TextView)findViewById(R.id.winnerMessage);
                    if (gameState[winningPoitions[0]] == 0){
                        txt.setText("Yellow Won !");
                    }else {
                        txt.setText("Red Won !");
                    }
                    gameIsActive = false ;

                }else {
                    boolean gameIsOver = true;
                    for (int countrtState : gameState) {
                        if (countrtState == 2) {
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver) {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        TextView txt = (TextView) findViewById(R.id.winnerMessage);
                        linearLayout.setVisibility(View.VISIBLE);
                        txt.setText("there 's no one won ");
                    }
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void playAgain(View view) {

        activePlayer = 0;

        gameIsActive = true ;
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0 ;
        for (int i = 0; i <gameState.length ; i++) {
            gameState[i] = 2 ;

        }

        ImageView imageView1 = (ImageView)findViewById(R.id.imageView);
        ImageView imageView2 = (ImageView)findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView)findViewById(R.id.imageView3);
        ImageView imageView4 = (ImageView)findViewById(R.id.imageView4);
        ImageView imageView5 = (ImageView)findViewById(R.id.imageView5);
        ImageView imageView6 = (ImageView)findViewById(R.id.imageView6);
        ImageView imageView7 = (ImageView)findViewById(R.id.imageView7);
        ImageView imageView8 = (ImageView)findViewById(R.id.imageView8);
        ImageView imageView9 = (ImageView)findViewById(R.id.imageView9);

        imageView1.setImageResource(0);
        imageView2.setImageResource(0);
        imageView3.setImageResource(0);
        imageView4.setImageResource(0);
        imageView5.setImageResource(0);
        imageView6.setImageResource(0);
        imageView7.setImageResource(0);
        imageView8.setImageResource(0);
        imageView9.setImageResource(0);


    }

    }

