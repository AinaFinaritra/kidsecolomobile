package com.example.ecomania.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomania.R;
import com.example.ecomania.model.QuestionResponse;
import com.example.ecomania.model.ReponseJoueur;
import com.example.ecomania.utils.ApiInterface;
import com.example.ecomania.utils.Constante;
import com.example.ecomania.utils.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView total_question;
    TextView question;
    Button reponse1, reponse2, reponse3;
    Button submit;
    QuestionResponse questionResponse;

    int total_questions;
    int score = 0;
    int current_question_index = 0;
    int firstInteraction = 0;
    String selectedAnswer = "";

    String url = Constante.url+"/question/niveau?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();
        questionResponse = QuestionResponse.getInstance();

        //initialise
        total_question = findViewById(R.id.total_question);
        question = findViewById(R.id.question);
        reponse1 = findViewById(R.id.reponse1);
        reponse2 = findViewById(R.id.reponse2);
        reponse3 = findViewById(R.id.reponse3);
        submit = findViewById(R.id.submit);

        //setting click listner
        reponse1.setOnClickListener(this);
        reponse2.setOnClickListener(this);
        reponse3.setOnClickListener(this);
        submit.setOnClickListener(this);

        //GetExtra
        Bundle extras = getIntent().getExtras();
        String idTheme = "";
        if(extras != null){
            idTheme = extras.getString("idTheme");
        }

        //get niveau
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QuizActivity.this);
        String id_niveau = pref.getString("idNiveau", null);

        //RESTAPI get question_reponse
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url+"idniveau="+id_niveau+"&idtheme="+idTheme, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("error restapi get", response.toString());

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            questionResponse.configureQuestionReponse(jsonArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error restapi get", error.toString());
                    }
                }
        );

        //initialisation de la premier question
        loadNewQuestion();

        requestQueue.add(objectRequest);
    }


    @Override
    public void onClick(View view) {

        reponse1.setBackgroundColor(Color.WHITE);
        reponse2.setBackgroundColor(Color.WHITE);
        reponse3.setBackgroundColor(Color.WHITE);
        Button clicked = (Button) view;

        if(firstInteraction != 0){
            if(clicked.getId() == R.id.submit){
                if(selectedAnswer.equals(questionResponse.correctAnswers.get(current_question_index).get("reponse"))){
                    //insert reponse WS
                    String idquestion = questionResponse.correctAnswers.get(current_question_index).get("idquestion");
                    int pts = Integer.parseInt(questionResponse.correctAnswers.get(current_question_index).get("score"));
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QuizActivity.this);
                    String idJoueur = pref.getString("user_id", "null");
                    insertReponse(idJoueur, idquestion, pts);

                    score = score + Integer.parseInt(questionResponse.correctAnswers.get(current_question_index).get("score"));
                }
                current_question_index++;
                loadNewQuestion();
            }else{
                //choices
                selectedAnswer = clicked.getText().toString();
                clicked.setBackgroundColor(Color.MAGENTA);
            }
        }else{
            firstInteraction = 1;
            loadNewQuestion();
        }
    }

    void loadNewQuestion(){

        if(questionResponse.question != null){
            total_questions = questionResponse.question.size();
            total_question.setText("Total questions : "+total_questions);
            if(total_questions == 0){
                firstInteraction = 0;
                question.setText("Aucune question trouvée!");
            }else{

                if(current_question_index == total_questions){
                    new AlertDialog.Builder(this)
                            .setTitle("Ton score")
                            .setMessage("tu as "+score+" points sur "+total_questions+" question(s)")
                            .setPositiveButton("Refaire le quiz", ((dialogInterface, i) -> restartQuiz()))
                            .setNeutralButton("revenir au menu principal", ((dialogInterface, i) -> quitQuiz()))
                            .setCancelable(false)
                            .show();
                    return;
                }else{
                    //continue question
                    question.setText(questionResponse.question.get(current_question_index).get("question"));
                    reponse1.setText(questionResponse.choices.get(current_question_index).get(0).get("reponse"));
                    reponse2.setText(questionResponse.choices.get(current_question_index).get(1).get("reponse"));
                    reponse3.setText(questionResponse.choices.get(current_question_index).get(2).get("reponse"));
                }
            }
        }else{
            firstInteraction = 0;
            question.setText("Choisis une réponse et clique le button 'Suivant' pour lancer le quiz !");
        }
    }

    void restartQuiz(){
        score=0;
        current_question_index=0;
        loadNewQuestion();
    }

    void quitQuiz(){
        Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    void insertReponse(String idJoueur, String idquestion, int pts){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ReponseJoueur> call = apiInterface.envoyer(idquestion, idJoueur, pts);
        call.enqueue(new Callback<ReponseJoueur>() {
            @Override
            public void onResponse(Call<ReponseJoueur> call, retrofit2.Response<ReponseJoueur> response) {
                Log.e("Success", response.body().getStatus());
            }

            @Override
            public void onFailure(Call<ReponseJoueur> call, Throwable t) {
                Log.e("Misy err login ", t.getMessage());
                Log.e("err ", t.getCause().toString());
            }
        });
    }
}