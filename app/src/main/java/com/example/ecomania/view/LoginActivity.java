package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecomania.R;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button btn_inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //initialisation
        usernameEditText = findViewById(R.id.login_usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        btn_inscription = findViewById(R.id.btn_inscription);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0) {
                    //login post de fy

                    //stockage dans la persistance
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("username", usernameEditText.getText().toString());
                    edit.putString("user_id", passwordEditText.getText().toString());
                    edit.commit();
                    String username = pref.getString("user_id", "null");
                    Log.e("(LoginActivity) user_id? ", username);

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
}