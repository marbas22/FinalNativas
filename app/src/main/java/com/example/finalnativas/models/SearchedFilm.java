package com.example.finalnativas.models;

// Maneja el formato devuelto por las peticiones : (http://www.omdbapi.com/?apikey=984efdc6&s=island)
// Este formato se presenta en el SearchedFilmAdapter para mostrar las películas devueltas por las
// consultas.
public class SearchedFilm {
    private String title; // Título de la película buscada
    private String year; // Año de la película buscada
    private String type; // Tipo de la película buscada
    private String poster; // URL del póster de la película buscada

    public SearchedFilm(String title, String year, String type, String poster) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }

    public SearchedFilm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
