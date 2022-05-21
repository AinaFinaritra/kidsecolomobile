package com.example.ecomania.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomania.R;
import com.example.ecomania.controller.ThemeController;
import com.example.ecomania.model.Theme;
import com.example.ecomania.utils.Constante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    View view;
    ListView liste_theme;
    private ThemeController themeController;
    String url = Constante.url+"/theme/all";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.themeController = ThemeController.getInstance();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        liste_theme = (ListView) view.findViewById(R.id.liste_theme);

        //RESTAPI get theme
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("error restapi get", response.toString());

                        ArrayList<HashMap<String, String>> theme = new ArrayList<HashMap<String,String>>();
                        //avoir la liste
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject item = new JSONObject();
                            for (int i=0; i < jsonArray.length(); i++){
                                item = jsonArray.getJSONObject(i);
                                HashMap<String,String> map = new HashMap<String,String>();
                                map.put("idtheme", item.getString("id"));
                                map.put("theme", item.getString("valeur"));
                                map.put("description", item.getString("desce"));
                                theme.add(map);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        themeController.setTheme(theme);

                        // affichage de la liste
                        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), theme, R.layout.theme_item,
                                new String[]{"theme", "description"},
                                new int[]{R.id.theme, R.id.description}
                        );
                        liste_theme.setAdapter(adapter);
                        // affichage de la liste end

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error restapi get", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);

        // click sur liste
        liste_theme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), DetailHomeActivity2.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
        // click sur liste end

        return view;
    }
}