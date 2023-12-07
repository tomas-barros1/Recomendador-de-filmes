package com.example.recomendador_de_filmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterMovies extends AppCompatActivity {

    TextInputEditText titleMovie, genderMovie, releaseMovie, ratingMovie, imdbMovie;
    Button addMovie;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movies);

        titleMovie = findViewById(R.id.titleField);
        genderMovie = findViewById(R.id.genderField);
        releaseMovie = findViewById(R.id.releaseField);
        ratingMovie = findViewById(R.id.ratingField);
        imdbMovie = findViewById(R.id.imdbField);
        addMovie = findViewById(R.id.addMovie);

        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleMovie.getText().toString();
                String gender = genderMovie.getText().toString();
                String release = releaseMovie.getText().toString();
                String rating = ratingMovie.getText().toString();
                String imdb = imdbMovie.getText().toString();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(release) || TextUtils.isEmpty(rating) || TextUtils.isEmpty(imdb)) {
                    Toast.makeText(RegisterMovies.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("filmes");

                    Movie movie = new Movie(title, gender, release, rating, imdb);
                    reference.child(title).setValue(movie);

                    Toast.makeText(RegisterMovies.this, "Filme adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ParametersActivity2.class);
                    startActivity(intent);
                }
            }
        });
    }
}