package com.example.recomendador_de_filmes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParametersActivity2 extends AppCompatActivity {

    private TextView textView;
    private RatingBar ratingBar;
    private EditText dataMinimaEditText, dataMaximaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters2);

        SeekBar seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        Spinner classificacaoSpinner = findViewById(R.id.classificacaoSpinner);
        ratingBar = findViewById(R.id.ratingBar);
        dataMinimaEditText = findViewById(R.id.dataMinimaEditText);
        Button btnFinalizar = findViewById(R.id.btnFinalizar);

        seekBar.setMax(120);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress + 90;
                int hours = minutes / 60;
                int remainingMinutes = minutes % 60;

                textView.setText("Duração Máxima: " + String.format("%02d:%02d", hours, remainingMinutes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.classificacao_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classificacaoSpinner.setAdapter(adapter);

        classificacaoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
        });

        btnFinalizar.setOnClickListener(v -> finalizarAcao());
    }

    private void finalizarAcao() {
        String dataMinima = dataMinimaEditText.getText().toString();
        String dataMaxima = dataMaximaEditText.getText().toString();
        float avaliacao = ratingBar.getRating();

        String mensagem = "Data Mínima: " + dataMinima + "\nData Máxima: " + dataMaxima + "\nAvaliação: " + avaliacao;
        Toast.makeText(ParametersActivity2.this, mensagem, Toast.LENGTH_SHORT).show();


    }
}