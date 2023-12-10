package com.example.recomendador_de_filmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class profileAndChooseActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "SharedPrefs";
    TextView usernameProfile, emailProfile;
    ImageButton goToMovies;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_and_choose);

        usernameProfile = findViewById(R.id.profileName);
        emailProfile = findViewById(R.id.profileEmail);
        goToMovies = findViewById(R.id.selectMovie);

        logOut = findViewById(R.id.logOut);

        showUserData();

        goToMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParametersActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLoginData();

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void showUserData() {
        Intent intent = getIntent();

        String nameUser = intent.getStringExtra("username");
        String emailUser = intent.getStringExtra("email");

        usernameProfile.setText(nameUser);
        emailProfile.setText(emailUser);
    }

    private void clearLoginData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
