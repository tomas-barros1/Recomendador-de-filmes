package com.example.recomendador_de_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ParametersActivity1 extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters1);

        radioGroup = findViewById(R.id.radioGroup);

        Button avancar = findViewById(R.id.btnAvancar);
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    String escolha = radioButton.getText().toString();

                    Intent intent = new Intent(ParametersActivity1.this, RecomendationActivity.class);
                    intent.putExtra("escolha", escolha);
                    startActivity(intent);
                }
            }
        });
    }
}