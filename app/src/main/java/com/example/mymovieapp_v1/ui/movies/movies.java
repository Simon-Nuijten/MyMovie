package com.example.mymovieapp_v1.ui.movies;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovieapp_v1.ui.movieDetails.MovieDetailsActivity;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.presentation.MovieAdapter;
import com.example.mymovieapp_v1.presentation.MovieDto;
import com.google.gson.Gson;

import java.util.ArrayList;

public class movies extends Fragment {

    private Spinner spinner;
    private String searchType;
    private MovieAdapter movieAdapter;
    private MoviesViewModel mViewModel;
    private RecyclerView movieRecyclerView;
    private SharedPreferences sharedPreferences;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private static final String LOG_TAG = movies.class.getSimpleName();

    public static movies newInstance() {
        return new movies();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        mViewModel.getMovies();
        mViewModel.getGenres();

        View view = inflater.inflate(R.layout.movies_fragment, container, false);

        movieRecyclerView = view.findViewById(R.id.movie_recycler_list);

        sharedPreferences = requireActivity()
                .getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        spinner = view.findViewById(R.id.spinner_select_search_type);

        ArrayAdapter adapter = new ArrayAdapter(
                this.requireActivity(), android.R.layout.simple_spinner_item, new String[]{"Title", "Genre", "Rating"});

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                searchType = spinner.getItemAtPosition(i).toString();
                Log.i("onItemSelected", searchType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Gson gson = new Gson();
        String json = sharedPreferences.getString(String.valueOf(R.string.configResponse), "");
        ConfigurationResponse configurationResponse = gson.fromJson(json, ConfigurationResponse.class);

        movieAdapter = new MovieAdapter(new ArrayList<>(), new ArrayList<>(), view.getContext(), configurationResponse);

        mViewModel.getmMovies().observe(getViewLifecycleOwner(), mMovies -> {
            if (mMovies != null) {
                Log.d(LOG_TAG, "SearchFragment -> Movies found: " + mMovies.size());

                movieAdapter.setData(mMovies);

                movieRecyclerView.setAdapter(movieAdapter);
                movieRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            } else {
                Log.d(LOG_TAG, "MoviesFragment -> No movies found!");
            }
        });

        movieAdapter.setOnItemClickListener(new MovieAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(LOG_TAG, "MovieOnClick event triggered!");
                MovieDto movieDto = movieAdapter.getMovieAtPosition(position);
                launchMovieDetailsActivity(movieDto);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    Log.i("onItemSelected, onQuery", searchType);
                    filter(newText, searchType);
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void filter(String text, String searchType) {
        // creating a new array list to filter our data.
        ArrayList<MovieDto> filteredMoviesDtos = new ArrayList<>();

        mViewModel.getmMovies().observe(getViewLifecycleOwner(), mMovies -> {

            switch (searchType) {
                case "Title": {
                    for (MovieDto movieDtoItem : mMovies) {
                        if (movieDtoItem.getTitle().toLowerCase().contains(text.toLowerCase())) {
                            // if the item is matched we are
                            // adding it to our filtered list.
                            filteredMoviesDtos.add(movieDtoItem);
                        }
                    }
                }
                break;
                case "Genre": {
                    for (MovieDto movieDtoItem : mMovies) {
                        for (int i = 0; i < movieDtoItem.getGenre_ids().size(); i++) {
                            if (movieDtoItem.getGenre_ids().get(i).toLowerCase().contains(text.toLowerCase()) && !filteredMoviesDtos.contains(movieDtoItem)) {
                                filteredMoviesDtos.add(movieDtoItem);
                            }
                        }
                    }
                }
                break;
                case "Rating": {
                    try {
                        for (MovieDto movieDtoItem : mMovies) {
                            if (text.isEmpty()) {
                                filteredMoviesDtos.add(movieDtoItem);
                            } else {
                                if (movieDtoItem.getVote_average() > Double.parseDouble(text.toLowerCase())) {
                                    // if the item is matched we are
                                    // adding it to our filtered list.
                                    filteredMoviesDtos.add(movieDtoItem);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(requireActivity(), "Search value is not a number: " + text, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                default:
                    break;
            }
        });

        if (filteredMoviesDtos.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            movieAdapter.filterList(filteredMoviesDtos);

            Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            movieAdapter.filterList(filteredMoviesDtos);
        }
    }

    public void launchMovieDetailsActivity(MovieDto movieDto) {
        Log.d(LOG_TAG, "Launching Movie Details activity!");
        Intent intent = new Intent(requireActivity(), MovieDetailsActivity.class);
        intent.putExtra("MOVIE", movieDto);
        startActivity(intent);
    }
}