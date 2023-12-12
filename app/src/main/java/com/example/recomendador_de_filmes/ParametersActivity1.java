package com.example.recomendador_de_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ParametersActivity1 extends AppCompatActivity {

    private RadioGroup radioGroup;
    Button backToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters1);

        radioGroup = findViewById(R.id.radioGroup);
        backToProfile = findViewById(R.id.backToProfile);

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParametersActivity1.this, profileAndChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    String escolha = radioButton.getText().toString();
                    Log.d("ParametersActivity1", "Escolha do usuário: " + escolha);

                    Intent intent = new Intent(ParametersActivity1.this, RecomendationActivity.class);
                    intent.putExtra("escolha", escolha);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
