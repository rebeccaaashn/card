package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    TextView  tvPoints, personalBest;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        int points =getIntent().getExtras().getInt("points");
        tvPoints=findViewById(R.id.tvPoints);
        personalBest=findViewById(R.id.personalBest);
        tvPoints.setText("" +points);
        sharedPreferences= getSharedPreferences("pref",0  );
        int pointsSP =sharedPreferences.getInt("pointsSP", 0);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        if(points>pointsSP){
            pointsSP =points;
            editor.putInt("pointsSP", pointsSP);
            editor.commit();
        }
        personalBest.setText(""+pointsSP);
    }


    public void restart(View view){
    Intent intent = new Intent(GameOver.this,startGame.class);
    startActivity(intent);
    finish();
    }
    public void exit(View view){
    finish();
    }
}

