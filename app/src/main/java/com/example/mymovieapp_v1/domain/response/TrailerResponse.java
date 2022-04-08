package com.example.mymovieapp_v1.domain.response;

import com.example.mymovieapp_v1.domain.Trailer;

import java.util.ArrayList;

public class TrailerResponse {
    private int id;
    private ArrayList<Trailer> results;

    public TrailerResponse(int id, ArrayList<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
