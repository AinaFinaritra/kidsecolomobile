package com.example.ecomania.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ecomania.R;

public class SettingFragment extends Fragment {

    View view;
    Button logoutButton;
    Button loginButton;
    LinearLayout info_user;
    LinearLayout setting_connexion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        //init parameter
        logoutButton = view.findViewById(R.id.logoutButton);
        loginButton = view.findViewById(R.id.loginButton);
        info_user = view.findViewById(R.id.info_user);
        setting_connexion = view.findViewById(R.id.setting_connexion);

        //controlle de l'affichage
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SettingFragment.this.getContext());
        String user_id = pref.getString("user_id", null);
        if(user_id != null){
            info_user.setVisibility(View.VISIBLE);
            setting_connexion.setVisibility(View.INVISIBLE);
        }else{
            info_user.setVisibility(View.INVISIBLE);
            setting_connexion.setVisibility(View.VISIBLE);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //begin logout
                //SharedPreferences pref = view.getContext().getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SettingFragment.this.getContext());
                pref.edit().clear().commit();
                String username = pref.getString("user_id", "null");
                Log.e("(SettingFragment) logout user_id? ", username);
                //end logout
                info_user.setVisibility(View.INVISIBLE);
                setting_connexion.setVisibility(View.VISIBLE);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingFragment.this.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}