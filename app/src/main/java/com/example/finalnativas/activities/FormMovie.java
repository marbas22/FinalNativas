package com.example.finalnativas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalnativas.R;
import com.example.finalnativas.database.DatabaseHelper;
import com.example.finalnativas.models.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FormMovie extends AppCompatActivity {

    private EditText actorEditText;
    private EditText fechaEditText;
    private EditText ciudadEditText;
    private Button submitButton;
    private Button backButton;
    private DatabaseHelper databaseHelper;
    private String selectedDate;
    private Integer stars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_movie);

        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("filmTitle");
        TextView movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieTitleTextView.setText(movieTitle);

        // Obtener referencias a los elementos de la interfaz de usuario (archivo .xml)
        actorEditText = findViewById(R.id.actorEditText);
        fechaEditText = findViewById(R.id.fechaEditText);
        ciudadEditText = findViewById(R.id.ciudadEditText);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);

        // Asignar un listener al campo de fecha para mostrar el diálogo de selección de fecha
        // cuando el usuario hace click en el campo de asignar la fecha de visionado de película
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Asignar un listener al botón de retroceso para finalizar la actividad actual, es decir, para
        // volver hacia atrás (botón back)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Asignar un listener al RatingBar para capturar la calificación seleccionada. Cuando el usuario
        // realice cambios sobre el número de estrellas seleccionados se actualizará.
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Obtener el valor de la calificación seleccionada y almacenarlo
                stars = (int) rating;
                Log.d("Formulario", "Calificación: " + stars);
            }
        });

        // Asignar un listener al botón de envío para procesar los datos del formulario
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se recogen los valores del formulario (layout .xml)
                String actor = actorEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();
                String ciudad = ciudadEditText.getText().toString();

                // Se verifica que los campos no estén vacíos
                if (actor.isEmpty() || fecha.isEmpty() || ciudad.isEmpty()) {
                    // Se muestra un mensaje utilizando el toast personalizado
                    LayoutInflater inflater = LayoutInflater.from(FormMovie.this);
                    View layout = inflater.inflate(R.layout.toast_custom, null);

                    TextView textViewToastMessage = layout.findViewById(R.id.textViewToastMessage);
                    String toastMessage = FormMovie.this.getString(R.string.toast_message_campos_vacios);
                    textViewToastMessage.setText(toastMessage);

                    Toast toast = new Toast(FormMovie.this.getApplicationContext());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                } else {
                    // Verificar el formato de fecha (dd/MM/yyyy)
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    dateFormat.setLenient(false);
                    try {
                        dateFormat.parse(fecha); // Intentar analizar la fecha en el formato especificado
                        // La fecha tiene el formato válido

                        // Se crea un objeto de la clase Movie (formato interno que manejamos) con los campos
                        // recuperados del formulario
                        Movie movie = new Movie(movieTitle, actor, fecha, ciudad, stars);
                        databaseHelper = new DatabaseHelper(FormMovie.this);
                        databaseHelper.insertMovie(movie);

                        // Mostrar un mensaje de éxito utilizando el toast personalizado
                        LayoutInflater inflater = LayoutInflater.from(FormMovie.this);
                        View layout = inflater.inflate(R.layout.toast_custom, null);

                        TextView textViewToastMessage = layout.findViewById(R.id.textViewToastMessage);
                        String toastMessage = FormMovie.this.getString(R.string.toast_message_pelicula_insert);
                        textViewToastMessage.setText(toastMessage);

                        Toast toast = new Toast(FormMovie.this.getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                        // Se mueve al usuario a la actividad-vista de las películas vistas por el usuario
                        Intent intent = new Intent(FormMovie.this, MoviesActivity.class);
                        startActivity(intent);

                    } catch (ParseException e) {
                        // La fecha no tiene el formato válido
                        // Mostrar mensaje de error utilizando el toast personalizado
                        LayoutInflater inflater = LayoutInflater.from(FormMovie.this);
                        View layout = inflater.inflate(R.layout.toast_custom, null);

                        TextView textViewToastMessage = layout.findViewById(R.id.textViewToastMessage);
                        String toastMessage = FormMovie.this.getString(R.string.toast_message_formato_fecha);
                        textViewToastMessage.setText(toastMessage);

                        Toast toast = new Toast(FormMovie.this.getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }
                }
            }
        });
    }

    // Método para mostrar el diálogo de selección de fecha
    private void showDatePickerDialog() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog y configurarlo para mostrar el diálogo
        DatePickerDialog datePickerDialog = new DatePickerDialog(FormMovie.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Actualizar el campo fechaEditText con la fecha seleccionada
                        String selectedMonth = String.format(Locale.getDefault(), "%02d", month + 1);
                        String selectedDay = String.format(Locale.getDefault(), "%02d", dayOfMonth);
                        selectedDate = selectedDay + "/" + selectedMonth + "/" + year;
                        fechaEditText.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        // Mostrar el diálogo de selección de fecha
        datePickerDialog.show();
    }
}
