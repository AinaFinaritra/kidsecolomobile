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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ecomania.R;
import com.example.ecomania.controller.ScoreparthemeController;
import com.example.ecomania.controller.ThemeController;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreFragment extends Fragment {

    View view;
    ListView lst_score;
    private ScoreparthemeController scoreparthemeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //controle de l'existance de persistance
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ScoreFragment.this.getContext());
        String user_id = pref.getString("user_id", null);
        if(user_id == null){
            Intent intent = new Intent(ScoreFragment.this.getContext(), LoginActivity.class);
            startActivity(intent);
        }

        this.scoreparthemeController = scoreparthemeController.getInstance();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score, container, false);
        lst_score = (ListView) view.findViewById(R.id.lst_score);

        //static data
        ArrayList<HashMap<String, String>> score = new ArrayList<HashMap<String,String>>();
        score = this.scoreparthemeController.getScorepartheme();

        //affichage de la liste
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), score, R.layout.score_item,
                new String[]{"valeur", "pts"},
                new int[]{R.id.lbl_theme, R.id.lbl_points}
        );
        lst_score.setAdapter(adapter);

        return view;
    }
}