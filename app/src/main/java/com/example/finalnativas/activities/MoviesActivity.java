package com.example.finalnativas.activities;

import android.os.Bundle;
import com.example.finalnativas.views.MoviesView;
import androidx.appcompat.app.AppCompatActivity;


public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoviesView moviesView = new MoviesView(this);
        setContentView(moviesView);
    }
}
