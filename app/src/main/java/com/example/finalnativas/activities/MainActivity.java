package com.example.finalnativas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import com.example.finalnativas.views.MainView;
import com.example.finalnativas.database.DatabaseHelper;
import com.example.finalnativas.models.Movie;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecer el idioma que queramos depurar
        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        MainView mainView = new MainView(this);
        setContentView(mainView);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Insertar datos de ejemplo en la base de datos
        //insertExampleData();
    }

    private void insertExampleData() {
        // Abrir la base de datos en modo escritura
        databaseHelper.open();

        // Insertar películas de ejemplo
        databaseHelper.insertMovie(new Movie("Título 1", "Actor 1", "Fecha 1", "Ciudad 1"));
        databaseHelper.insertMovie(new Movie("Título 2", "Actor 2", "Fecha 2", "Ciudad 2"));
        databaseHelper.insertMovie(new Movie("Título 3", "Actor 3", "Fecha 3", "Ciudad 3"));

        // Cerrar la base de datos
        databaseHelper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del DatabaseHelper al destruir la actividad
        if (databaseHelper != null) {
            databaseHelper.close();
            databaseHelper = null;
        }
    }

}
