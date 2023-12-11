package com.example.recomendador_de_filmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileAndChooseActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "SharedPrefs";
    private DatabaseReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView usernameProfile, emailProfile;
    ImageButton goToMovies;
    Button logOut;
    String currentUserEmail, currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_and_choose);

        usernameProfile = findViewById(R.id.profileName);
        emailProfile = findViewById(R.id.profileEmail);
        goToMovies = findViewById(R.id.selectMovie);

        logOut = findViewById(R.id.logOut);

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

    @Override
    protected void onStart() {
        super.onStart();

        currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = database.getReference("usuarios").child(currentUserId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("username").getValue(String.class);

                usernameProfile.setText(username);
                emailProfile.setText(currentUserEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearLoginData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
