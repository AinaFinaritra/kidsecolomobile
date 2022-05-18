package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecomania.R;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView total_question;
    TextView question;
    Button reponse1, reponse2, reponse3;
    Button submit;

    int score = 0;
    int total_questions = 0;

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

    }

    @Override
    public void onClick(View view) {

    }
}