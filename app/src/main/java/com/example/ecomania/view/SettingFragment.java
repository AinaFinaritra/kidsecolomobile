package com.example.ecomania.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ecomania.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingFragment extends Fragment {

    View view;
    Button logoutButton;
    Button loginButton;
    LinearLayout info_user;
    LinearLayout setting_connexion;
    Spinner spn_niveau;
    ArrayList<HashMap<String, String>> list_niveau;
    ArrayAdapter<String> arrayAdapter_list_niveau;
    TextView niveau_selected;
    WebView aPropos;
    int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        //init parameter
        logoutButton = view.findViewById(R.id.logoutButton);
        loginButton = view.findViewById(R.id.loginButton);
        info_user = view.findViewById(R.id.info_user);
        setting_connexion = view.findViewById(R.id.setting_connexion);
        spn_niveau = view.findViewById(R.id.spn_niveau);
        niveau_selected = view.findViewById(R.id.niveau_selected);
        aPropos = view.findViewById(R.id.aPropos);

        //a propos en html
        WebSettings webSettings = aPropos.getSettings();
        webSettings.setJavaScriptEnabled(true);
        aPropos.setWebViewClient(new Callback());
        aPropos.loadUrl("https://www.futura-sciences.com/planete/definitions/developpement-durable-ecologie-133/");

        //controlle de l'affichage de la connexion
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SettingFragment.this.getContext());

        String user_id = pref.getString("user_id", null);
        if(user_id != null){
            info_user.setVisibility(View.VISIBLE);
            setting_connexion.setVisibility(View.INVISIBLE);
        }else{
            info_user.setVisibility(View.INVISIBLE);
            setting_connexion.setVisibility(View.VISIBLE);
        }
        String libelle_niveau = pref.getString("libelleNiveau", null);
        if(libelle_niveau != null){
            niveau_selected.setText(libelle_niveau);
        }else{
            niveau_selected.setText("Aucun niveau choisi");
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

        //changement de niveau
        list_niveau = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> niveau1 = new HashMap<String, String>();
        niveau1.put("id_niveau", "1");
        niveau1.put("libelle", "oeufs");
        list_niveau.add(niveau1);
        HashMap<String, String> niveau2 = new HashMap<String, String>();
        niveau2.put("id_niveau", "2");
        niveau2.put("libelle", "poussin");
        list_niveau.add(niveau2);
        HashMap<String, String> niveau3 = new HashMap<String, String>();
        niveau3.put("id_niveau", "3");
        niveau3.put("libelle", "coque");
        list_niveau.add(niveau3);

        //adapter la liste sur le front
        ArrayList<String> label = niveauTolabel(list_niveau);
        arrayAdapter_list_niveau = new ArrayAdapter<>(SettingFragment.this.getContext().getApplicationContext(),
                android.R.layout.simple_spinner_item, label);
        spn_niveau.setAdapter(arrayAdapter_list_niveau);

        //evenement de choix niveau
        spn_niveau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++check > 1){
                    HashMap<String, String> chosed = list_niveau.get(i);
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SettingFragment.this.getContext());
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("libelleNiveau", chosed.get("libelle"));
                    edit.putString("idNiveau", chosed.get("id_niveau"));
                    edit.commit();
                    niveau_selected.setText(chosed.get("libelle"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private ArrayList<String> niveauTolabel(ArrayList<HashMap<String, String>> WSResult){
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0; i<WSResult.size(); i++){
            String tmp = WSResult.get(i).get("libelle");
            result.add(tmp);
        }
        return result;
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}