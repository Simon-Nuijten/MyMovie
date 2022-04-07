package com.example.mymovieapp_v1.domain;

public class Avatar {
    private grAvatar gravatar;
    private tmdbAvatar tmdb;

    public Avatar(grAvatar gravatar, tmdbAvatar tmdb) {
        this.gravatar = gravatar;
        this.tmdb = tmdb;
    }

    public grAvatar getGravatar() {
        return gravatar;
    }

    public void setGravatar(grAvatar gravatar) {
        this.gravatar = gravatar;
    }

    public tmdbAvatar getTmdb() {
        return tmdb;
    }

    public void setTmdb(tmdbAvatar tmdb) {
        this.tmdb = tmdb;
    }
}
