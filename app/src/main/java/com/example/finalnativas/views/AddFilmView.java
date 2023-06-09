package com.example.finalnativas.views;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalnativas.R;
import com.example.finalnativas.activities.MainActivity;
import com.example.finalnativas.adapters.SearchedFilmAdapter;
import com.example.finalnativas.models.SearchedFilm;
import com.example.finalnativas.utils.OMDBApiResponse;
import com.example.finalnativas.utils.OMDBApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddFilmView extends LinearLayout {
    private EditText etFilmTitle;
    private Button btnSearch;
    private RecyclerView rvFilmResults;
    private SearchedFilmAdapter filmAdapter;
    private List<SearchedFilm> filmList;

    private final String API_KEY = "984efdc6";

    public AddFilmView(Context context) {
        super(context);
        initialize(context);
    }

    public AddFilmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.activity_add_film, this, true);

        etFilmTitle = findViewById(R.id.etFilmTitle);
        btnSearch = findViewById(R.id.btnSearch);
        rvFilmResults = findViewById(R.id.rvFilmResults);

        // Set up RecyclerView and SearchFilmAdapter
        filmList = new ArrayList<>();
        filmAdapter = new SearchedFilmAdapter(filmList, context);
        rvFilmResults.setLayoutManager(new LinearLayoutManager(context));
        rvFilmResults.setAdapter(filmAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filmTitle = etFilmTitle.getText().toString();
                searchFilm(filmTitle);
            }
        });

        Button buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llevar al usuario a la MainActivity
                Context context = getContext();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }
    private void searchFilm(String filmTitle) {
        filmAdapter.clear();
        // Crea el interceptor de registro
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Retrofit", message); // Registra el mensaje en el logcat
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Establece el nivel de registro deseado

        // Crea el cliente OkHttpClient con el interceptor de registro
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        // Crea el cliente Retrofit con el cliente OkHttpClient personalizado
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDBApiService apiService = retrofit.create(OMDBApiService.class);
        Call<OMDBApiResponse> call = apiService.searchMovies(API_KEY, filmTitle);
        call.enqueue(new Callback<OMDBApiResponse>() {
            @Override
            public void onResponse(Call<OMDBApiResponse> call, Response<OMDBApiResponse> response) {
                if (response.isSuccessful()) {
                    OMDBApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getSearch() != null) {
                        List<SearchedFilm> films = new ArrayList<>();
                        for (OMDBApiResponse.SearchItem searchItem : apiResponse.getSearch()) {
                            SearchedFilm film = new SearchedFilm();
                            film.setTitle(searchItem.getTitle());
                            film.setYear(searchItem.getYear());
                            film.setType(searchItem.getType());
                            film.setPoster(searchItem.getPoster());
                            films.add(film);
                            // Hacer lo que necesites con los datos (agregarlos a una lista, mostrarlos, etc.)
                            Log.d("Film", "Title: " + film.getTitle() + ", Year: " + film.getYear() + ", Type: " + film.getType());
                        }

                        filmList.addAll(films);
                        filmAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Handle error response
                    Toast.makeText(getContext(), "Failed to get movie data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OMDBApiResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(getContext(), "Failed to connect to the server", Toast.LENGTH_SHORT).show();

                // Print the error message to the log
                Log.e("APIError", "Failed to connect to the server", t);
            }
        });
    }

}

