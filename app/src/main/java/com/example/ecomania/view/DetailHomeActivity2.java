package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ecomania.R;
import com.example.ecomania.controller.ThemeController;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailHomeActivity2 extends AppCompatActivity {

    int position;

    //propreties
    private TextView lbl_titre;
    private ThemeController themeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home2);
        getSupportActionBar().hide();

        //data theme
        this.themeController = themeController.getInstance();
        ArrayList<HashMap<String, String>> theme = new ArrayList<HashMap<String,String>>();
        theme = this.themeController.getThemeTest();

        //get view
        this.lbl_titre = findViewById(R.id.lbl_titre);

        //recuperation de la position de la liste
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("position");
        }
        HashMap<String,String> chosed_theme = theme.get(position);
        String theme_text = chosed_theme.get("theme");

        //setDuText lbl_titre
        this.lbl_titre.setText(theme_text);

    }
}