package com.example.recomendador_de_filmes;// Importe as bibliotecas necessárias

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recomendador_de_filmes.R;
import com.example.recomendador_de_filmes.RegisterMovies;

public class IntermediaryActivity extends AppCompatActivity {

  private EditText numberEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intermediary);

    numberEditText = findViewById(R.id.numberEditText);

    Button checkNumberButton = findViewById(R.id.checkNumberButton);
    checkNumberButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkNumber();
      }
    });

    Button backButton = findViewById(R.id.backButton);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }

  private void checkNumber() {
    String enteredNumber = numberEditText.getText().toString();
    if (!TextUtils.isEmpty(enteredNumber) && enteredNumber.equals("321")) {
      Intent intent = new Intent(IntermediaryActivity.this, RegisterMovies.class);
      startActivity(intent);
      finish();
    } else {
      Toast.makeText(this, "Número incorreto.", Toast.LENGTH_SHORT).show();
    }
  }
}
