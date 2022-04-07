package com.example.mymovieapp_v1.datastorage.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.MoviesList;
import com.example.mymovieapp_v1.domain.body.AddMovieBody;
import com.example.mymovieapp_v1.domain.body.CreateListBody;
import com.example.mymovieapp_v1.domain.response.AddMovieResponse;
import com.example.mymovieapp_v1.domain.response.CreateListResponse;
import com.example.mymovieapp_v1.domain.response.GeneralResponse;
import com.example.mymovieapp_v1.domain.response.ListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListsRepository {
    private MutableLiveData<ListDetails> listDetails;
    private MutableLiveData<List<MoviesList>> movielists;
    private MutableLiveData<AddMovieResponse> addMovieNotation;
    private MutableLiveData<GeneralResponse> removeMovie;
    private MutableLiveData<CreateListResponse> createListResponse;
    private static volatile ListsRepository instance;
    private static final String LOG_TAG = ListsRepository.class.getSimpleName();

    public ListsRepository() {
        movielists = new MutableLiveData<>();
        listDetails = new MutableLiveData<>();
        removeMovie = new MutableLiveData<>();
        addMovieNotation = new MutableLiveData<>();
        createListResponse = new MutableLiveData<>();
    }

    public static ListsRepository getInstance() {
        if (instance == null) {
            instance = new ListsRepository();
        }
        return instance;
    }

    public LiveData<GeneralResponse> getRemoveMovie() { return this.removeMovie; }

    public LiveData<List<MoviesList>> getMovieListValues() {
        return this.movielists;
    }

    public LiveData<ListDetails> getListDetailsValues() { return this.listDetails; }

    public LiveData<AddMovieResponse> getAddMovieNotification() {
        return this.addMovieNotation;
    }

    public LiveData<CreateListResponse> getCreateListResponse() {
        return this.createListResponse;
    }

    //Get all lists
    public void getMovieLists() {

        int accountId = 12117732;

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<ListResponse> call = tmdbApiService.getAllMovieLists(accountId);

            call.enqueue(new Callback<ListResponse>() {
                @Override
                public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                    if (response.isSuccessful()) {
                        List<MoviesList> results = response.body().getResults();
                        Log.d(LOG_TAG, String.format("Got movie lists results: %d", results.size()));
                        movielists.setValue(results);
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting Reviews: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<ListResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting movie lists in: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    //Add movie to list
    //TODO: extra api-call: /list/{list_id}/item_status
    //to check if movie is already added
    public void addMovie(int listId, int movieId) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);
            Call<AddMovieResponse> call = tmdbApiService.addMovie(listId, new AddMovieBody(movieId));

            call.enqueue(new Callback<AddMovieResponse>() {
                @Override
                public void onResponse(Call<AddMovieResponse> call, Response<AddMovieResponse> response) {
                    if (response.isSuccessful()) {
                        AddMovieResponse movieResponse = response.body();
                        Log.d(LOG_TAG, String.format("Movie added: %b", movieResponse.isSuccess()));
                        addMovieNotation.setValue(movieResponse);
                    } else {
                        Log.d(LOG_TAG, String.format("Error adding movie: %s", response.raw()));
                    }
                }

                @Override
                public void onFailure(Call<AddMovieResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error adding movie to list: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    //Create new list
    public void createList(String listName, String listDesc) {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<CreateListResponse> call = tmdbApiService.createList(new CreateListBody(listName, listDesc));

            call.enqueue(new Callback<CreateListResponse>() {
                @Override
                public void onResponse(Call<CreateListResponse> call, Response<CreateListResponse> response) {
                    if (response.isSuccessful()) {
                        CreateListResponse result = response.body();
                        Log.d(LOG_TAG, String.format("List %s created: %b", listName, result.isSuccess()));

                    } else {
                        Log.d(LOG_TAG, String.format("Error creating list: %s", response.raw()));
                    }
                }

                @Override
                public void onFailure(Call<CreateListResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Exception creating list: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    //Get list details with movies
    public void getListDetails(int listId) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<ListDetails> call = tmdbApiService.getListDetails(listId);

            call.enqueue(new Callback<ListDetails>() {
                @Override
                public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
                    if (response.isSuccessful()) {
                        ListDetails result = response.body();
                        listDetails.setValue(result);
                        Log.d(LOG_TAG, String.format("Got list details from: %s", result.getName()));
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting list details: %s", response.raw()));
                    }
                }

                @Override
                public void onFailure(Call<ListDetails> call, Throwable t) {
                    Log.d(LOG_TAG, "Exception getting list details: " + t.getMessage());
                }
            });
        } catch(Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    public void deleteMovie(int listId, int movieId) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<GeneralResponse> call = tmdbApiService.deleteList(listId, new AddMovieBody(movieId));

            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    if (response.isSuccessful()) {
                        removeMovie.setValue(response.body());
                    } else {
                        Log.d(LOG_TAG, String.format("Error removing list: %s", response.raw()));

                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Exception removing movie from list: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }
}
