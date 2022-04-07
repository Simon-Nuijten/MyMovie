package com.example.mymovieapp_v1.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieapp_v1.datastorage.repository.GenreRepository;
import com.example.mymovieapp_v1.datastorage.repository.MovieRepository;
import com.example.mymovieapp_v1.domain.Genre;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.presentation.MovieDto;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private static final String LOG_TAG = MoviesViewModel.class.getSimpleName();

    private LiveData<List<MovieDto>> movies;
    private LiveData<List<Genre>> genres;

    public MoviesViewModel() {
        this.movieRepository = MovieRepository.getInstance();
        this.genreRepository = GenreRepository.getInstance();
        movies = movieRepository.getMovies();
        genres = genreRepository.getGenres();
    }

    public LiveData<List<Genre>> getmGenres() {
        return genres;
    }

    public LiveData<List<MovieDto>> getmMovies() {
        return movies;
    }

    public void getGenres() { this.genreRepository.getAllGenres(); }
    public void getMovies() {
        this.movieRepository.getAllMovies();
    }
}