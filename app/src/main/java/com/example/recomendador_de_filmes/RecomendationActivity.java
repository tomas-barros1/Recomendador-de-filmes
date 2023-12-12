package com.example.recomendador_de_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private TextView recommendedTitle, recommendedGender, recommendedRelease, recommendedRating, recommendedImdb;
    private DatabaseReference reference;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation);

        recommendedTitle = findViewById(R.id.txtRecommendedTitle);
        recommendedGender = findViewById(R.id.txtRecommendedGender);
        recommendedRelease = findViewById(R.id.txtRecommendedRelease);
        recommendedRating = findViewById(R.id.txtRecommendedRating);
        recommendedImdb = findViewById(R.id.txtRecommendedImdb);
        returnButton = findViewById(R.id.returnButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("escolha")) {
            String escolha = intent.getStringExtra("escolha");

            reference = FirebaseDatabase.getInstance().getReference("filmes");
            consultarFilmesNoFirebase(escolha);
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profileAndChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void consultarFilmesNoFirebase(final String generoEscolhido) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Movie>> genericTypeIndicator = new GenericTypeIndicator<List<Movie>>() {};
                List<Movie> filmes = new ArrayList<>();

                for (DataSnapshot filmeSnapshot : dataSnapshot.getChildren()) {
                    Movie filme = filmeSnapshot.getValue(Movie.class);

                    if (filme != null && generoEscolhido.equals(filme.getGender())) {
                        filmes.add(filme);
                    }
                }

                if (!filmes.isEmpty()) {
                    Movie filmeEscolhido = filmes.get(new Random().nextInt(filmes.size()));
                    exibirFilmeRecomendado(filmeEscolhido);
                } else {
                    recommendedTitle.setText("Sem filmes disponíveis para este gênero");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                recommendedTitle.setText("Erro ao consultar filmes: " + databaseError.getMessage());
            }
        });
    }

    private void exibirFilmeRecomendado(Movie filme) {
        recommendedTitle.setText("Título: " + filme.getTitle());
        recommendedGender.setText("Gênero: " + filme.getGender());
        recommendedRelease.setText("Ano de Lançamento: " + filme.getRelease());
        recommendedRating.setText("Classificação Indicativa: " + filme.getRating());
        recommendedImdb.setText("IMDB: " + filme.getImdb());
    }
}
