package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int userTotalScore = 0;
    private int turnTotalScore = 0;
    private boolean userTurn = true;
    private int compTotalScore = 0;

    private Button roll;
    private Button hold;
    private Button reset;
    private TextView userScore;
    private TextView compScore;
    private TextView turn;
    private ImageView diePic;



    private Random rand = new Random();
    private Map<Integer, String> diceImages = new HashMap<Integer, String>() {{
        put(1, "dice1.png");
        put(2, "dice2.png");
        put(3, "dice3.png");
        put(4, "dice4.png");
        put(5, "dice5.png");
        put(6, "dice6.png");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = (Button)findViewById(R.id.button);
        hold = (Button) findViewById(R.id.button2);
        reset = (Button) findViewById(R.id.button3);
        userScore = (TextView) findViewById(R.id.textView);
        compScore = (TextView) findViewById(R.id.textView2);
        turn = (TextView) findViewById(R.id.textView3);
        diePic = (ImageView) findViewById(R.id.imageView);
    }


    public void rollDice(View v) {
        if (end()) return;

        int diceFace = rand.nextInt(6);
        int [] arr = {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
        diePic.setImageDrawable(getResources().getDrawable(arr[diceFace]));
        turnTotalScore += diceFace+1;

        if (diceFace == 0){
            turnTotalScore = 0;
            turn.setText("Turn Score: " + Integer.toString(turnTotalScore));
            holdTurn(v);
        }

        turn.setText("Turn Score: " + Integer.toString(turnTotalScore));

    }

    public void holdTurn(View v) {
        userTurn = false;
        userTotalScore += turnTotalScore;
        userScore.setText("Your Score: " + Integer.toString(userTotalScore));
        turn.setText("Turn Score: " + Integer.toString(0));
        turnTotalScore = 0;

        roll.setEnabled(false);
        hold.setEnabled(false);

        if (end()) return;

        computerTurn();
    }

    public void resetTurn(View v) {
        userTotalScore = 0;
        turnTotalScore = 0;
        userTurn = true;
        compTotalScore = 0;

        userScore.setText("Your Score: " + Integer.toString(0));
        compScore.setText("AI Score: " + Integer.toString(0));
        turn.setText("Turn Score: " + Integer.toString(0));

        roll.setEnabled(true);
        hold.setEnabled(true);
    }

    public void computerTurn(){
        Log.d("123", String.valueOf(turnTotalScore));
        int [] arr = {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};

        Handler h = new Handler();

        while (turnTotalScore <= 20){
            Log.d("la", String.valueOf(turnTotalScore));

            // Rolling the dice
            int diceFace = rand.nextInt(6);
            diePic.setImageDrawable(getResources().getDrawable(arr[diceFace]));
            turnTotalScore += diceFace+1;

            //edge case
            if (diceFace == 0){
                turnTotalScore = 0;
                break;
            }

            //updating
            turn.setText("Turn Score: " + Integer.toString(turnTotalScore));

            //h.postDelayed((Runnable) this, 500);

        }

        userTurn = true;
        compTotalScore += turnTotalScore;
        compScore.setText("AI Score: " + Integer.toString(compTotalScore));
        turn.setText("Turn Score: " + Integer.toString(0));
        turnTotalScore = 0;

        roll.setEnabled(true);
        hold.setEnabled(true);

        if (end()) return;
    }

    public boolean end(){
        if (userTotalScore >= 100){
            userScore.setText("You Win!");
            compScore.setText("AI Loses!");
            turn.setText("" );
            roll.setEnabled(false);
            hold.setEnabled(false);
            return true;
        }
        else if (compTotalScore >= 100){
            userScore.setText("You Lose!");
            compScore.setText("AI Wins!");
            turn.setText("" );
            roll.setEnabled(false);
            hold.setEnabled(false);
            return true;
        }

        return false;


    }




}
