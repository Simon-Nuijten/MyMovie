package com.example.mymovieapp_v1.ui.HomeMoviesOverview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.PopularMoviesAdapter;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.HomeMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesActivity extends AppCompatActivity {
    private HomeMoviesViewModel mViewModel;

    private ConfigurationResponse configResponse;
    private RecyclerView listPopularMovies;
    private PopularMoviesAdapter popularMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        mViewModel = new ViewModelProvider(this).get(HomeMoviesViewModel.class);
        mViewModel.setPopularMovies();

        listPopularMovies = findViewById(R.id.list_popular_movies);

        configResponse = (ConfigurationResponse) getIntent().getSerializableExtra("CONFIGURATION");

        popularMoviesAdapter = new PopularMoviesAdapter(this, new ArrayList<>(), configResponse);

        mViewModel.getPopularMovies().observe(this, movies -> {
            popularMoviesAdapter.setData(movies);

            listPopularMovies.setAdapter(popularMoviesAdapter);
            listPopularMovies.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}