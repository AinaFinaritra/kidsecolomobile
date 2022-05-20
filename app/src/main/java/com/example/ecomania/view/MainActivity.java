package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ecomania.R;

public class MainActivity extends AppCompatActivity {

    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //begin presistant login

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("user_id", "null");
        Log.e("(MainActivity) user_id? ", username);

        //simule login
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("username", "ainaaa");
        edit.putString("user_id", "756");
        edit.commit();
        String user_id = pref.getString("user_id", "null");
        Log.e("(HomeActivity) user_id? ", user_id);
        //end simule login

        //end persistant login

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}