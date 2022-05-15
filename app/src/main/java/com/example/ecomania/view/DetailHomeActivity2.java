package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ecomania.R;
import com.example.ecomania.adapter.DetailThemeItemAdapter;
import com.example.ecomania.controller.DetailsThemeController;
import com.example.ecomania.controller.ThemeController;
import com.example.ecomania.model.DetailsTheme;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailHomeActivity2 extends AppCompatActivity {

    int position;

    //propreties
    private TextView lbl_titre;
    private ThemeController themeController;
    private ArrayList<HashMap<String, String>> theme;
    private HashMap<String,String> chosed_theme;
    private DetailsThemeController detailsThemeController;
    private ArrayList<DetailsTheme> detailsList;
    private RecyclerView rcl_detail_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home2);
        getSupportActionBar().hide();

        //data theme
        this.themeController = themeController.getInstance();
        theme = this.themeController.getThemeTest();

        //get view
        this.rcl_detail_theme = findViewById(R.id.rcl_detail_theme);
        this.lbl_titre = findViewById(R.id.lbl_titre);

        //recuperation de la position de la liste
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("position");
        }
        chosed_theme = theme.get(position); //objet recuperer
        String theme_text = chosed_theme.get("theme");

        //setDuText lbl_titre
        this.lbl_titre.setText(theme_text);

        //initialization liste to item
        detailsThemeController = detailsThemeController.getInstance();
        detailsList = new ArrayList<DetailsTheme>();
        detailsList = detailsThemeController.getDetail_themes();

        //adapter recycler view
        DetailThemeItemAdapter adapter = new DetailThemeItemAdapter(detailsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcl_detail_theme.setLayoutManager(layoutManager);
        rcl_detail_theme.setItemAnimator(new DefaultItemAnimator());
        rcl_detail_theme.setAdapter(adapter);


    }
}