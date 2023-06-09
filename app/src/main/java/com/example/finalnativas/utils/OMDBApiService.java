package com.example.finalnativas.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDBApiService {

    // Al introducir la s en la búsqueda de películas nos va a devolver una lista de las coincidencias. Hay
    // que tener cuidado entonces ya que los campos devueltos por una petición con la opción s no son los mismos
    // que devuelve una petición con la opción t
    @GET("/")
    Call<OMDBApiResponse> searchMovies(@Query("apikey") String apiKey, @Query("s") String filmTitle);

}
