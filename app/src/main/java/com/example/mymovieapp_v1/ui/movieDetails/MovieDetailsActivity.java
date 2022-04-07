package com.example.mymovieapp_v1.ui.movieDetails;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymovieapp_v1.MainActivity;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.MoviesList;
import com.example.mymovieapp_v1.domain.Review;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.MovieDto;
import com.example.mymovieapp_v1.presentation.ReviewAdapter;
import com.example.mymovieapp_v1.ui.lists.MovieListViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private Button movieReviewAddReview;
    private MovieDto movie;

    private ReviewAdapter reviewAdapter;
    private ImageView movieDetailsImage;
    private TextView movieDetailsTitle;
    private TextView movieDetailsViews;
    private TextView movieDetailsGenres;
    private RatingBar movieDetailsRating;
    private TextView movieDetailsDescription;
    private TextView movieDetailsReleaseDate;
    private SharedPreferences sharedPreferences;
    private RecyclerView movieDetailsReviewRecyclerView;
    private MovieListViewModel movieListViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;
    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        sharedPreferences = this.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);

        movie = (MovieDto) getIntent().getSerializableExtra("MOVIE");
        movieDetailsViewModel.getReviewsByMovieId(movie.getId());
        movieListViewModel.getMovieListsTask();

        //TODO: On this button clicked add a review to current movie
        movieReviewAddReview = findViewById(R.id.movie_review_addreview);

        movieDetailsImage = findViewById(R.id.movie_details_image);
        movieDetailsTitle = findViewById(R.id.movie_details_title);
        movieDetailsViews = findViewById(R.id.movie_details_views);
        movieDetailsGenres = findViewById(R.id.movie_details_genres);
        movieDetailsRating = findViewById(R.id.movie_details_rating);
        movieDetailsDescription = findViewById(R.id.movie_details_description);
        movieDetailsReleaseDate = findViewById(R.id.movie_details_release_date);
        movieDetailsReviewRecyclerView = findViewById(R.id.movie_details_reviews);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(String.valueOf(R.string.configResponse), "");
        ConfigurationResponse configurationResponse = gson.fromJson(json, ConfigurationResponse.class);

        String imageUrl = configurationResponse.getImages().getSecure_base_url()
                + configurationResponse.getImages().getPoster_sizes().get(1)
                + movie.getPoster_path();

        Glide.with(movieDetailsImage)
                .load(imageUrl)
                .centerCrop()
                .into(movieDetailsImage);

        StringBuilder genres = new StringBuilder();
        for (String genre : movie.getGenre_ids()) {
            genres.append("\n");
            genres.append(" - ");
            genres.append(genre);
        }

        double rating = movie.getVote_average() / 2;

        movieDetailsTitle.setText(movie.getTitle());
        movieDetailsViews.append("\n" + movie.getPopularity().toString() + " viewed");
        movieDetailsGenres.append(genres);
        movieDetailsRating.setRating(((float) rating));
        movieDetailsDescription.setText(movie.getOverview());
        movieDetailsReleaseDate.setText("Released: " + movie.getRelease_date());

        reviewAdapter = new ReviewAdapter(new ArrayList<>(), this);

        movieDetailsViewModel.getReviews().observe(this, reviews -> {
            if (reviews != null) {
                reviewAdapter.setData(reviews);

                movieDetailsReviewRecyclerView.setAdapter(reviewAdapter);
                movieDetailsReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            } else {
                Log.d(LOG_TAG, "MovieDetails -> No reviews found!");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_details_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.add_to_list:

                movieListViewModel.getMovieList().observe(this, moviesLists -> {

                    String[] movieNames = new String[moviesLists.size()];

                    for (int i = 0; i < moviesLists.size(); i++) {
                        movieNames[i] = moviesLists.get(i).getName();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Add movie to a list")
                            .setItems(movieNames,
                                    (DialogInterface.OnClickListener) (dialogInterface, i) -> {

                                        movieListViewModel.AddMovieToList(moviesLists.get(i).getId(), movie.getId());

                                        movieListViewModel.getAddMovieResponse().observe(this, addMovieResponse -> {
                                            if (addMovieResponse.isSuccess()) {
                                                Toast.makeText(this, String.format("%s added to list: %s", movie.getTitle(), moviesLists.get(i).getName()), Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(this, String.format("Error adding %s to %s", movie.getTitle(), moviesLists.get(i).getName()), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    });
                    builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> { });
                    builder.setPositiveButton(android.R.string.ok, (dialog, id) -> { });
                    builder.show();
                });


                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}