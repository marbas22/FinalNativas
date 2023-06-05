package com.example.finalnativas.models;

public class Movie {

    private Integer id;
    private String title;
    private String actor;
    private String date;
    private String city;

    public Movie(String title, String actor, String date, String city) {
        this.title = title;
        this.actor = actor;
        this.date = date;
        this.city = city;
    }

    public Movie(int id, String title, String actor, String date, String city) {
        this.id = id;
        this.title = title;
        this.actor = actor;
        this.date = date;
        this.city = city;
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
}