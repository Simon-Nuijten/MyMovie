package com.example.mymovieapp_v1.ui.movieDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieapp_v1.datastorage.repository.ReviewRepository;
import com.example.mymovieapp_v1.domain.Review;

import java.util.List;

public class MovieDetailsViewModel extends ViewModel {

    private ReviewRepository reviewRepository;
    private static final String LOG_TAG = MovieDetailsViewModel.class.getSimpleName();

    private LiveData<List<Review>> reviews;

    public MovieDetailsViewModel() {
        reviewRepository = ReviewRepository.getInstance();

        reviews = reviewRepository.GetAllReviews();
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void getReviewsByMovieId(int movieId) {
        reviewRepository.getReviewResponse(movieId);
    }
}
