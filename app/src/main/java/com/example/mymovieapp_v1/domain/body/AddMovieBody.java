package com.example.mymovieapp_v1.domain.body;

public class AddMovieBody {
    private int media_id;

    public AddMovieBody(int movie_id) {
        this.media_id = movie_id;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }
}
