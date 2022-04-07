package com.example.mymovieapp_v1.presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.MoviesList;

import java.util.List;
import java.util.Objects;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {
    private List<MoviesList> movieLists;
    private Context context;
    private LayoutInflater mInflater;
    private static ClickListener clickListener;
    private static final String LOG_TAG = ListsAdapter.class.getSimpleName();

    public ListsAdapter(List<MoviesList> movieLists,
                        Context context) {
        this.movieLists = movieLists;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lists_recyclerview_item, parent, false);
        return new ListsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsViewHolder holder, int position) {
        if (!movieLists.isEmpty()) {
            MoviesList movieList = movieLists.get(position);

            holder.listTitle.setText(movieList.getName());

        } else {
            Log.d(LOG_TAG, "No movie lists found!");
        }
    }

    public void setData(List<MoviesList> movieLists) {
        this.movieLists = movieLists;
    }

    public MoviesList getMovieListAtPosition(int position) {
        return this.movieLists.get(position);
    }

    @Override
    public int getItemCount() {
        return this.movieLists.size();
    }

    class ListsViewHolder extends RecyclerView.ViewHolder {
        private TextView listTitle;
        private CardView listRecycerCardItem;
        private RecyclerView hiddenMovieList;
        private ImageButton listExpandButton;
        private LinearLayout hiddenListMovieContent;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = (TextView) itemView.findViewById(R.id.lists_recycler_item_name);
            listRecycerCardItem = (CardView) itemView.findViewById(R.id.lists_recycler_item);
            hiddenMovieList = (RecyclerView) itemView.findViewById(R.id.lists_recyclerview_movie);
            listExpandButton = (ImageButton) itemView.findViewById(R.id.lists_recycler_item_arrow_button);
            hiddenListMovieContent = (LinearLayout) itemView.findViewById(R.id.lists_recycler_item_hidden_movie_layout);
            listRecycerCardItem.setOnClickListener(view -> clickListener.onItemClick(view, getAdapterPosition()));
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ListsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
