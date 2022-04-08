package com.example.mymovieapp_v1.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mymovieapp_v1.NowPlayingMoviesActivity;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.PopularMoviesActivity;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.TopRatedMoviesActivity;
import com.example.mymovieapp_v1.ui.HomeMoviesOverview.UpcomingMoviesActivity;
import com.google.gson.Gson;

public class home extends Fragment {

    private ImageButton nowPlaying;
    private ImageButton popularMovies;
    private ImageButton topRatedMovies;
    private ImageButton upcomingMovies;
    private HomeViewModel mViewModel;
    private SharedPreferences sharedPreferences;

    public static home newInstance() {
        return new home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

<<<<<<< HEAD
        nowPlaying.findViewById(R.id.show_now_playing);
        popularMovies.findViewById(R.id.show_popular);
        topRatedMovies.findViewById(R.id.show_top_rated_movies);
        upcomingMovies.findViewById(R.id.show_upcoming);
=======
        nowPlaying = view.findViewById(R.id.show_now_playing);
        popularMovies = view.findViewById(R.id.show_popular);
        topRatedMovies = view.findViewById(R.id.show_top_rated_movies);
        upcomingMovies = view.findViewById(R.id.show_upcoming);

        sharedPreferences = requireActivity()
                .getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);


        Gson gson = new Gson();
        String json = sharedPreferences.getString(String.valueOf(R.string.configResponse), "");
        ConfigurationResponse configurationResponse = gson.fromJson(json, ConfigurationResponse.class);

        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), NowPlayingMoviesActivity.class);
                intent.putExtra("CONFIGURATION", configurationResponse);
                startActivity(intent);
            }
        });

        popularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), PopularMoviesActivity.class);
                intent.putExtra("CONFIGURATION", configurationResponse);
                startActivity(intent);
            }
        });

        topRatedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), TopRatedMoviesActivity.class);
                intent.putExtra("CONFIGURATION", configurationResponse);
                startActivity(intent);
            }
        });

        upcomingMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), UpcomingMoviesActivity.class);
                intent.putExtra("CONFIGURATION", configurationResponse);
                startActivity(intent);
            }
        });
>>>>>>> master

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}