package com.johir.diceout.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Variable to hold score
    int score;

    TextView rollResult;
    Random rnd;
    int die1;
    int die2;
    int die3;
    ArrayList<Integer> die;
    ArrayList<ImageView> dieImages;
    TextView scoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        // Set initial score
        score = 0;
        Toast.makeText(getApplicationContext(),"Welcome to DiceOut Game",Toast.LENGTH_SHORT).show();
        rollResult  = (TextView)findViewById(R.id.rollResult);
        scoreText = (TextView) findViewById(R.id.score);
        rnd = new Random();
        die = new ArrayList<>();
        dieImages =new ArrayList<>();
        ImageView die1Image = (ImageView) findViewById(R.id.die1);
        ImageView die2Image = (ImageView) findViewById(R.id.die2);
        ImageView die3Image = (ImageView) findViewById(R.id.die3);
        dieImages.add(die1Image);
        dieImages.add(die2Image);
        dieImages.add(die3Image);

    }

    public void rollDice(View v){
        die1 = rnd.nextInt(6)+1;
        die2 = rnd.nextInt(6)+1;
        die3 = rnd.nextInt(6)+1;
        die.clear();
        die.add(die1);
        die.add(die2);
        die.add(die3);
        for(int img =0;img<3;img++){
            int value = die.get(img);
            String file = "die_"+value+".png";
            try {
                InputStream stream = getAssets().open(file);
                Drawable d = Drawable.createFromStream(stream,null);
                dieImages.get(img).setImageDrawable(d);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        String msg = "You rolled "+die1+", "+die2+" and "+die3;

        if(die1==die2 && die1==die3){
            int scoreDelta= 3*die1;
            score+=scoreDelta;
            msg = "You scored a triple "+die1+". Your score is: "+score+" points!";
        }
        else if(die1==die2 || die1==die3 || die2==die3){
            msg = "You scored double for 50 points!";
            score+=50;
        }
        else
        {
            msg = "You scored nothing in this roll. Try again";
        }
        scoreText.setText("Score: "+score);
        rollResult.setText(msg);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
