package com.example.finalnativas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.finalnativas.activities.AddFilmActivity;
import com.example.finalnativas.activities.FormMovie;
import com.example.finalnativas.database.DatabaseHelper;
import com.example.finalnativas.models.Movie;
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

    public void clear() {
        filmList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_film_item, parent, false);
        return new SearchFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilmViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SearchedFilm film = filmList.get(position);
        holder.filmTitle.setText(film.getTitle());
        holder.type.setText(film.getType());
        holder.filmYear.setText(film.getYear());
        // Cargar y mostrar la imagen utilizando Picasso
        Picasso.get().load(film.getPoster()).into(holder.filmPoster);

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchedFilm film = filmList.get(position);
                String filmTitle = film.getTitle();

                // Verifica si existe una película con el título seleccionado
                boolean movieExists = dbHelper.checkMovieExists(filmTitle);

                if (movieExists) {
                    // La película ya existe en la base de datos
                    // Mostrar un mensaje de éxito en el centro de la pantalla
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
                    Intent intent = new Intent(context, FormMovie.class);
                    intent.putExtra("filmTitle", filmTitle);
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public static class SearchFilmViewHolder extends RecyclerView.ViewHolder {
        public TextView filmTitle;
        public TextView type;
        public TextView filmYear;
        public ImageView filmPoster;
        public AppCompatImageButton buttonAdd;

        public SearchFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            filmTitle = itemView.findViewById(R.id.film_title);
            type = itemView.findViewById(R.id.type);
            filmYear = itemView.findViewById(R.id.film_year);
            filmPoster = itemView.findViewById(R.id.film_poster);
            buttonAdd = itemView.findViewById(R.id.btnAddToMyList);
        }
    }
}
