package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecomania.R;
import com.example.ecomania.model.QuestionResponse;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView total_question;
    TextView question;
    Button reponse1, reponse2, reponse3;
    Button submit;

    int score = 0;
    int total_questions = QuestionResponse.question.length;
    int current_question_index = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();

        //initialise
        total_question = findViewById(R.id.total_question);
        question = findViewById(R.id.question);
        reponse1 = findViewById(R.id.reponse1);
        reponse2 = findViewById(R.id.reponse2);
        reponse3 = findViewById(R.id.reponse3);
        submit = findViewById(R.id.submit);

        //setting click listner
        reponse1.setOnClickListener(this);
        reponse2.setOnClickListener(this);
        reponse3.setOnClickListener(this);
        submit.setOnClickListener(this);

        total_question.setText("Total questions :"+total_questions);

        //initialisation de la premier question
        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        reponse1.setBackgroundColor(Color.WHITE);
        reponse2.setBackgroundColor(Color.WHITE);
        reponse3.setBackgroundColor(Color.WHITE);

        Button clicked = (Button) view;

        if(clicked.getId() == R.id.submit){
            if(selectedAnswer.equals(QuestionResponse.correctAnswers[current_question_index])){
                score ++;
            }
            current_question_index++;
            loadNewQuestion();
        }else{
            //choices
            selectedAnswer = clicked.getText().toString();
            clicked.setBackgroundColor(Color.MAGENTA);
        }
    }

    void loadNewQuestion(){

        if(current_question_index == total_questions){
            new AlertDialog.Builder(this)
                    .setTitle("Ton score")
                    .setMessage("tu as "+score+" points sur "+total_questions)
                    .setPositiveButton("Refaire le quiz", ((dialogInterface, i) -> restartQuiz()))
                    .setNeutralButton("revenir au menu principal", ((dialogInterface, i) -> quitQuiz()))
                    .setCancelable(false)
                    .show();
            return;
        }else{
            //continue question
            question.setText(QuestionResponse.question[current_question_index]);
            reponse1.setText(QuestionResponse.choices[current_question_index][0]);
            reponse2.setText(QuestionResponse.choices[current_question_index][1]);
            reponse3.setText(QuestionResponse.choices[current_question_index][2]);
        }
    }

    void restartQuiz(){
        score=0;
        current_question_index=0;
        loadNewQuestion();
    }

    void quitQuiz(){
        Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}