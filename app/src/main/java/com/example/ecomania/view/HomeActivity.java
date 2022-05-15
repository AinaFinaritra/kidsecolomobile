package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecomania.R;
import com.example.ecomania.controller.ThemeController;

public class HomeActivity extends AppCompatActivity {

    //propreties
    Button home_fragment_button;
    Button score_fragment_button;
    Button setting_fragment_button;
    private ThemeController themeController;

    private void init(){
        this.themeController = ThemeController.getInstance();
        this.themeController.makeThemeTest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        home_fragment_button = findViewById(R.id.btnHome);
        score_fragment_button = findViewById(R.id.btnScore);
        setting_fragment_button = findViewById(R.id.btnSettings);

        home_fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

        score_fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ScoreFragment());
            }
        });

        setting_fragment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SettingFragment());
            }
        });

        replaceFragment(new HomeFragment());

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}