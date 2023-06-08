package com.example.finalnativas.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.finalnativas.views.AddFilmView;

public class AddFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear una instancia de AddFilmView y establecerla como el contenido de la actividad
        AddFilmView addFilmView = new AddFilmView(this);
        setContentView(addFilmView);
    }
}
