package com.example.greenspot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tutorial2 extends AppCompatActivity {

    ImageView btnCarrossel1;
    ImageView btnCarrossel2;
    ImageView btnCarrossel3;
    ImageView btnPular;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutorial2);

        btnCarrossel1 = findViewById(R.id.btnCarrossel1);
        btnCarrossel2 = findViewById(R.id.btnCarrossel2);
        btnCarrossel3 = findViewById(R.id.btnCarrossel3);
        btnPular = findViewById(R.id.btnPular);

        btnCarrossel1.setOnClickListener(view -> {
            Intent intent = new Intent(this, Tutorial1.class);

            startActivity(intent);
        });

        btnCarrossel3.setOnClickListener(view -> {
            Intent intent = new Intent(this, Tutorial3.class);

            startActivity(intent);
        });

        btnPular.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}