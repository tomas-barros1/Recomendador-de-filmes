package com.example.recomendador_de_filmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recomendador_de_filmes.R;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {
    private EditText login, password;
    private TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginField);
        password = findViewById(R.id.passwordField);
        createAccount = findViewById(R.id.createAccount);

        //ir para tela de cadastro
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), RegisterScreen.class);
                startActivity(signUp);
            }
        });
    }

    //lógica para validar login


}

