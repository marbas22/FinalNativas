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
                "stars INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Por defecto se deja este método sin realizar ninguna acción.
    }

    public void open() {
        // Se obtiene una instancia de escritura de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public void insertMovie(Movie movie) {
        // Se obtiene una instancia de escritura de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        // Se crea un objeto ContentValues para almacenar los valores de las columnas
        ContentValues values = new ContentValues();
        values.put("title", movie.getTitle());
        values.put("actor", movie.getActor());
        values.put("date", movie.getDate());
        values.put("city", movie.getCity());
        values.put("stars", movie.getStars());

        // Se inserta la fila en la tabla de películas
        db.insert("finalapp", null, values);

        // Se cierra la conexión a la base de datos
        db.close();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        // Se obtiene una instancia de lectura de la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Se define la consulta SQL para seleccionar todas las filas de la tabla de películas
        String query = "SELECT * FROM finalapp";

        // Se ejecuta la consulta y se obtiene un cursor que apunte a los resultados
        Cursor cursor = db.rawQuery(query, null);

        // Se recorre el cursor y se crea objetos Movie con los datos de cada fila
        if (cursor.moveToFirst()) {
            do {
                // Se obtienen los valores de las columnas para cada fila
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String actor = cursor.getString(cursor.getColumnIndex("actor"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
                @SuppressLint("Range") int stars = cursor.getInt(cursor.getColumnIndex("stars"));  // Nuevo campo para las estrellas

                // Se crea un objeto Movie y añádelo a la lista
                Movie movie = new Movie(id, title, actor, date, city, stars);
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // Se cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return movies;
    }

    public void deleteMovieFromDatabase(Integer movieId) {
        // Se obtiene una instancia de escritura de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        // Se define la cláusula de selección para eliminar la película por su ID
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(movieId)};

        // Se elimina la fila de la tabla de películas
        db.delete("finalapp", selection, selectionArgs);

        // Se cierra la conexión a la base de datos
        db.close();
    }

    public boolean checkMovieExists(String movieTitle) {
        // Se obtiene una instancia de lectura de la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Se define la consulta SQL para seleccionar la fila con el título de la película
        String query = "SELECT * FROM finalapp WHERE title = ?";
        String[] selectionArgs = {movieTitle};

        // Se ejecuta la consulta y obtén un cursor que apunte a los resultados
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Se verifica si hay algún resultado en el cursor
        boolean movieExists = cursor.moveToFirst();

        // Se cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return movieExists;
    }
}
