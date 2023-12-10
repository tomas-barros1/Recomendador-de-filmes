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

import com.example.recomendador_de_filmes.Util.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {
    private EditText loginField, passwordField;
    private TextView createAccount;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginField = findViewById(R.id.emailLoginField);
        passwordField = findViewById(R.id.passwordLoginField);

        auth = ConfigFirebase.FirebaseAuthentication();
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
                    Toast.makeText(getApplicationContext(), "Login bem sucedido!", Toast.LENGTH_SHORT).show();
                    goToNextScreen();
                }
                else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Usuário não está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Email ou senha incorretos!";
                    } catch (Exception e) {
                        exception = "Erro ao logar o usuário!" + e.getMessage();
                    }
                    Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //mudar tela depois
    public void goToNextScreen() {
        Intent i = new Intent(getApplicationContext(), profileAndChooseActivity.class);
        startActivity(i);
    }

    public void goToSingUp(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterScreen.class);
        startActivity(i);
    }

}
