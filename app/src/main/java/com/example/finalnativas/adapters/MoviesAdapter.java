package com.example.finalnativas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalnativas.R;
import com.example.finalnativas.database.DatabaseHelper;
import com.example.finalnativas.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;
    private DatabaseHelper dbHelper;

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de elemento de película (movie_item.xml) como vista de elemento
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Obtener la película en la posición actual
        Movie movie = movies.get(position);

        // Establecer los datos de la película en los elementos de la vista de elemento
        holder.movieTitle.setText(movie.getTitle());
        holder.movieActor.setText(movie.getActor());
        holder.movieDate.setText(movie.getDate());
        holder.movieCity.setText(movie.getCity());
        holder.ratingBar.setRating(movie.getStars());

        // Configurar el evento de clic en el botón de eliminar
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un diálogo de alerta para confirmar la eliminación
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String titleMessage = context.getString(R.string.exit_confirmation_title);
                builder.setTitle(titleMessage);
                String confirmMessage = context.getString(R.string.exit_confirmation_message);
                builder.setMessage(confirmMessage);

                // Agregar botón de confirmación para eliminar la película
                String deleteMessage = context.getString(R.string.exit_confirmation_confirm);
                // En caso de que se confirme la eliminación, se realice dicha acción eliminando
                // la película de la BBDD
                builder.setPositiveButton(deleteMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtener la película en la posición actual
                        Movie movie = movies.get(position);

                        // Obtener el ID de la película
                        Integer id_pelicula  = movie.getId();

                        // Eliminar la película de la base de datos
                        dbHelper.deleteMovieFromDatabase(id_pelicula);

                        // Eliminar la película de la lista y notificar al adaptador
                        movies.remove(position);
                        notifyDataSetChanged();

                        // Mostrar un mensaje de éxito con el toast personalizado
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View layout = inflater.inflate(R.layout.toast_custom, null);

                        TextView textViewToastMessage = layout.findViewById(R.id.textViewToastMessage);
                        String toastMessage = context.getString(R.string.toast_message_eliminada);
                        textViewToastMessage.setText(toastMessage);

                        Toast toast = new Toast(context.getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }
                });

                // Agregar botón de cancelar para cancelar la eliminación
                String exitMessage = context.getString(R.string.exit_confirmation_cancel);
                // En caso de no confirmar la eliminación no se hace nada.
                builder.setNegativeButton(exitMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancelar la eliminación
                        dialog.dismiss();
                    }
                });

                // Mostrar el diálogo de alerta
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        // Devolver el número de elementos en la lista de películas
        return movies.size();
    }

    // ViewHolder que representa cada elemento en la lista, es decir, cada película en la lista
    // de películas que hay en la BBDD
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle;
        public TextView movieActor;
        public TextView movieDate;
        public TextView movieCity;
        public Button buttonDelete;
        public RatingBar ratingBar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Obtener las referencias a los elementos de la vista de elemento
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieActor = itemView.findViewById(R.id.movie_actor);
            movieDate = itemView.findViewById(R.id.movie_date);
            movieCity = itemView.findViewById(R.id.movie_city);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            ratingBar = itemView.findViewById(R.id.show_rating_bar);
        }
    }
}
