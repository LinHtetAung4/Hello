package com.example.multiplechoicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Level extends AppCompatActivity implements View.OnClickListener{
TextView leveltxt;
TextView Timetxt;
TextView txtquestionno;
TextView txtscore,txtquestion;
Button ans_A,ans_B,ans_C,ans_D,submit,next;
CountDownTimer cdTimer;

int questionIndex=1;
int num1=0;
int num2=0;
int opeNum=0;
int score;
String operator="+";
int selectedAnswer = -1;
int answer=0;
String level;
ArrayList<Integer> optionList=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        level= getIntent().getStringExtra("Level");
        leveltxt =(TextView)findViewById(R.id.idlevel0);
        leveltxt.setText("Level: " + level);
        Timetxt =(TextView)findViewById(R.id.idtimer);
        ans_A = findViewById(R.id.ans_A);
        ans_B = findViewById(R.id.ans_B);
        ans_C = findViewById(R.id.ans_C);
        ans_D = findViewById(R.id.ans_D);
        submit = findViewById(R.id.submit);
        txtquestion=findViewById(R.id.question);
        txtquestionno=findViewById(R.id.idquestionno);
        txtscore=findViewById(R.id.idscore);
        next=findViewById(R.id.next);

        ans_A.setOnClickListener(this);
        ans_B.setOnClickListener(this);
        ans_C.setOnClickListener(this);
        ans_D.setOnClickListener(this);

        next.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(questionIndex>10){
                    Toast.makeText(Level.this,"Congratulation",Toast.LENGTH_LONG).show();
                }
                else{
                    setUpQuestion();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checkAnswer();
                next.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
            }
        });
        if(level.equals("0")){
            Timetxt.setVisibility(View.INVISIBLE);
        }
        setUpQuestion();


    }
    public void Timer(String _level){
        long miliSec = 0;
        if (_level.equals("1")){
           miliSec = 21000;
        }
        else if(_level.equals("2")){
            miliSec=11000;
        }
        cdTimer=new CountDownTimer( miliSec,1000) {
            @Override
            public void onTick(long l) {
                Timetxt.setText("Timer : 00:"+l/1000);

            }
            @Override
            public void onFinish() {
                if(questionIndex<10){
                    questionIndex++;
                    setUpQuestion();
                }
                else{
                    Toast.makeText(Level.this,"Congratulations",Toast.LENGTH_LONG).show();
                }
            }
        }.start();

    }
    public void setUpQuestion(){
        submit.setVisibility(View.VISIBLE);
        next.setVisibility(View.INVISIBLE);

        txtquestionno.setText("Question: "+questionIndex+"/10");
        if (level.equals("1")) {
            Timer(level);
        }
        else if(level.equals("2")){
            Timer(level);
        }
        clearCheckedButton();
        txtscore.setText("Score: "+score);
        num1=randomNumber(12,1);
        num2=randomNumber(12,1);
        opeNum=randomNumber(4,1);
        switch (opeNum){
            case 1: answer=num1+num2;
            operator="+";
            break;
            case 2:if(num1>num2){
                answer=num1-num2;
                operator="-";
            }
            else if(num2>num1){
                Toast.makeText(Level.this,"Num1 is greater than num2",Toast.LENGTH_LONG).show();
                setUpQuestion();
            }
            break;

            case 3:answer=num1*num2;
            operator="*";
            break;

            case 4:answer=num1/num2;
            operator="/";
            break;

        }
        txtquestion.setText(String.valueOf(num1)+operator+String.valueOf(num2)+ " Will be ");
        setUpOptions();

    }
    public int randomNumber(int max, int min){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
    public void setUpOptions(){
        optionList=new ArrayList<Integer>();
        optionList.add((answer));
        for(int i=0;i<3;i++){
            int radNum= randomNumber(12,1);
            while (optionList.contains(radNum)){
                radNum=randomNumber(12,1);
            }
            optionList.add((radNum));
        }


        Collections.shuffle(optionList);

        ans_A.setText(optionList.get(0).toString());
        ans_B.setText(optionList.get(1).toString());
        ans_C.setText(optionList.get(2).toString());
        ans_D.setText(optionList.get(3).toString());


    }
    public void checkAnswer(){
     if(selectedAnswer==-1){
         Toast.makeText(Level.this, "Please Choose one",Toast.LENGTH_LONG).show();

     }
     else{
         if(answer==(selectedAnswer) && questionIndex<=10){
             score++;
             txtscore.setText("Score: "+score);

             MediaPlayer ring= MediaPlayer.create(Level.this,R.raw.correct);
             ring.start();
         }
         else{
             MediaPlayer ring= MediaPlayer.create(Level.this,R.raw.wrong);
             ring.start();
         }


         if(!(level.equals("0"))){
             cdTimer.cancel();
             cdTimer=null;
         }
         questionIndex++;
     }
    }


    @Override
    public void onClick(View view) {

        Button clickedButton = (Button) view;
        selectedAnswer = Integer.parseInt(clickedButton.getText().toString());
        clickedButton.setBackgroundColor(Color.GREEN);


    }
    public void clearCheckedButton(){
        ans_A.setBackgroundColor(Color.LTGRAY);
        ans_B.setBackgroundColor(Color.LTGRAY);
        ans_C.setBackgroundColor(Color.LTGRAY);
        ans_D.setBackgroundColor(Color.LTGRAY);
    }

}