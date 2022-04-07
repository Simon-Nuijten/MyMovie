package com.example.mymovieapp_v1.presentation;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<Review> reviews;
    private LayoutInflater mInflator;
    private static ClickListener clickListener;
    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    public ReviewAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
        this.mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.movie_recyclerview_review_item, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (reviews != null) {
            Review review = reviews.get(position);
            double rating = review.getAuthor_details().getRating() / 2;

            if (review.getAuthor_details().getAvatar_path() != null) {
                String url = review.getAuthor_details().getAvatar_path().substring(1);

                Glide.with(holder.reviewAuthorImage)
                        .load(url)
                        .centerCrop()
                        .into(holder.reviewAuthorImage);
            }

            holder.reviewRating.setRating((float) rating);
            holder.reviewAuthorName.setText(review.getAuthor());
            holder.reviewContent.setText(review.getContent());

        } else {
            Log.d(LOG_TAG, "No reviews found!");
        }
    }

    public void setData(List<Review> reviewList) {
        this.reviews = reviewList;
    }

    @Override
    public int getItemCount() {
        return this.reviews.size();
    }

    public Review getReviewAtPostition(int position) {
        return this.reviews.get(position);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private ImageView reviewAuthorImage;
        private RatingBar reviewRating;
        private TextView reviewAuthorName;
        private TextView reviewContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthorImage = (ImageView) itemView.findViewById(R.id.movie_review_author_image);
            reviewRating = (RatingBar) itemView.findViewById(R.id.movie_review_rating);
            reviewAuthorName = (TextView) itemView.findViewById(R.id.movie_review_author);
            reviewContent = (TextView) itemView.findViewById(R.id.movie_review_content);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ReviewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
