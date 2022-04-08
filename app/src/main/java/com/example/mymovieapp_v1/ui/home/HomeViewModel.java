package com.example.mymovieapp_v1.ui.home;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp_v1.datastorage.repository.MovieRepository;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.UpcomingMovieAdapter;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.HomeMoviesViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {
}