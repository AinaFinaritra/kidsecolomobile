package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomania.R;
import com.example.ecomania.adapter.DetailThemeItemAdapter;
import com.example.ecomania.controller.DetailsThemeController;
import com.example.ecomania.controller.ThemeController;
import com.example.ecomania.model.DetailsTheme;
import com.example.ecomania.utils.LoadingDialogue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private RecyclerView rcl_detail_theme;
    private Button btn_quiz;
    private String url = "https://kidsecolonode.herokuapp.com/theme/details?idtheme=";

    LoadingDialogue loadingDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home2);
        getSupportActionBar().hide();

        //loading
        loadingDialogue = new LoadingDialogue(DetailHomeActivity2.this);
        loadingDialogue.startLoadingDialog();

        //data theme
        this.themeController = themeController.getInstance();
        theme = this.themeController.getThemeTest();

        //get view
        this.rcl_detail_theme = findViewById(R.id.rcl_detail_theme);
        this.lbl_titre = findViewById(R.id.lbl_titre);
        this.btn_quiz = findViewById(R.id.btn_quiz);

        //recuperation de la position de la liste
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("position");
        }
        chosed_theme = theme.get(position); //objet recuperer
        String theme_text = chosed_theme.get("theme");
        String id_theme = chosed_theme.get("idtheme");

        //setDuText lbl_titre
        this.lbl_titre.setText(theme_text);

        //initialization liste to item
        detailsThemeController = detailsThemeController.getInstance();

        //RESTAPI get detail theme by id
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url+id_theme, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("error restapi get", response.toString());

                        ArrayList<DetailsTheme> detailsList = new ArrayList<DetailsTheme>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject item = new JSONObject();

                            for (int i=0; i < jsonArray.length(); i++){
                                item = jsonArray.getJSONObject(i);
                                DetailsTheme tmpdetail = new DetailsTheme(
                                        item.getString("idtheme"),
                                        item.getString("titre"),
                                        item.getString("desce"),
                                        item.getString("img"),
                                        item.getString("video")
                                );
                                detailsList.add(tmpdetail);
                            }

                            loadingDialogue.dismissLoadingDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //adapter recycler view
                        DetailThemeItemAdapter adapter = new DetailThemeItemAdapter(detailsList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        rcl_detail_theme.setLayoutManager(layoutManager);
                        rcl_detail_theme.setItemAnimator(new DefaultItemAnimator());

                        rcl_detail_theme.setAdapter(adapter);

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

        //

        //pass to quiz
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(DetailHomeActivity2.this);
                String user_id = pref.getString("user_id", null);
                String id_niveau = pref.getString("idNiveau", null);
                if(id_niveau != null){
                    if(user_id != null){
                        Intent intent = new Intent(DetailHomeActivity2.this, QuizActivity.class);
                        intent.putExtra("idTheme", id_theme);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(DetailHomeActivity2.this, LoginActivity.class);
                        intent.putExtra("idTheme", id_theme);
                        startActivity(intent);
                    }
                }else{
                    CharSequence text = "Selection niveau obligatoire !";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                    toast.show();
                    Intent intent = new Intent(DetailHomeActivity2.this, HomeActivity.class);
                    intent.putExtra("toSetting", "yes");
                    startActivity(intent);
                }
            }
        });

    }
}