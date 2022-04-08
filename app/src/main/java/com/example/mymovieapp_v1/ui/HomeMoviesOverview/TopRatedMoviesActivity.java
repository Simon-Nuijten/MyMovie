package com.example.mymovieapp_v1.ui.HomeMoviesOverview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.TopRatedMovieAdapter;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.HomeMoviesViewModel;

import java.util.ArrayList;

public class TopRatedMoviesActivity extends AppCompatActivity {
    private HomeMoviesViewModel mViewModel;

    private ConfigurationResponse configResponse;
    private RecyclerView listTopRatedMovies;
    private TopRatedMovieAdapter topRatedMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);

        mViewModel = new ViewModelProvider(this).get(HomeMoviesViewModel.class);
        mViewModel.setTopRatedMovies();

        listTopRatedMovies = findViewById(R.id.list_top_rated_movies);

        configResponse = (ConfigurationResponse) getIntent().getSerializableExtra("CONFIGURATION");

        topRatedMovieAdapter = new TopRatedMovieAdapter(this, new ArrayList<>(), configResponse);

        mViewModel.getTopRatedMovies().observe(this, movies -> {
            topRatedMovieAdapter.setData(movies);

            listTopRatedMovies.setAdapter(topRatedMovieAdapter);
            listTopRatedMovies.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}