package com.example.finalnativas.activities;

import android.os.Bundle;
import com.example.finalnativas.views.MoviesView;
import androidx.appcompat.app.AppCompatActivity;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear una instancia de la vista MoviesView
        MoviesView moviesView = new MoviesView(this);

        // Establecer la vista MoviesView como contenido de la actividad
        setContentView(moviesView);
    }
}
