package com.example.finalnativas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalnativas.R;
import com.example.finalnativas.activities.FormMovie;
import com.example.finalnativas.database.DatabaseHelper;
import com.example.finalnativas.models.SearchedFilm;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchedFilmAdapter extends RecyclerView.Adapter<SearchedFilmAdapter.SearchFilmViewHolder> {
    private List<SearchedFilm> filmList;

    private DatabaseHelper dbHelper;

    private Context context;

    public SearchedFilmAdapter(List<SearchedFilm> filmList, Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.filmList = filmList;
        this.context = context;
    }

    // Método para limpiar la lista de películas
    public void clear() {
        filmList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de elemento de película buscada (searched_film_item.xml) como vista de elemento
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_film_item, parent, false);
        return new SearchFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilmViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Obtener la película buscada en la posición actual
        SearchedFilm film = filmList.get(position);

        // Establecer los datos de la película en los elementos de la vista de elemento (SearchFilmViewHolder)
        holder.filmTitle.setText(film.getTitle());
        holder.type.setText(film.getType());
        holder.filmYear.setText(film.getYear());

        // Cargar y mostrar la imagen utilizando Picasso
        Picasso.get().load(film.getPoster()).into(holder.filmPoster);

        // Configurar el evento de clic en el botón de agregar. Previamente a pasar al formulario
        // de rellenar los datos de la movie se realiza una comprobación de que el título de la película
        // no exista previamente en la lista del usuario. Aquí aun se maneja el objeto SearchedFilm que tiene
        // el formato que devuelve la API de OMDB
        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la película buscada en la posición actual
                SearchedFilm film = filmList.get(position);
                String filmTitle = film.getTitle();

                // Verificar si existe una película con el título seleccionado en la base de datos
                boolean movieExists = dbHelper.checkMovieExists(filmTitle);

                if (movieExists) {
                    // La película ya existe en la base de datos
                    // Mostrar un mensaje de éxito con el toast personalizado
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View layout = inflater.inflate(R.layout.toast_custom, null);

                    TextView textViewToastMessage = layout.findViewById(R.id.textViewToastMessage);
                    String toastMessage = context.getString(R.string.toast_message_existe);
                    textViewToastMessage.setText(toastMessage);

                    Toast toast = new Toast(context.getApplicationContext());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                } else {
                    // La película no existe en la base de datos
                    // Iniciar la actividad FormMovie para agregar la película
                    Intent intent = new Intent(context, FormMovie.class);
                    intent.putExtra("filmTitle", filmTitle);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devolver el número de elementos en la lista de películas buscadas
        return filmList.size();
    }

    // ViewHolder que representa cada elemento en la lista
    public static class SearchFilmViewHolder extends RecyclerView.ViewHolder {
        public TextView filmTitle;
        public TextView type;
        public TextView filmYear;
        public ImageView filmPoster;
        public AppCompatImageButton buttonAdd;

        public SearchFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            // Obtener las referencias a los elementos de la vista de elemento en el constructor de SearchFilmViewHolder
            filmTitle = itemView.findViewById(R.id.film_title);
            type = itemView.findViewById(R.id.type);
            filmYear = itemView.findViewById(R.id.film_year);
            filmPoster = itemView.findViewById(R.id.film_poster);
            buttonAdd = itemView.findViewById(R.id.btnAddToMyList);
        }
    }
}
