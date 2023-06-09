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

    private final String API_KEY = "984efdc6";

    private Integer stars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_movie);

        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("filmTitle");
        TextView movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieTitleTextView.setText(movieTitle);

        actorEditText = findViewById(R.id.actorEditText);
        fechaEditText = findViewById(R.id.fechaEditText);
        ciudadEditText = findViewById(R.id.ciudadEditText);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);


        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Dentro del método onCreate de la clase FormMovie

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // rating contiene el valor de la calificación seleccionada
                stars = (int) rating;
                // Puedes utilizar el valor de la calificación como desees, por ejemplo, guardarlo en una variable o realizar alguna acción adicional.
                Log.d("Formulario", "Calificación: " + stars);
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actor = actorEditText.getText().toString();
                String fecha = fechaEditText.getText().toString();
                String ciudad = ciudadEditText.getText().toString();

                // Verificar si los campos están vacíos
                if (actor.isEmpty() || fecha.isEmpty() || ciudad.isEmpty()) {
                    // Mostrar mensaje de error en el centro de la pantalla
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
                    dateFormat.setLenient(false); // Evita la interpretación flexible de la fecha

                    try {
                        dateFormat.parse(fecha); // Intenta analizar la fecha en el formato especificado
                        // La fecha tiene el formato válido
                        // Continúa con el procesamiento del formulario

                        Log.d("Formulario", "Actor: " + actor);
                        Log.d("Formulario", "Fecha: " + fecha);
                        Log.d("Formulario", "Ciudad: " + ciudad);

                        // Realizar las acciones adicionales con los valores del formulario

                        // Por ejemplo, puedes crear un intent para enviar los datos a otra actividad
                        Movie movie = new Movie(movieTitle, actor, fecha, ciudad,stars);
                        databaseHelper = new DatabaseHelper(FormMovie.this);
                        databaseHelper.insertMovie(movie);
                        // La película ya existe en la base de datos
                        // Mostrar un mensaje de éxito en el centro de la pantalla
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

                        Intent intent = new Intent(FormMovie.this, MoviesActivity.class);
                        startActivity(intent);
                    } catch (ParseException e) {
                        // La fecha no tiene el formato válido
                        // Mostrar mensaje de error en el centro de la pantalla
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

    private void showDatePickerDialog() {
        // Obtén la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea el DatePickerDialog y configúralo para mostrar el diálogo
        DatePickerDialog datePickerDialog = new DatePickerDialog(FormMovie.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Actualiza el campo fechaEditText con la fecha seleccionada
                        String selectedMonth = String.format(Locale.getDefault(), "%02d", month + 1);
                        String selectedDay = String.format(Locale.getDefault(), "%02d", dayOfMonth);
                        selectedDate = selectedDay + "/" + selectedMonth + "/" + year;
                        fechaEditText.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        // Muestra el diálogo de selección de fecha
        datePickerDialog.show();
    }


}
