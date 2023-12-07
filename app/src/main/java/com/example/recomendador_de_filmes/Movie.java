package com.example.recomendador_de_filmes;

public class Movie {

    private String title;
    private String gender;
    private String release;
    private String rating;
    private String imdb;

    public Movie(String title, String gender, String release, String rating, String imdb) {
        this.title = title;
        this.gender = gender;
        this.release = release;
        this.rating = rating;
        this.imdb = imdb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }
}
