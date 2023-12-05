package com.example.recomendador_de_filmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recomendador_de_filmes.Util.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {
    private TextView emailField, passwordField, confirmPasswordField;
    FirebaseAuth auth;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        user = new Users();
    }

    public void validateFields(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        user.setEmail(email);

        if (password.equals(confirmPassword)) {
            user.setPassword(password);
        }
        else {
            Toast.makeText(this, "As senhas estão diferentes!", Toast.LENGTH_SHORT).show();
        }

        if (!email.isEmpty() || !password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }

        registerUser();
    }

    public void registerUser() {

        auth = ConfigFirebase.FirebaseAuthentication();

        auth.createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() { // Ajuste aqui
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterScreen.this, "Registro bem sucedido!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterScreen.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}