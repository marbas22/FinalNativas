package com.example.finalnativas.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.finalnativas.views.AddFilmView;

public class AddFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se crea una instancia de AddFilmView y se establece como el contenido de la actividad
        AddFilmView addFilmView = new AddFilmView(this);
        setContentView(addFilmView);
    }
}
