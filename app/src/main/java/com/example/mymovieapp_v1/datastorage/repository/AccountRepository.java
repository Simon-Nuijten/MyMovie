package com.example.mymovieapp_v1.datastorage.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.AccountDetails;
import com.example.mymovieapp_v1.domain.response.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountRepository {
    private MutableLiveData<AccountDetails> Account;
    private static volatile AccountRepository instance;
    private static final String LOG_TAG = AccountRepository.class.getSimpleName();

    public AccountRepository() {
        Account = new MutableLiveData<>();
    }

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public void getRequestToken() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<AuthResponse> call = tmdbApiService.getRequestToken();

            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful()) {
                        AuthResponse authResponse = response.body();
                        Log.d(LOG_TAG, String.format("Got request token: ", authResponse.getRequest_token()));
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting request token: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting request token in: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    public LiveData<AccountDetails> getAccountDetails() {
        return this.Account;
    }

    public void getAccountDetailsTask() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TmdbApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

            Call<AccountDetails> call = tmdbApiService.getAccountInfo();

            call.enqueue(new Callback<AccountDetails>() {
                @Override
                public void onResponse(Call<AccountDetails> call, Response<AccountDetails> response) {
                    if (response.isSuccessful()) {
                        AccountDetails result = response.body();
                        Account.setValue(result);
                        Log.d(LOG_TAG, String.format("Got Account details: %s", result.getName() == null ? result.getUsername() : result.getName()));
                    } else {
                        Log.d(LOG_TAG, String.format("Error getting account details: %s", response.message()));
                    }
                }

                @Override
                public void onFailure(Call<AccountDetails> call, Throwable t) {
                    Log.d(LOG_TAG, "Error getting account details in: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }
}
