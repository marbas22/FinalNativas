package com.example.finalnativas.models;

// La clase Movie es la clase de formato interno que maneja la aplicación. Es decir, tiene diferentes
// campos a los que tiene la clase SearchedFilm que es una adaptación para poder recoger los datos
// devueltos por la API realizado la consulta con la opción s (http://www.omdbapi.com/?apikey=984efdc6&s=island)
public class Movie {

    private Integer id; // Identificador único de la película
    private String title; // Título de la película
    private String actor; // Actor principal de la película
    private String date; // Fecha de lanzamiento de la película
    private String city; // Ciudad de origen de la película
    private Integer stars; // Puntuación de la película

    public Movie(String title, String actor, String date, String city, Integer stars) {
        this.title = title;
        this.actor = actor;
        this.date = date;
        this.city = city;
        this.stars = stars;
    }

    public Movie(int id, String title, String actor, String date, String city, Integer stars) {
        this.id = id;
        this.title = title;
        this.actor = actor;
        this.date = date;
        this.city = city;
        this.stars = stars;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }
}
