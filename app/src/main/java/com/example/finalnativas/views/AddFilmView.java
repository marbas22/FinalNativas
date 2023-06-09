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
    private EditText etFilmTitle; // Campo de texto para el título de la película
    private Button btnSearch; // Botón para buscar películas
    private RecyclerView rvFilmResults; // RecyclerView para mostrar los resultados de la búsqueda de películas
    private SearchedFilmAdapter filmAdapter; // Adaptador para mostrar los elementos en el RecyclerView
    private List<SearchedFilm> filmList; // Lista de películas buscadas

    private final String API_KEY = "984efdc6"; // Clave de API para realizar las solicitudes

    public AddFilmView(Context context) {
        super(context);
        initialize(context);
    }

    public AddFilmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        // Inflar el diseño de la vista "activity_add_film" en la vista actual
        LayoutInflater.from(context).inflate(R.layout.activity_add_film, this, true);

        // Obtener referencias a los elementos de la vista
        etFilmTitle = findViewById(R.id.etFilmTitle); // EditText para el título de la película
        btnSearch = findViewById(R.id.btnSearch); // Botón de búsqueda
        rvFilmResults = findViewById(R.id.rvFilmResults); // RecyclerView para mostrar los resultados

        // Configurar el RecyclerView y el adaptador
        filmList = new ArrayList<>(); // Inicializar la lista de películas
        filmAdapter = new SearchedFilmAdapter(filmList, context); // Crear el adaptador con la lista de películas y el contexto
        rvFilmResults.setLayoutManager(new LinearLayoutManager(context)); // Establecer el LinearLayoutManager para el RecyclerView
        rvFilmResults.setAdapter(filmAdapter); // Establecer el adaptador para el RecyclerView

        // Configurar el evento de clic del botón de búsqueda
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filmTitle = etFilmTitle.getText().toString(); // Obtener el título de la película ingresado
                searchFilm(filmTitle); // Realizar la búsqueda de la película
            }
        });

        // Configurar el evento de clic del botón "Home"
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
        filmAdapter.clear(); // Limpiar el adaptador antes de realizar una nueva búsqueda

        // Crear el interceptor de registro para mostrar los mensajes de Retrofit en el logcat
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Retrofit", message); // Registrar el mensaje en el logcat
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Establecer el nivel de registro deseado

        // Crear el cliente OkHttpClient con el interceptor de registro
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        // Crear el cliente Retrofit con el cliente OkHttpClient personalizado
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/") // Establecer la URL base de la API
                .client(httpClient) // Establecer el cliente OkHttpClient personalizado
                .addConverterFactory(GsonConverterFactory.create()) // Utilizar el convertidor Gson para convertir la respuesta JSON a objetos Java
                .build();

        // Crear una instancia de la interfaz OMDBApiService utilizando el cliente Retrofit
        OMDBApiService apiService = retrofit.create(OMDBApiService.class);

        // Crear la solicitud de búsqueda de películas utilizando la clave de API y el título de la película
        Call<OMDBApiResponse> call = apiService.searchMovies(API_KEY, filmTitle);

        // Realizar la solicitud de forma asíncrona
        call.enqueue(new Callback<OMDBApiResponse>() {
            @Override
            public void onResponse(Call<OMDBApiResponse> call, Response<OMDBApiResponse> response) {
                if (response.isSuccessful()) {
                    OMDBApiResponse apiResponse = response.body(); // Obtener la respuesta de la API

                    if (apiResponse != null && apiResponse.getSearch() != null) {
                        List<SearchedFilm> films = new ArrayList<>(); // Lista de películas obtenidas de la respuesta

                        for (OMDBApiResponse.SearchItem searchItem : apiResponse.getSearch()) {
                            SearchedFilm film = new SearchedFilm(); // Crear una nueva instancia de SearchedFilm

                            // Establecer los atributos de la película utilizando los datos de la respuesta
                            film.setTitle(searchItem.getTitle());
                            film.setYear(searchItem.getYear());
                            film.setType(searchItem.getType());
                            film.setPoster(searchItem.getPoster());

                            films.add(film); // Agregar la película a la lista
                            Log.d("Film", "Title: " + film.getTitle() + ", Year: " + film.getYear() + ", Type: " + film.getType());
                            // Imprimir en el logcat los detalles de la película (título, año y tipo)
                        }

                        filmList.addAll(films); // Agregar todas las películas a la lista de películas
                        filmAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                    }
                } else {
                    // Manejar la respuesta de error
                    Toast.makeText(getContext(), "Failed to get movie data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OMDBApiResponse> call, Throwable t) {
                // Manejar el fallo de la solicitud
                Toast.makeText(getContext(), "Failed to connect to the server", Toast.LENGTH_SHORT).show();

                // Imprimir el mensaje de error en el logcat
                Log.e("APIError", "Failed to connect to the server", t);
            }
        });
    }
}
