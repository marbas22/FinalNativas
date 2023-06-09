package com.example.finalnativas.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalnativas.R;
import com.example.finalnativas.activities.AddFilmActivity;
import com.example.finalnativas.activities.MainActivity;
import com.example.finalnativas.adapters.MoviesAdapter;
import com.example.finalnativas.models.Movie;
import com.example.finalnativas.database.DatabaseHelper;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class MoviesView extends CollapsingToolbarLayout {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private DatabaseHelper databaseHelper;

    public MoviesView(Context context) {
        super(context);
        initialize(context, null);
    }

    public MoviesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {

        // Código autogenerado por android studio al elegir un componente determinado
        LayoutInflater.from(context).inflate(R.layout.activity_movies, this, true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((AppCompatActivity) context, toolbar);

        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(context.getString(R.string.title_movies_activity));
        toolBarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingToolbarTitleStyle);
        toolBarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarTitleStyle);

        // Obtener la instancia del recycler
        recyclerView = findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Obtener la instancia de la BBDD
        databaseHelper = new DatabaseHelper(context);

        // Obtener todas las películas que el usuario ha guardado en su lista de películas
        List<Movie> movies = databaseHelper.getAllMovies();
        moviesAdapter = new MoviesAdapter(movies, context);
        recyclerView.setAdapter(moviesAdapter);

        // Manejar el evento de volver a la pantalla principal de la aplicación
        Button buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llevar al usuario a la MainActivity
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        // Manejar el evento de ir a la pantalla de búsqueda de películas para añadir
        Button buttonAddFilm = findViewById(R.id.button_add_movie);
        buttonAddFilm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llevar al usuario a la AddFilmActivity
                Intent intent = new Intent(context, AddFilmActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void setSupportActionBar(AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
    }
}
