package com.example.mymovieapp_v1.domain.response;

import com.example.mymovieapp_v1.domain.Genre;

import java.util.ArrayList;

public class GenreResponse {
    private ArrayList<Genre> genres;

    public GenreResponse(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
