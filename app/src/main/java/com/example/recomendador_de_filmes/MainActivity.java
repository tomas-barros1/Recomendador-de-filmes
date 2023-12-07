package com.example.recomendador_de_filmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recomendador_de_filmes.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {

    TextInputLayout emailLayout, passwordLayout;
    TextInputEditText emailField, passwordField;
    Button loginButton;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLayout = findViewById(R.id.emailLoginLayout);
        passwordLayout = findViewById(R.id.passwordLoginLayout);
        emailField = findViewById(R.id.emailLoginField);
        passwordField = findViewById(R.id.passwordLoginField);
        loginButton = findViewById(R.id.login);
        signUp = findViewById(R.id.signUpText);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterScreen.class);
                startActivity(intent);
            }
        });

    }

    public boolean validateEmail () {
        String emailLogin = emailField.getText().toString();
        if (emailLogin.isEmpty()) {
            emailLayout.setError("Por favor, informe seu e-mail.");
            return false;
        } else {
           emailLayout.setError(null);
           return true;
        }
    }

    public boolean validatePassword () {
        String passwordLogin = passwordField.getText().toString();
        if (passwordLogin.isEmpty()) {
            passwordLayout.setError("Por favor, informe sua senha.");
            return false;
        } else {
            passwordLayout.setError(null);
            return true;
        }
    }
}

