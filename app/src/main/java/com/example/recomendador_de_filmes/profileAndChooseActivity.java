package com.example.recomendador_de_filmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileAndChooseActivity extends AppCompatActivity {
    private TextView name, email;
    private ImageButton selectMovie, selectBook, selectSerie, selectAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_and_choose);
        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        selectMovie = findViewById(R.id.selectMovie);
        selectBook = findViewById(R.id.selectBook);
        selectSerie= findViewById(R.id.selectShow);
        selectAnimation = findViewById(R.id.selectCartoon);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userEmail = user.getEmail();

            email.setText(userEmail);

        } else {
            Toast.makeText(getApplicationContext(), "Usuário não autenticado", Toast.LENGTH_SHORT).show();
        }
        selectMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParametersActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        selectBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Em desenvolvimento!", Toast.LENGTH_SHORT).show();
            }
        });

        selectSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Em desenvolvimento!", Toast.LENGTH_SHORT).show();
            }
        });

        selectAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Em desenvolvimento!", Toast.LENGTH_SHORT).show();
            }
        });

    }



}