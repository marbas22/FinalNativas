package com.example.finalnativas.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.finalnativas.R;
import com.example.finalnativas.activities.AddFilmActivity;
import com.example.finalnativas.activities.MoviesActivity;
import com.example.finalnativas.utils.MenuHelper;

public class MainView extends LinearLayout {

    private MenuHelper menuHelper;  // Declarar una variable de instancia de la clase MenuHelper
    private Activity activity;  // Declarar una variable de instancia de la clase Activity para almacenar la referencia a la actividad actual

    public MainView(Context context) {
        super(context);
        initialize(context);  // Llamar al método initialize para inicializar la vista
    }

    private void initialize(Context context) {
        // Obtener la referencia a la actividad actual
        activity = (Activity) context;

        // Inflar el diseño de la vista desde el archivo XML view_main
        LayoutInflater.from(context).inflate(R.layout.view_main, this, true);

        // Obtener la referencia al botón de menú en la vista
        Button btnMenu = findViewById(R.id.btnMenu);
        // Obtener la referencia al botón "View Movies" en la vista
        Button btnViewMovies = findViewById(R.id.btnViewMovies);
        // Obtener la referencia al botón "Add Movie" en la vista
        Button btnAddMovie = findViewById(R.id.btnAddMovie);

        // Establecer un listener para el botón "Ver Peliculas"
        btnViewMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MoviesActivity.class);
                getContext().startActivity(intent);  // Iniciar la actividad MoviesActivity
            }
        });

        // Establecer un listener para el botón "Add Movie"
        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFilmActivity.class);
                getContext().startActivity(intent);  // Iniciar la actividad AddFilmActivity
            }
        });

        menuHelper = new MenuHelper(context);
        // Adjuntar el menú al botón de menú (=)
        menuHelper.attachMenuToButton(btnMenu, R.menu.menu_main, new MenuHelper.OnMenuItemClickListener() {

            // Definir el listener para los elementos del menú
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                // Verificar si se seleccionó el elemento "Exit" del menú (pensado por si se meten más
                // opciones en el menú
                if (itemId == R.id.menu_exit) {
                    // Mostrar el diálogo de confirmación de salida
                    menuHelper.showExitConfirmationDialog(new MenuHelper.OnExitConfirmedListener() {
                        @Override
                        public void onExitConfirmed() {
                            // Finalizar todas las actividades de la aplicación
                            activity.finishAffinity();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }
}
