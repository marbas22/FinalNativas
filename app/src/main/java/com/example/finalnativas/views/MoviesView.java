package com.example.finalnativas.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalnativas.R;
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
        LayoutInflater.from(context).inflate(R.layout.activity_movies, this, true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((AppCompatActivity) context, toolbar);

        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(context.getString(R.string.title_movies_activity));
        toolBarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingToolbarTitleStyle);
        toolBarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarTitleStyle);

        recyclerView = findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        databaseHelper = new DatabaseHelper(context);

        List<Movie> movies = databaseHelper.getAllMovies();
        moviesAdapter = new MoviesAdapter(movies, context);
        recyclerView.setAdapter(moviesAdapter);

        Button buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llevar al usuario a la MainActivity
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void setSupportActionBar(AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
    }
}
