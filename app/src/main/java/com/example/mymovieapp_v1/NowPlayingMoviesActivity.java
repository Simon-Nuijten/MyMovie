package com.example.mymovieapp_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.NowPlayingMoviesAdapter;
import com.example.mymovieapp_v1.presentation.PopularMoviesAdapter;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.HomeMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingMoviesActivity extends AppCompatActivity {

    private HomeMoviesViewModel mViewModel;

    private ConfigurationResponse configResponse;
    private RecyclerView listNowPlayingMovies;
    private NowPlayingMoviesAdapter nowPlayingMoviesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_movies);

        mViewModel = new ViewModelProvider(this).get(HomeMoviesViewModel.class);
        mViewModel.setNowPlayingMovies();

        listNowPlayingMovies = findViewById(R.id.list_now_playing_movies);

        configResponse = (ConfigurationResponse) getIntent().getSerializableExtra("CONFIGURATION");

        nowPlayingMoviesAdapter = new NowPlayingMoviesAdapter(this, new ArrayList<>(), configResponse);

        mViewModel.getNowPlayingMovies().observe(this, movies -> {
            nowPlayingMoviesAdapter.setData(movies);

            listNowPlayingMovies.setAdapter(nowPlayingMoviesAdapter);
            listNowPlayingMovies.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}