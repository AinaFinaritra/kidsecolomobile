package com.example.ecomania.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomania.R;
import com.example.ecomania.model.Joueur;
import com.example.ecomania.model.JoueurModel;
import com.example.ecomania.utils.ApiInterface;
import com.example.ecomania.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button btn_inscription;
    private ProgressBar loadingPB;
    private int login_success = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //initialisation
        login_success = 0;
        usernameEditText = findViewById(R.id.login_usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        btn_inscription = findViewById(R.id.btn_inscription);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0) {
                    //login post de fy
                    login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    //fin login

                    if(login_success == 1){
                        Bundle extras = getIntent().getExtras();
                        //si le login vient avant acces au quiz
                        if(extras != null){
                            String idTheme = extras.getString("idTheme");
                            Intent intent = new Intent(LoginActivity.this, QuizActivity.class);
                            intent.putExtra("idTheme", idTheme);
                            startActivity(intent);
                        }
                        //si c un login qui vient du setting
                        else{
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }

                } else {
                    String toastMessage = "Champs obligatoires";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });

    }

    void login(String pseudo, String mdp) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Joueur> call = apiInterface.loginJoueur(pseudo, mdp);
        call.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                //JSONArray the_json_array = response.body().getData().;
                if(response.body().getData().size()!=0){
                    JoueurModel jm= (JoueurModel)response.body().getData().get(0);

                    //stockage dans la persistance
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("username", jm.getPseudo());
                    edit.putString("user_id", jm.getId());
                    edit.commit();
                    String username = pref.getString("user_id", "null");
                    Log.e("(LoginActivity) user_id? ", username);
                    login_success = 1;

                }else{
                    String toastMessage = "Login ou mot de passe erroné";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    login_success = 0;
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {
                Log.e("Misy err login ", t.getMessage());
                Log.e("err ", t.getCause().toString());
            }
        });
    }
}