package com.example.mymovieapp_v1.presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.google.gson.Gson;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ListMoviesViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private ListDetails listDetails;
    private ConfigurationResponse configurationResponse;
    private static ListMoviesAdapter.ClickListener clickListener;
    private static final String LOG_TAG = ListMoviesAdapter.class.getSimpleName();

    public ListMoviesAdapter(Context context,
                             ListDetails listDetails,
                             ConfigurationResponse configurationResponse) {
        this.listDetails = listDetails;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.configurationResponse = configurationResponse;
    }

    @NonNull
    @Override
    public ListMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lists_recyclerview_movie_item, parent, false);
        return new ListMoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMoviesViewHolder holder, int position) {
        if (listDetails != null) {
            Movie listItem = listDetails.getItems().get(position);

            String imageUrl = configurationResponse.getImages().getSecure_base_url()
                    + configurationResponse.getImages().getPoster_sizes().get(1)
                    + listItem.getPoster_path();

            holder.movieTitle.setText(listItem.getTitle());
            Glide.with(holder.movieImage)
                    .load(imageUrl)
                    .centerCrop()
                    .into(holder.movieImage);
        } else {
            Log.d(LOG_TAG, "No list details found!");
        }
    }

    public void setData(ListDetails listDetails1) {
        this.listDetails = listDetails1;
    }

    @Override
    public int getItemCount() {
        return listDetails.getItem_count();
    }

    public Movie getMovieAtPosition(int position) { return listDetails.getItems().get(position); }

    class ListMoviesViewHolder extends RecyclerView.ViewHolder {

        private ImageButton removeMovie;
        private ImageView movieImage;
        private TextView movieTitle;

        public ListMoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.lists_recycler_item_movie_image);
            movieTitle = itemView.findViewById(R.id.lists_recycler_item_movie_title);
            removeMovie = itemView.findViewById(R.id.lists_recycler_item_movie_remove);
            removeMovie.setOnClickListener(view -> clickListener.onItemClick(view, getAdapterPosition()));
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ListMoviesAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
