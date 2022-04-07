package com.example.mymovieapp_v1.datastorage.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymovieapp_v1.datastorage.interfaces.TmdbApiService;
import com.example.mymovieapp_v1.domain.Language;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LanguageRepository {
    private final MutableLiveData<List<Language>> languages;
    private static volatile LanguageRepository instance;
    private static final String LOG_TAG = LanguageRepository.class.getSimpleName();

    public LanguageRepository() {
        languages = new MutableLiveData<>();
    }

    public static LanguageRepository getInstance() {
        if (instance == null) {
            instance = new LanguageRepository();
        }
        return instance;
    }

    public void getAllLanguages() {
        Log.d(LOG_TAG, "Requesting all languages");
        new LanguagesAsyncTask().execute();
    }

    public LiveData<List<Language>> getLanguages() {
        return languages;
    }

    private class LanguagesAsyncTask extends AsyncTask<Void, Void, List<Language>> {

        @Override
        protected List<Language> doInBackground(Void... voids) {

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(TmdbApiService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TmdbApiService tmdbApiService = retrofit.create(TmdbApiService.class);

                Call<List<Language>> call = tmdbApiService.getAllLanguages();

                Response<List<Language>> response = call.execute();

                if (response.isSuccessful()) {
                    List<Language> result = response.body();
                    Log.d(LOG_TAG, String.format("Got language results: %d", result.size()));
                    return result;
                } else {
                    Log.d(LOG_TAG, String.format("Error getting Languages: %s", response.message()));
                    return null;
                }
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception: " + e.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Language> result) {
            if (result != null) {
                languages.setValue(result);
                Log.d(LOG_TAG, "onPostExecuted languages found: " + result);
            } else {
                Log.d(LOG_TAG, "onPostExecuted Error happened, no language found!");
            }
        }
    }
}
