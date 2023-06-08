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
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Movie movie = movies.get(position);

        holder.movieTitle.setText(movie.getTitle());
        holder.movieActor.setText(movie.getActor());
        holder.movieDate.setText(movie.getDate());
        holder.movieCity.setText(movie.getCity());
        holder.ratingBar.setRating(movie.getStars());

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String titleMessage = context.getString(R.string.exit_confirmation_title);
                builder.setTitle(titleMessage);
                String confirmMessage = context.getString(R.string.exit_confirmation_message);
                builder.setMessage(confirmMessage);

                // Agregar botón de confirmación
                String deleteMessage = context.getString(R.string.exit_confirmation_confirm);
                builder.setPositiveButton(deleteMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Movie movie = movies.get(position);

                        Integer id_pelicula  = movie.getId();
                        // Eliminar la película de la base de datos
                        dbHelper.deleteMovieFromDatabase(id_pelicula);

                        // Eliminar la película de la lista y notificar al adaptador
                        movies.remove(position);
                        notifyDataSetChanged();

                        // Mostrar un mensaje de éxito en el centro de la pantalla
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

                // Agregar botón de cancelar
                String exitMessage = context.getString(R.string.exit_confirmation_cancel);
                builder.setNegativeButton(exitMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancelar la eliminación
                        dialog.dismiss();
                    }
                });

                // Mostrar el diálogo
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle;
        public TextView movieActor;
        public TextView movieDate;
        public TextView movieCity;
        public Button buttonDelete;

        public RatingBar ratingBar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieActor = itemView.findViewById(R.id.movie_actor);
            movieDate = itemView.findViewById(R.id.movie_date);
            movieCity = itemView.findViewById(R.id.movie_city);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            ratingBar = itemView.findViewById(R.id.show_rating_bar);
        }
    }
}
