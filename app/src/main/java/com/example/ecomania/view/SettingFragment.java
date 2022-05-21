package com.example.ecomania.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomania.R;
import com.example.ecomania.utils.Constante;
import com.example.ecomania.utils.LoadingDialogue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String url = Constante.url+"/joueur/niveau";

    TextView nomJoueur;
    TextView prenomJoueur;
    TextView pseudoJoueur;

    LoadingDialogue loadingDialogue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        //loading
        loadingDialogue = new LoadingDialogue(this.getActivity());
        loadingDialogue.startLoadingDialog();

        //init parameter
        init();
        //a propos en html
        loadAproposWeb("https://www.futura-sciences.com/planete/definitions/developpement-durable-ecologie-133/");
        //loadAproposWeb(Constante.url+"/about");
        //controlle de l'affichage de la connexion
        gererAffichage();

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

        //RESTAPI get niveau
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("error restapi get", response.toString());

                        list_niveau = new ArrayList<HashMap<String,String>>();
                        //avoir la liste
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject item = new JSONObject();
                            for (int i=0; i < jsonArray.length(); i++){
                                item = jsonArray.getJSONObject(i);
                                HashMap<String, String> niveau = new HashMap<String, String>();
                                niveau.put("id_niveau", item.getString("id"));
                                niveau.put("libelle", item.getString("valeur"));
                                list_niveau.add(niveau);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //adapter la liste sur le front
                        ArrayList<String> label = niveauTolabel(list_niveau);
                        arrayAdapter_list_niveau = new ArrayAdapter<>(SettingFragment.this.getContext().getApplicationContext(),
                                android.R.layout.simple_spinner_item, label);
                        spn_niveau.setAdapter(arrayAdapter_list_niveau);

                        loadingDialogue.dismissLoadingDialog();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error restapi get", error.toString());
                        loadingDialogue.dismissLoadingDialog();
                    }
                }
        );
        requestQueue.add(objectRequest);

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

    private void init(){
        logoutButton = view.findViewById(R.id.logoutButton);
        loginButton = view.findViewById(R.id.loginButton);
        info_user = view.findViewById(R.id.info_user);
        setting_connexion = view.findViewById(R.id.setting_connexion);
        spn_niveau = view.findViewById(R.id.spn_niveau);
        niveau_selected = view.findViewById(R.id.niveau_selected);
        aPropos = view.findViewById(R.id.aPropos);
        pseudoJoueur = view.findViewById(R.id.pseudo);
        prenomJoueur = view.findViewById(R.id.prenom);
        nomJoueur = view.findViewById(R.id.nom);
    }

    private void loadAproposWeb(String link){
        WebSettings webSettings = aPropos.getSettings();
        webSettings.setJavaScriptEnabled(true);
        aPropos.setWebViewClient(new Callback());
        aPropos.loadUrl(link);
    }

    private void gererAffichage(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SettingFragment.this.getContext());
        String user_id = pref.getString("user_id", null);
        if(user_id != null){
            info_user.setVisibility(View.VISIBLE);
            setting_connexion.setVisibility(View.INVISIBLE);
            nomJoueur.setText(pref.getString("nom", "Votre nom s'affichera ici une fois connecté"));
            prenomJoueur.setText(pref.getString("prenom", "Votre prenom s'affichera ici une fois connecté"));
            pseudoJoueur.setText(pref.getString("pseudo", "Votre pseudo s'affichera ici une fois connecté"));
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
    }
}