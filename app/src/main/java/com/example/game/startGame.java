package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class startGame extends AppCompatActivity {

    TextView Timer;
    TextView tvPoints;
    ImageView showImage;
    HashMap<String,Integer> map = new HashMap<>();
    ArrayList<String> techList = new ArrayList<>();
    int index;
    Button btn1, btn2, btn3, btn4;
    TextView tvResult;
    int points;
    CountDownTimer countDownTimer;
    long millsUntilFinished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        Timer = findViewById(R.id.Timer);
        tvResult=findViewById(R.id.tvResult);
        showImage=findViewById(R.id.showImage);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        tvPoints=findViewById(R.id.tvPoints);
        index=0;
        techList.add("fats");
        techList.add("carbohydrates");
        techList.add("protein");
        techList.add("vitamins");
        techList.add("minerals");
        map.put(techList.get(0), R.drawable.carbs);
        map.put(techList.get(1), R.drawable.chicken);
        map.put(techList.get(2), R.drawable.chocolate);
        map.put(techList.get(3), R.drawable.fats);
        map.put(techList.get(4), R.drawable.fish);
        map.put(techList.get(5), R.drawable.lemon);
        map.put(techList.get(6), R.drawable.milk);
        map.put(techList.get(7), R.drawable.orange);
        Collections.shuffle(techList);
        millsUntilFinished=1000;
        points=0;
        startGame();
    }
    private void startGame(){
        millsUntilFinished =1000;
        Timer.setText("" + (millsUntilFinished/1000) +"s");
        tvPoints.setText(points+"/" +techList.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millsUntilFinished, 1000) {
            @Override
            public void onTick(long millsUntilFinised) {
              Timer.setText("" + (millsUntilFinised / 1000)+ "s");
            }

            @Override
            public void onFinish() {
                index++;
                if (index > techList.size() -1){
                    showImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(startGame.this, GameOver.class);
                    intent.putExtra("points", points);
                    startActivity(intent);
                    finish();
                }else{
                    startGame();
                }
            }
        }.start();
    }
    private void generateQuestions (int index){
        ArrayList<String> techListTemp =(ArrayList<String>) techList.clone();
        String correctAnswer=techList.get(index);
        techListTemp.remove(correctAnswer);
        Collections.shuffle(techListTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(techListTemp.get(0));
        newList.add(techListTemp.get(1));
        newList.add(techListTemp.get(2));
        newList.add(correctAnswer);
        Collections.shuffle(newList);
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));
        showImage.setImageResource(map.get(techList.get(index)));
    }
    public void nextQuestion (View view){
    countDownTimer.cancel();
    index++;
     if (index > techList.size()-1){
         showImage.setVisibility(View.GONE);
         btn1.setVisibility(View.GONE);
         btn2.setVisibility(View.GONE);
         btn3.setVisibility(View.GONE);
         btn4.setVisibility(View.GONE);
         Intent intent = new Intent(startGame.this,GameOver.class);
         intent.putExtra("points", points);
         startActivity(intent);
         finish();
     }else{
         startGame();
     }
    }
    public void answerSelected (View view){
    countDownTimer.cancel();
    String answer =((Button) view).getText().toString().trim();
    String correctAnswer = techList.get(index);
    if(answer.equals(correctAnswer)){
        points++;
        tvPoints.setText(points+"/" +techList.size());
        tvResult.setText("correct");
    }else{
        tvResult.setText("wrong");
    }
    }

}
