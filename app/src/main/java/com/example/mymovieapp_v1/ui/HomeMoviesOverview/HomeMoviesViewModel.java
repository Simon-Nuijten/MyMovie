package com.example.mymovieapp_v1.ui.HomeMoviesOverview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieapp_v1.datastorage.repository.MovieRepository;
import com.example.mymovieapp_v1.domain.Movie;

import java.util.List;

public class HomeMoviesViewModel extends ViewModel {

    private MovieRepository movieRepository;

    private LiveData<List<Movie>> nowPlayingMovies;
    private LiveData<List<Movie>> popularMovies;
    private LiveData<List<Movie>> topRatedMovies;
    private LiveData<List<Movie>> upcomingMovies;

    public HomeMoviesViewModel() {
        movieRepository = MovieRepository.getInstance();

        nowPlayingMovies = movieRepository.getNowPlayingMovies();
        popularMovies = movieRepository.getPopularMovies();
        topRatedMovies = movieRepository.getTopRatedMovies();
        upcomingMovies = movieRepository.getUpcomingMovies();
    }


    public LiveData<List<Movie>> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public LiveData<List<Movie>> getUpcomingMovies() {
        return upcomingMovies;
    }

    public void setNowPlayingMovies() { movieRepository.getNowPlaying(); }
    public void setPopularMovies() { movieRepository.getPopular(); }
    public void setTopRatedMovies() { movieRepository.getTopRated(); }
    public void setUpcomingMovies() { movieRepository.getUpcoming(); }

}
