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
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla para almacenar las películas
        String createTableQuery = "CREATE TABLE movies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "actor TEXT," +
                "date TEXT," +
                "city TEXT)";
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

        // Inserta la fila en la tabla de películas
        db.insert("movies", null, values);

        // Cierra la conexión a la base de datos
        db.close();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        // Obtén una instancia de lectura de la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Define la consulta SQL para seleccionar todas las filas de la tabla de películas
        String query = "SELECT * FROM movies";

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

                // Crea un objeto Movie y añádelo a la lista
                Movie movie = new Movie(id, title, actor, date, city);
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
        db.delete("movies", selection, selectionArgs);

        // Cierra la conexión a la base de datos
        db.close();
    }
}
