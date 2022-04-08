package com.example.mymovieapp_v1.ui.HomeMoviesOverview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.UpcomingMovieAdapter;
import com.example.mymovieapp_v1.ui.movies.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMoviesActivity extends AppCompatActivity {

    private HomeMoviesViewModel mViewModel;

    private ConfigurationResponse configResponse;
    private RecyclerView listUpcomingMovies;
    private UpcomingMovieAdapter upcomingMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_movies);

        mViewModel = new ViewModelProvider(this).get(HomeMoviesViewModel.class);
        mViewModel.setUpcomingMovies();

        listUpcomingMovies = findViewById(R.id.list_upcoming_movies);

        configResponse = (ConfigurationResponse) getIntent().getSerializableExtra("CONFIGURATION");

        upcomingMovieAdapter = new UpcomingMovieAdapter(this, new ArrayList<>(), configResponse);

        mViewModel.getUpcomingMovies().observe(this, movies -> {
            upcomingMovieAdapter.setData(movies);

            listUpcomingMovies.setAdapter(upcomingMovieAdapter);
            listUpcomingMovies.setLayoutManager(new LinearLayoutManager(this));
        });

    }
}