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
import com.example.finalnativas.activities.MoviesActivity;
import com.example.finalnativas.utils.MenuHelper;

public class MainView extends LinearLayout {
    private MenuHelper menuHelper;
    private Activity activity; // Agrega una variable de instancia para almacenar la referencia a la actividad actual

    public MainView(Context context) {
        super(context);
        initialize(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        activity = (Activity) context; // Obtener la referencia a la actividad actual

        LayoutInflater.from(context).inflate(R.layout.view_main, this, true);
        Button btnMenu = findViewById(R.id.btnMenu);

        Button btnViewMovies = findViewById(R.id.btnViewMovies);
        Button btnAddMovie = findViewById(R.id.btnAddMovie);

        btnViewMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad MoviesActivity
                Intent intent = new Intent(getContext(), MoviesActivity.class);
                getContext().startActivity(intent);
            }
        });


        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción cuando se hace clic en el botón "Agregar película"
                // Aquí puedes agregar el código para manejar el evento de clic en el botón "Agregar película"
            }
        });

        menuHelper = new MenuHelper(context);
        menuHelper.attachMenuToButton(btnMenu, R.menu.menu_main, new MenuHelper.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    // Acción cuando se selecciona "Home"
                    return true;
                } else if (itemId == R.id.menu_exit) {
                    menuHelper.showExitConfirmationDialog(new MenuHelper.OnExitConfirmedListener() {
                        @Override
                        public void onExitConfirmed() {
                            // Cerrar la aplicación después de la confirmación
                            activity.finishAffinity(); // Utiliza la referencia a la actividad actual para llamar a finishAffinity()
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }
}
