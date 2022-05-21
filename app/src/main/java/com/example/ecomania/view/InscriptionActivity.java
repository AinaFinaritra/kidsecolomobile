package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecomania.R;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        getSupportActionBar().hide();
    }
}