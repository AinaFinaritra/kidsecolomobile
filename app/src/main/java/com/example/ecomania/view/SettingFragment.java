package com.example.ecomania.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecomania.R;

public class SettingFragment extends Fragment {

    View view;
    Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        //init parameter
        logoutButton = view.findViewById(R.id.logoutButton);

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
            }
        });


        return view;
    }
}