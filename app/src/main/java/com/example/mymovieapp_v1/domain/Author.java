package com.example.mymovieapp_v1.domain;

public class Author {
    private String name;
    private String username;
    private String avatar_path;
    private double rating;

    public Author(String name, String username, String avatar_path, double rating) {
        this.name = name;
        this.username = username;
        this.avatar_path = avatar_path;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
