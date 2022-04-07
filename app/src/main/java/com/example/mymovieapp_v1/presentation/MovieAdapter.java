package com.example.mymovieapp_v1.presentation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.Genre;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieDto> movies;
    private List<Genre> genres;
    private LayoutInflater mInflater;
    private static ClickListener clickListener;
    private ConfigurationResponse configurationResponse;
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(List<MovieDto> movies,
                        List<Genre> genres,
                        Context context,
                        ConfigurationResponse configurationResponse) {
        this.movies = movies;
        this.genres = genres;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.configurationResponse = configurationResponse;
    }

    public void filterList(ArrayList<MovieDto> filteredMovies) {
        movies = filteredMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movie_recylcerview_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (movies != null) {
            MovieDto movie = movies.get(position);
            String imageUrl =
                    configurationResponse.getImages().getSecure_base_url()
                            + configurationResponse.getImages().getPoster_sizes().get(1)
                            + movie.getPoster_path();

            Glide.with(holder.movieImage)
                    .load(imageUrl)
                    .centerCrop()
                    .into(holder.movieImage);
            holder.movieTitle.setText(movie.getTitle());
            holder.movieDesc.setText(movie.getOverview());
            holder.movieGenre.setText(convertGenres(movie.getGenre_ids()));
            holder.movieRating.setText(movie.getVote_average().toString());
        } else {
            Log.d(LOG_TAG, "No movies found!");
        }
    }

    public void setData(List<MovieDto> movies) {
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public MovieDto getMovieAtPosition(int position) {
        return this.movies.get(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private CardView movieCard;
        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieDesc;
        private TextView movieGenre;
        private TextView movieRating;

        private MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieCard = (CardView) itemView;
            movieImage = (ImageView) itemView.findViewById(R.id.movie_list_item_image);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_list_item_title);
            movieDesc = (TextView) itemView.findViewById(R.id.movie_list_item_description);
            movieGenre = (TextView) itemView.findViewById(R.id.move_list_item_genre);
            movieRating = (TextView) itemView.findViewById(R.id.movie_list_item_rating);
            itemView.setOnClickListener(view -> clickListener.onItemClick(view, getAdapterPosition()));
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MovieAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

    public String convertGenres(List<String> movieGenres) {
        StringBuilder result = new StringBuilder();

        for (String genre : movieGenres) {
            result.append(genre);
            result.append(", ");
        }

        return result.toString();
    }
}
