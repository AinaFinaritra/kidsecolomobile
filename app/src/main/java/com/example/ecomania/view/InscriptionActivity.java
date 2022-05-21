package com.example.ecomania.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecomania.R;

public class InscriptionActivity extends AppCompatActivity {
    private EditText inscription_NameEditText;
    private EditText inscription_FirstNameEditText;
    private EditText inscription_pseudoEditText;
    private EditText inscription_passwordEditText;
    private EditText inscription_confirm_passwordEditText;
    private Button inscriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        getSupportActionBar().hide();

        //init params
        inscription_NameEditText = findViewById(R.id.inscription_NameEditText);
        inscription_FirstNameEditText = findViewById(R.id.inscription_FirstNameEditText);
        inscription_pseudoEditText = findViewById(R.id.inscription_pseudoEditText);
        inscription_passwordEditText = findViewById(R.id.inscription_passwordEditText);
        inscription_confirm_passwordEditText = findViewById(R.id.inscription_confirm_passwordEditText);
        inscriptionButton = findViewById(R.id.inscriptionButton);

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inscription_NameEditText.getText().length() > 0
                        && inscription_FirstNameEditText.getText().length() > 0
                        && inscription_pseudoEditText.getText().length() > 0
                        && inscription_passwordEditText.getText().length() > 0
                        && inscription_confirm_passwordEditText.getText().length() > 0
                ) {
                    String inscription_Name = inscription_NameEditText.getText().toString();
                    String inscription_FirstName = inscription_FirstNameEditText.getText().toString();
                    String inscription_pseudo = inscription_pseudoEditText.getText().toString();
                    String inscription_password = inscription_passwordEditText.getText().toString();
                    String inscription_confirm_password = inscription_confirm_passwordEditText.getText().toString();

                    if(inscription_password.compareToIgnoreCase(inscription_confirm_password) == 0){
                        //inscription WS de FY

                        String toastMessage = "Entrer le pseudo et le mots de passe";
                        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }else{
                        String toastMessage = "Erreur confirmation mots de passe";
                        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String toastMessage = "Champs obligatoires";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}