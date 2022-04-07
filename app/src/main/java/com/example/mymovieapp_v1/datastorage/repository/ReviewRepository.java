package com.example.mymovieapp_v1.datastorage.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.Review;
import com.example.mymovieapp_v1.domain.response.ReviewResponse;
import com.example.mymovieapp_v1.presentation.MovieDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewRepository {
    private MutableLiveData<List<Review>> reviews;
    private static volatile ReviewRepository instance;
    private static final String LOG_TAG = ReviewRepository.class.getSimpleName();

    public ReviewRepository() { reviews = new MutableLiveData<>(); }

    public static ReviewRepository getInstance() {
        if (instance == null) {
            instance = new ReviewRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Review>> GetAllReviews() {
        return reviews;
    }

    public void getReviewResponse(int movieId) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<ReviewResponse> call = tmdbApiService.getReviews(movieId);

            call.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    if (response.isSuccessful()) {
                        List<Review> results = response.body().getResults();
                        Log.d(LOG_TAG, String.format("Got review results: %d", results.size()));
                        reviews.setValue(results);
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting Reviews: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting reviews in: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }
}
