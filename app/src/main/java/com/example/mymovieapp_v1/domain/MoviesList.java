package com.example.mymovieapp_v1.domain;

import java.io.Serializable;

public class MoviesList implements Serializable {
    private String description;
    private int favorite_count;
    private int id;
    private int item_count;
    private String iso_639_1;
    private String list_type;
    private String name;
    private String poster_path;

    public MoviesList(String description, int favorite_count, int id, int item_count, String iso_639_1, String list_type, String name, String poster_path) {
        this.description = description;
        this.favorite_count = favorite_count;
        this.id = id;
        this.item_count = item_count;
        this.iso_639_1 = iso_639_1;
        this.list_type = list_type;
        this.name = name;
        this.poster_path = poster_path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
