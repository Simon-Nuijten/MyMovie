package com.example.mymovieapp_v1.ui.lists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieapp_v1.datastorage.repository.ListsRepository;
import com.example.mymovieapp_v1.domain.MoviesList;
import com.example.mymovieapp_v1.domain.response.AddMovieResponse;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private ListsRepository listsRepository;

    private LiveData<List<MoviesList>> movieLists;
    private LiveData<AddMovieResponse> addMovieResponse;

    public MovieListViewModel() {
        listsRepository = ListsRepository.getInstance();
        movieLists = listsRepository.getMovieListValues();
        addMovieResponse = listsRepository.getAddMovieNotification();
    }

    public LiveData<List<MoviesList>> getMovieList() {
        return movieLists;
    }
    public LiveData<AddMovieResponse> getAddMovieResponse() { return addMovieResponse; }

    public void getMovieListsTask() {
        listsRepository.getMovieLists();
    }
    public void AddMovieToList(int listId, int movieId) { listsRepository.addMovie(listId, movieId); }
}
