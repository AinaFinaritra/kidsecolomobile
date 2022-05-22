package com.example.ecomania.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class ScoreFragment extends Fragment {

    View view;
    ListView lst_score;
    String url = Constante.url+"/joueur/score?id=";
    TextView message;
    LoadingDialogue loadingDialogue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score, container, false);
        lst_score = (ListView) view.findViewById(R.id.lst_score);
        message = view.findViewById(R.id.message);

        loadingDialogue = new LoadingDialogue(this.getActivity());
        loadingDialogue.startLoadingDialog();

        //controle de l'existance de persistance
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ScoreFragment.this.getContext());
        String user_id = pref.getString("user_id", null);
        if(user_id == null){
            Intent intent = new Intent(ScoreFragment.this.getContext(), LoginActivity.class);
            startActivity(intent);
        }else{

            //RESTAPI get score
            RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url+user_id, null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("error restapi get", response.toString());

                            ArrayList<HashMap<String, String>> score = new ArrayList<HashMap<String,String>>();
                            //avoir la liste
                            try {
                                Boolean error = response.getBoolean("error");
                                if(error){
                                    message.setText(response.getString("data"));
                                }else{
                                    JSONArray jsonArray = response.getJSONArray("data");
                                    JSONObject item = new JSONObject();
                                    for (int i=0; i < jsonArray.length(); i++){
                                        item = jsonArray.getJSONObject(i);
                                        HashMap<String,String> map = new HashMap<String,String>();
                                        map.put("valeur", item.getString("valeur"));
                                        map.put("pts", item.getString("score"));
                                        score.add(map);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            loadingDialogue.dismissLoadingDialog();

                            //affichage de la liste
                            SimpleAdapter adapter = new SimpleAdapter(view.getContext(), score, R.layout.score_item,
                                    new String[]{"valeur", "pts"},
                                    new int[]{R.id.lbl_theme, R.id.lbl_points}
                            );
                            lst_score.setAdapter(adapter);
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
        }

        return view;
    }
}