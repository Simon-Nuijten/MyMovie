package com.example.mymovieapp_v1.ui.configuration;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieapp_v1.datastorage.repository.ConfigurationRepository;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;

public class ConfigurationViewModel extends ViewModel {
    private ConfigurationRepository configurationRepository;
    private static final String LOG_TAG = ConfigurationViewModel.class.getSimpleName();

    private LiveData<ConfigurationResponse> mConfigurationResponse;

    public ConfigurationViewModel() {
        Log.d(LOG_TAG, "Init Config ViewModel");
        this.configurationRepository = ConfigurationRepository.getInstance();
        mConfigurationResponse = configurationRepository.getConfiguration();
    }

    public LiveData<ConfigurationResponse> getmConfigurationResponse() {
        Log.d(LOG_TAG, String.format("Get ConfigResponse: %s", mConfigurationResponse));
        return mConfigurationResponse;
    }

    public void getConfiguration() {
        Log.d(LOG_TAG, "Executing Async ConfigurationTask");
        configurationRepository.ExecuteConfigurationTask();
    }
}
