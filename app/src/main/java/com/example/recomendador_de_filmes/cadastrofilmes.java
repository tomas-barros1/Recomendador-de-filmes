package com.example.recomendador_de_filmes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroFilmes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrofilmes);

        final EditText editTextMovieName = findViewById(R.id.editTextMovieName);
        final EditText editTextRating = findViewById(R.id.editTextRating);
        final EditText editTextImdbRating = findViewById(R.id.editTextImdbRating);
        final EditText editTextGenre = findViewById(R.id.editTextGenre);
        Button btnCadastrarFilme = findViewById(R.id.btnCadastrarFilme);

        btnCadastrarFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieName = editTextMovieName.getText().toString();
                String rating = editTextRating.getText().toString();
                String imdbRating = editTextImdbRating.getText().toString();
                String genre = editTextGenre.getText().toString();

                String mensagem = "Nome: " + movieName + "\nClassificação: " + rating +
                        "\nIMDb: " + imdbRating + "\nGênero: " + genre;

                Toast.makeText(CadastroFilmes.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
