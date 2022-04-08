package com.example.mymovieapp_v1.datastorage.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.Genre;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.response.GenreResponse;
import com.example.mymovieapp_v1.domain.response.LatestResponse;
import com.example.mymovieapp_v1.domain.response.MovieResponse;
import com.example.mymovieapp_v1.presentation.MovieDto;
import com.example.mymovieapp_v1.ui.movies.movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private MutableLiveData<List<MovieDto>> movies;
    private MutableLiveData<List<Movie>> topRatedMovies;
    private MutableLiveData<List<Movie>> nowPlayingMovies;
    private MutableLiveData<List<Movie>> upcomingMovies;
    private MutableLiveData<List<Movie>> popularMovies;
    private static volatile MovieRepository instance;
    private static final String LOG_TAG = MovieRepository.class.getSimpleName();

    public MovieRepository() {
        movies = new MutableLiveData<>();
        popularMovies = new MutableLiveData<>();
        topRatedMovies = new MutableLiveData<>();
        upcomingMovies = new MutableLiveData<>();
        nowPlayingMovies = new MutableLiveData<>();
    }

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public void getAllMovies() {
        Log.d(LOG_TAG, "Requesting all movies");
        new MoviesAsyncTask().execute();
    }

    public LiveData<List<MovieDto>> getMovies() {
        return this.movies;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {return this.topRatedMovies;}
    public LiveData<List<Movie>> getNowPlayingMovies() {return this.nowPlayingMovies;}
    public LiveData<List<Movie>> getUpcomingMovies() {return this.upcomingMovies;}
    public LiveData<List<Movie>> getPopularMovies() {return this.popularMovies;}

    public void getPopular() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<MovieResponse> call = tmdbApiService.getPopularMovies();

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        popularMovies.setValue(response.body().getResults());
                        Log.d(LOG_TAG, String.format("Getting popular movies: %o", response.body().getResults().size()));
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting popular movies: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting popular movies: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    public void getNowPlaying() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<LatestResponse> call = tmdbApiService.getNowPlayingMovies();

            call.enqueue(new Callback<LatestResponse>() {
                @Override
                public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                    if (response.isSuccessful()) {
                        nowPlayingMovies.setValue(response.body().getResults());
                        Log.d(LOG_TAG, String.format("Getting NowPlayingMovies: %o", response.body().getResults().size()));
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting NowPlayingMovies: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<LatestResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting now playing movies: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    public void getTopRated() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<MovieResponse> call = tmdbApiService.getTopRatedMovies();

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        topRatedMovies.setValue(response.body().getResults());
                        Log.d(LOG_TAG, String.format("Getting top rated: %o", response.body().getResults().size()));
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting top rated: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting top rated movies: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    public void getUpcoming() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<LatestResponse> call = tmdbApiService.getUpcomingMovies();

            call.enqueue(new Callback<LatestResponse>() {
                @Override
                public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                    if (response.isSuccessful()) {
                        upcomingMovies.setValue(response.body().getResults());
                        Log.d(LOG_TAG, String.format("Getting upcoming: %o", response.body().getResults().size()));

                    } else {
                        Log.d(LOG_TAG, String.format("Error getting upcoming: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<LatestResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting upcoming movies: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    private class MoviesAsyncTask extends AsyncTask<Void, Void, List<MovieDto>> {

        @Override
        protected List<MovieDto> doInBackground(Void... voids) {

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(TmdbApiService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

                Call<MovieResponse> call = tmdbApiService.getPopularMovies();
                Call<GenreResponse> call1 = tmdbApiService.getGenres();

                Response<MovieResponse> response = call.execute();
                Response<GenreResponse> response1 = call1.execute();

                if (response.isSuccessful() && response1.isSuccessful()) {
                    List<Movie> results = response.body().getResults();
                    List<Genre> results1 = response1.body().getGenres();
                    List<MovieDto> movieDtoResult = new ArrayList<>();
                    for (Movie movie : results) {
                        ArrayList<String> genreNames = new ArrayList<>();

                        for (int i = 0; i < movie.getGenre_ids().size(); i++) {
                            for (int j = 0; j < results1.size(); j++) {
                                if (movie.getGenre_ids().get(i) == results1.get(j).getId()) {
                                    genreNames.add(results1.get(j).getName());
                                }
                            }
                        }

                        movieDtoResult.add(
                                new MovieDto(
                                        movie.getAdult(),
                                        movie.getBackdrop_path(),
                                        genreNames,
                                        movie.getId(),
                                        movie.getOriginal_language(),
                                        movie.getOriginal_title(),
                                        movie.getOverview(),
                                        movie.getPopularity(),
                                        movie.getPoster_path(),
                                        movie.getRelease_date(),
                                        movie.getTitle(),
                                        movie.getVideo(),
                                        movie.getVote_average(),
                                        movie.getVote_count()
                                )
                        );
                    }


                    Log.d(LOG_TAG, String.format("Got movie results: %d", results.size()));
                    Log.d(LOG_TAG, String.format("Got genre results: %d", results1.size()));
                    return movieDtoResult;
                } else {
                    Log.d(LOG_TAG, String.format("Error getting movies: %s or genres: %s", response.message(), response1.message()));
                    return null;
                }

            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<MovieDto> results) {
            if (results != null) {
                movies.setValue(results);
                Log.d(LOG_TAG, "onPostExecuted movies found: " + results);
            } else {
                Log.d(LOG_TAG, "onPostExecuted Error happened, no movies found!");
            }
        }
    }
}
