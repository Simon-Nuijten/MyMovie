package com.example.mymovieapp_v1.presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;

import java.util.List;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<Movie> upcomingMovies;
    private static ClickListener clickListener;
    private ConfigurationResponse configurationResponse;
    public static final String LOG_TAG = UpcomingMovieAdapter.class.getSimpleName();

    public UpcomingMovieAdapter(Context context,
                                List<Movie> upcomingMovies,
                                ConfigurationResponse configurationResponse) {
        this.context = context;
        this.upcomingMovies = upcomingMovies;
        this.mInflater = LayoutInflater.from(context);
        this.configurationResponse = configurationResponse;
    }

    @NonNull
    @Override
    public UpcomingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movie_recylcerview_item, parent, false);
        return new UpcomingMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMovieViewHolder holder, int position) {
        if (!upcomingMovies.isEmpty()) {

            Movie movie = upcomingMovies.get(position);

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


        } else {
            Log.d(LOG_TAG, "No movies found!");
        }
    }

    @Override
    public int getItemCount() {
        return upcomingMovies.size();
    }

    public void setData(List<Movie> upcomingMovies) { this.upcomingMovies = upcomingMovies; }

    class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieDesc;

        public UpcomingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movie_list_item_image);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_list_item_title);
            movieDesc = (TextView) itemView.findViewById(R.id.movie_list_item_description);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        UpcomingMovieAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
