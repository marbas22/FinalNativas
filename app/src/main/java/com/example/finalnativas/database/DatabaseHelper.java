package com.example.finalnativas.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalnativas.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "finalapp.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla para almacenar las películas
        String createTableQuery = "CREATE TABLE finalapp (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "actor TEXT," +
                "date TEXT," +
                "city TEXT," +
                "stars INTEGER)";  // Nuevo campo para las estrellas
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si hay una actualización de la base de datos, aquí puedes realizar las modificaciones necesarias
        // Por ejemplo, si necesitas agregar una nueva columna o tabla, puedes hacerlo en este método
    }

    public void open() {
        // Obtén una instancia de escritura de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public void insertMovie(Movie movie) {
        // Obtén una instancia de escritura de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        // Crea un objeto ContentValues para almacenar los valores de las columnas
        ContentValues values = new ContentValues();
        values.put("title", movie.getTitle());
        values.put("actor", movie.getActor());
        values.put("date", movie.getDate());
        values.put("city", movie.getCity());
        values.put("stars", movie.getStars());  // Agrega el nuevo campo para las estrellas

        // Inserta la fila en la tabla de películas
        db.insert("finalapp", null, values);

        // Cierra la conexión a la base de datos
        db.close();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        // Obtén una instancia de lectura de la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Define la consulta SQL para seleccionar todas las filas de la tabla de películas
        String query = "SELECT * FROM finalapp";

        // Ejecuta la consulta y obtén un cursor que apunte a los resultados
        Cursor cursor = db.rawQuery(query, null);

        // Recorre el cursor y crea objetos Movie con los datos de cada fila
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String actor = cursor.getString(cursor.getColumnIndex("actor"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
                @SuppressLint("Range") int stars = cursor.getInt(cursor.getColumnIndex("stars"));  // Nuevo campo para las estrellas

                // Crea un objeto Movie y añádelo a la lista
                Movie movie = new Movie(id, title, actor, date, city, stars);
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return movies;
    }

    public void deleteMovieFromDatabase(Integer movieId) {
        // Obtén una instancia de escritura de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        // Define la cláusula de selección para eliminar la película por su ID
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(movieId)};

        // Elimina la fila de la tabla de películas
        db.delete("finalapp", selection, selectionArgs);

        // Cierra la conexión a la base de datos
        db.close();
    }

    public boolean checkMovieExists(String movieTitle) {
        // Obtén una instancia de lectura de la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Define la consulta SQL para seleccionar la fila con el título de la película
        String query = "SELECT * FROM finalapp WHERE title = ?";
        String[] selectionArgs = {movieTitle};

        // Ejecuta la consulta y obtén un cursor que apunte a los resultados
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Verifica si hay algún resultado en el cursor
        boolean movieExists = cursor.moveToFirst();

        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return movieExists;
    }
}

