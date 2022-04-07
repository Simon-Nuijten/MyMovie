package com.example.mymovieapp_v1.datastorage.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.Genre;
import com.example.mymovieapp_v1.domain.Language;
import com.example.mymovieapp_v1.domain.response.GenreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenreRepository {
    private MutableLiveData<List<Genre>> genres;
    private static volatile GenreRepository instance;
    private static final String LOG_TAG = GenreRepository.class.getSimpleName();

    public GenreRepository() { genres = new MutableLiveData<>(); }

    public static GenreRepository getInstance() {
        if (instance == null) {
            instance = new GenreRepository();
        }
        return instance;
    }

    public void getAllGenres() {
        Log.d(LOG_TAG, "Requesting all genres");
        new GenreAsyncTask().execute();
    }

    public LiveData<List<Genre>> getGenres() {
        return genres;
    }

    private class GenreAsyncTask extends AsyncTask<Void, Void, List<Genre>> {

        @Override
        protected List<Genre> doInBackground(Void... voids) {

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(TmdbApiService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

                Call<GenreResponse> call = tmdbApiService.getGenres();

                Response<GenreResponse> response = call.execute();

                if (response.isSuccessful()) {
                    List<Genre> results = response.body().getGenres();
                    Log.d(LOG_TAG, String.format("Got genre results: %d", results.size()));
                    return results;

                } else {
                    Log.d(LOG_TAG, String.format("Error getting Genres: %s", response.message()));
                    return null;
                }

            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Genre> results) {
            if (results != null) {
                genres.setValue(results);
                Log.d(LOG_TAG, "onPostExecuted genres found: " + results);
            } else {
                Log.d(LOG_TAG, "onPostExecuted Error happened, no genres found!");
            }
        }
    }
}
