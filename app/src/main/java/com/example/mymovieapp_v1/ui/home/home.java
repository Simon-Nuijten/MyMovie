package com.example.mymovieapp_v1.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mymovieapp_v1.R;

public class home extends Fragment {

    private ImageButton nowPlaying;
    private ImageButton popularMovies;
    private ImageButton topRatedMovies;
    private ImageButton upcomingMovies;
    private HomeViewModel mViewModel;

    public static home newInstance() {
        return new home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        nowPlaying.findViewById(R.id.show_now_playing);
        popularMovies.findViewById(R.id.show_popular);
        topRatedMovies.findViewById(R.id.show_top_rated_movies);
        upcomingMovies.findViewById(R.id.show_upcoming);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}