package com.example.recomendador_de_filmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recomendador_de_filmes.R;
import com.example.recomendador_de_filmes.Util.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {
    private EditText loginField, passwordField;
    private TextView createAccount;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginField = findViewById(R.id.loginField);
        passwordField = findViewById(R.id.passwordField);
        createAccount = findViewById(R.id.createAccount);

        auth = ConfigFirebase.FirebaseAuthentication();

        //ir para tela de cadastro
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), RegisterScreen.class);
                startActivity(signUp);
            }
        });
    }

    public void validateAuth(View view) {
        String email = loginField.getText().toString();
        String password = passwordField.getText().toString();

        if(!email.isEmpty()){
            if(!password.isEmpty()){

                Users user = new Users();

                user.setPassword(password);
                user.setEmail(email);

                login(user);

            } else {
                Toast.makeText(getApplicationContext(), "Preencha a senha!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Preencha o email!", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(Users user) {
        auth.signInWithEmailAndPassword(
                user.getEmail(),user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    goToOtherScreen();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Erro ao logar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //mudar tela depois
    public void goToOtherScreen() {
        Intent i = new Intent(getApplicationContext(), RegisterScreen.class);
        startActivity(i);
    }

}

