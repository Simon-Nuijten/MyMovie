package com.example.mymovieapp_v1.datastorage.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigurationRepository {
    private static volatile ConfigurationRepository instance;
    private MutableLiveData<ConfigurationResponse> configuration;
    private static final String LOG_TAG = ConfigurationRepository.class.getSimpleName();

    public ConfigurationRepository() {
        configuration = new MutableLiveData<>();
    }

    public static ConfigurationRepository getInstance() {
        if (instance == null) {
            instance = new ConfigurationRepository();
        }
        return instance;
    }

    public void ExecuteConfigurationTask() {
        Log.d(LOG_TAG, "Requesting configuration");
        new ConfigurationAsyncTask().execute();
    }

    public LiveData<ConfigurationResponse> getConfiguration() {return this.configuration;}

    private class ConfigurationAsyncTask extends AsyncTask<Void, Void, ConfigurationResponse> {

        @Override
        protected ConfigurationResponse doInBackground(Void... voids) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(TmdbApiService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

                Call<ConfigurationResponse> call = tmdbApiService.getConfiguration();

                Response<ConfigurationResponse> response = call.execute();

                if (response.isSuccessful()) {
                    ConfigurationResponse result = response.body();
                    Log.d(LOG_TAG, String.format("Got configuration response: %s", result.getImages().getBase_url().toString()));
                    return result;
                } else {
                    Log.d(LOG_TAG, String.format("Error getting Configuration: %s", response.message()));
                    return null;
                }
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConfigurationResponse result) {
            if (result != null) {
                configuration.setValue(result);
                Log.d(LOG_TAG, "onPostExecuted configuration found" + result);
            } else  {
                Log.d(LOG_TAG, "onPostExecuted Error happened, no configuration found!");
            }
        }
    }
}
