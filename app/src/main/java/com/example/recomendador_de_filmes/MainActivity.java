package com.example.recomendador_de_filmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recomendador_de_filmes.Util.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {
    private ImageButton admImg;
    private EditText loginField, passwordField;
    private TextView admText;
    private FirebaseAuth auth;
    private CheckBox rememberMe;
    public static final String SHARED_PREFS = "SharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginField = findViewById(R.id.emailLoginField);
        passwordField = findViewById(R.id.passwordLoginField);
        admText = findViewById(R.id.admText);
        admImg = findViewById(R.id.admpage);
        rememberMe = findViewById(R.id.rememberMe);

        admImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntermediaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        admText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntermediaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        auth = ConfigFirebase.FirebaseAuthentication();

        loadSavedLoginData();

        if (rememberMe.isChecked()) {
            autoLoginToProfile();
        }
    }

    public void autoLoginToProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            Users user = new Users();
            user.setPassword(savedPassword);
            user.setEmail(savedEmail);

            login(user);
        }
    }

    public void loadSavedLoginData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            loginField.setText(savedEmail);
            passwordField.setText(savedPassword);
            rememberMe.setChecked(true);
        }
    }

    public void validateAuth(View view) {
        String email = loginField.getText().toString();
        String password = passwordField.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            Users user = new Users();
            user.setPassword(password);
            user.setEmail(email);

            if (rememberMe.isChecked()) {
                saveLoginData(email, password);
            }

            login(user);
        } else {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveLoginData(String email, String password) {
        Log.d("RememberMe", "Saving login data - Email: " + email + ", Password: " + password);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    public void login(Users user) {
        auth.signInWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    goToNextScreen();
                } else {
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

    public void goToNextScreen() {
        Intent intent = new Intent(getApplicationContext(), profileAndChooseActivity.class);
        startActivity(intent);
    }

    public void goToSingUp(View view) {
        Intent i = new Intent(getApplicationContext(), RegisterScreen.class);
        startActivity(i);
    }
}
