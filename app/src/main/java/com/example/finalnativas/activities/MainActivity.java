package com.example.finalnativas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import com.example.finalnativas.views.MainView;
import com.example.finalnativas.database.DatabaseHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecer el idioma que queramos depurar (vamos a trabajar con el italiano)
        Locale locale = new Locale("it");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Se establece la main view de la pantalla. Esta main view constituye la pantalla inicial
        // de nuestra aplicación
        MainView mainView = new MainView(this);
        setContentView(mainView);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

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
