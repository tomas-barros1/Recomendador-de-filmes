package com.example.recomendador_de_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recomendador_de_filmes.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecomendationActivity extends AppCompatActivity {

    private TextView recommendedMovie;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation);

        recommendedMovie = findViewById(R.id.txtRecommendedMovie);

        // Receba a escolha do usuário da atividade anterior
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("escolha")) {
            String escolha = intent.getStringExtra("escolha");

            // Agora você tem a escolha, você pode usá-la para consultar o Firebase
            reference = FirebaseDatabase.getInstance().getReference("filmes");
            consultarFilmesNoFirebase(escolha);
        }
    }

    private void consultarFilmesNoFirebase(final String generoEscolhido) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Converter o snapshot para uma lista de filmes
                GenericTypeIndicator<List<Movie>> genericTypeIndicator = new GenericTypeIndicator<List<Movie>>() {};
                List<Movie> filmes = new ArrayList<>();

                for (DataSnapshot filmeSnapshot : dataSnapshot.getChildren()) {
                    Movie filme = filmeSnapshot.getValue(Movie.class);

                    // Verificar se o filme tem o gênero escolhido
                    if (filme != null && generoEscolhido.equals(filme.getGender())) {
                        filmes.add(filme);
                    }
                }

                // Escolher aleatoriamente um filme
                if (!filmes.isEmpty()) {
                    Movie filmeEscolhido = filmes.get(new Random().nextInt(filmes.size()));
                    exibirFilmeRecomendado(filmeEscolhido);
                } else {
                    recommendedMovie.setText("Sem filmes disponíveis para este gênero");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Lidar com falhas na consulta
                recommendedMovie.setText("Erro ao consultar filmes: " + databaseError.getMessage());
            }
        });
    }

    private void exibirFilmeRecomendado(Movie filme) {
        // Aqui, você pode obter os detalhes do filme e exibi-los na TextView ou em outro local da interface do usuário
        String nomeFilme = filme.getTitle();
        recommendedMovie.setText("Recomendação: " + nomeFilme);
    }
}
