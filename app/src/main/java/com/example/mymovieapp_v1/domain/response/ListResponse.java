package com.example.mymovieapp_v1.domain.response;

import com.example.mymovieapp_v1.domain.MoviesList;

import java.util.ArrayList;

public class ListResponse {
    private int page;
    private ArrayList<MoviesList> results;
    private int total_pages;
    private int total_results;

    public ListResponse(int page, ArrayList<MoviesList> results, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MoviesList> getResults() {
        return results;
    }

    public void setResults(ArrayList<MoviesList> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
