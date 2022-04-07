package com.example.mymovieapp_v1;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mymovieapp_v1.databinding.ActivityMainBinding;
import com.example.mymovieapp_v1.ui.configuration.ConfigurationViewModel;
import com.example.mymovieapp_v1.ui.lists.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        NavController navController = navHostFragment.getNavController();

        setupActionBarWithNavController(this, navController);
        setupWithNavController(bottomNavigationView, navController);

        ConfigurationViewModel configurationViewModel =
                new ViewModelProvider(this).get(ConfigurationViewModel.class);
        configurationViewModel.getConfiguration();

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_file_key), MODE_PRIVATE);

        configurationViewModel.getmConfigurationResponse().observe(this, configurationResponse -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(configurationResponse);
            editor.putString(String.valueOf(R.string.configResponse), json);
            editor.apply();
        });
    }

    public void setupActionBar(Toolbar toolbar, Activity activity) {
        setSupportActionBar(toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        setupActionBarWithNavController((AppCompatActivity) activity, navHostFragment.getNavController());
    }

//    Dit is een test. Public / private test sheesh. ez ez.
}