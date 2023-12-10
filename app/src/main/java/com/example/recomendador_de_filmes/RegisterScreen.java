package com.example.recomendador_de_filmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recomendador_de_filmes.Util.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterScreen extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[\\S+$])" +
                    ".{6,}" +
                    "$");

    private TextInputEditText usernameField, emailField, passwordField, confirmPasswordField;
    private TextInputLayout emailLayout, passwordLayout, confirmPasswordLayout;
    Button button, backButton;
    FirebaseAuth auth;
    Users user;
    FirebaseDatabase fireDataBase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        usernameField = findViewById(R.id.usernameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        button = findViewById(R.id.button);
        backButton = findViewById(R.id.backButton);
        fireDataBase = FirebaseDatabase.getInstance();
        databaseReference = fireDataBase.getReference("usuarios");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        user = new Users();
    }

    public boolean validateEmail () {
        String email = emailField.getText().toString().trim();

        if (email.isEmpty()) {
            emailLayout.setError("O campo não pode estar vazio.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Informe um e-mail válido.");
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }
    }

    private boolean validatePassword () {
        String password = passwordField.getText().toString().trim();

        if (password.isEmpty()) {
            passwordLayout.setError("O campo não pode estar vazio.");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            passwordLayout.setError("Senha muito fraca. Sua senha precisa de ao menos 6 caracteres");
            return false;
        }else {
            passwordLayout.setError(null);
            return true;
        }
    }

    public boolean validateConfirmPassword () {
        String confirmPassword = confirmPasswordField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (confirmPassword.isEmpty()) {
            confirmPasswordLayout.setError("Necessário informar novamente sua senha.");
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordLayout.setError("As senhas não correspondem.");
            return false;
        }else {
            confirmPasswordLayout.setError(null);
            return true;
        }
    }

    public void validateFields(View view) {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (!validateEmail() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }

        user.setEmail(email);
        user.setPassword(password);

        registerUser();
    }

    public void saveUserOnDatabase() {
        String username = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterScreen.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else {
            Users users = new Users(username, email, password);
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference.child(uid).setValue(users);

            Toast.makeText(RegisterScreen.this, "Conta adicionada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser() {
        auth = ConfigFirebase.FirebaseAuthentication();

        auth.createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() { // Ajuste aqui
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterScreen.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                    saveUserOnDatabase();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterScreen.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
