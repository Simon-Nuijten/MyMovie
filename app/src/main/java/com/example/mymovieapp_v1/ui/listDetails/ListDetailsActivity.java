package com.example.mymovieapp_v1.ui.listDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.MoviesList;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.ListMoviesAdapter;
import com.example.mymovieapp_v1.ui.movies.MoviesViewModel;
import com.example.mymovieapp_v1.ui.profile.ProfileViewModel;
import com.google.gson.Gson;

public class ListDetailsActivity extends AppCompatActivity {

    private MoviesList list;

    private ListMoviesAdapter listMoviesAdapter;
    private ProfileViewModel pViewModel;

    private ImageButton removeButton;
    private ImageView movieImage;
    private TextView listTitle;
    private RecyclerView listOfMovies;
    private SharedPreferences sharedPreferences;

    private static final String LOG_TAG = ListDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);

        list = (MoviesList) getIntent().getSerializableExtra("LIST");
        pViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        pViewModel.getListDetails(list.getId());

        sharedPreferences = this.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);

        listTitle = findViewById(R.id.list_name_title);
        listOfMovies = findViewById(R.id.lists_recyclerview_movie);
        removeButton = findViewById(R.id.lists_recycler_item_movie_remove);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(String.valueOf(R.string.configResponse), "");
        ConfigurationResponse configurationResponse = gson.fromJson(json, ConfigurationResponse.class);

        listMoviesAdapter = new ListMoviesAdapter(this, null, configurationResponse);

        pViewModel.getmListDetails().observe(this, listDetails -> {
            if (listDetails != null) {
                listTitle.setText(listDetails.getName());
                listMoviesAdapter.setData(listDetails);

                listOfMovies.setAdapter(listMoviesAdapter);
                listOfMovies.setLayoutManager(new LinearLayoutManager(this));
            } else {
                Log.d(LOG_TAG, "ListDetailsActivity -> No movies found in list!");
            }
        });

        listMoviesAdapter.setOnItemClickListener(new ListMoviesAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Movie movie = listMoviesAdapter.getMovieAtPosition(position);

                pViewModel.getRemoveMovie(list.getId(), movie.getId());
                Toast.makeText(ListDetailsActivity.this, String.format("Movie %s removed", movie.getTitle()), Toast.LENGTH_SHORT).show();

                listMoviesAdapter.notifyDataSetChanged();
            }
        });
    }
}