package com.example.mymovieapp_v1.ui.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.R.layout;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymovieapp_v1.R;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.Movie;
import com.example.mymovieapp_v1.domain.MoviesList;
import com.example.mymovieapp_v1.presentation.ListsAdapter;
import com.example.mymovieapp_v1.presentation.validation.ValidateListInput;
import com.example.mymovieapp_v1.ui.listDetails.ListDetailsActivity;

import java.util.ArrayList;
import java.util.Objects;

public class profile extends Fragment {

    private Spinner languageSpinner;
    private Spinner filterListSpinner;

    private ImageView accountImage;
    private TextView accountName;
    private TextView languageValue;

    private RecyclerView listRecyclerView;
    private ListsAdapter listsAdapter;
    private boolean listClicked;

    private ProfileViewModel mViewModel;
    private final static String LOG_TAG = profile.class.getSimpleName();

    public static profile newInstance() {
        return new profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.getLists();
        mViewModel.getLanguages();
        mViewModel.getAccountDetailsTask();

        accountImage = view.findViewById(R.id.profile_picture);
        accountName = view.findViewById(R.id.profile_name);
        languageValue = view.findViewById(R.id.profile_language_value);

        languageSpinner = view.findViewById(R.id.spinner_language);
        filterListSpinner = view.findViewById(R.id.list_movies_overview_filter_dropdown);

        listRecyclerView = view.findViewById(R.id.list_movies_recycler_overview);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> getActivity().onBackPressed());

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                requireActivity(), layout.simple_spinner_item, new String[]{"Title"}
        );

        arrayAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        filterListSpinner.setAdapter(arrayAdapter);

        filterListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchType = filterListSpinner.getItemAtPosition(i).toString();
                Log.i("onItemSelected", searchType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mViewModel.getmAccountDetails().observe(getViewLifecycleOwner(), accountDetails -> {
            if (accountDetails.getAvatar().getTmdb().getAvatar_path() != null) {
                Glide.with(accountImage)
                        .load(accountDetails.getAvatar().getTmdb().getAvatar_path())
                        .centerCrop()
                        .into(accountImage);
            } else {
                accountImage.setImageResource(R.drawable.ic_person_big_24);
            }

            String name = accountDetails.getName().isEmpty() ? accountDetails.getUsername() : accountDetails.getName();
            accountName.setText(name);
            languageValue.setText(accountDetails.getIso_3166_1());
        });

        listsAdapter = new ListsAdapter(new ArrayList<>(), view.getContext());

        mViewModel.getmLists().observe(getViewLifecycleOwner(), moviesLists -> {
            if (!moviesLists.isEmpty()) {
                listsAdapter.setData(moviesLists);

                listRecyclerView.setAdapter(listsAdapter);
                listRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            } else {
                Log.d(LOG_TAG, "ProfileFragment -> No lists found!");
            }
        });

        listsAdapter.setOnItemClickListener(new ListsAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mViewModel.getListDetails(listsAdapter.getMovieListAtPosition(position).getId());
                Log.d(LOG_TAG, "Launching list details activity");
                Intent intent = new Intent(requireActivity(), ListDetailsActivity.class);

                intent.putExtra("LIST", listsAdapter.getMovieListAtPosition(position));

                startActivity(intent);
            }
        });
//        mViewModel.getmLists().observe(getViewLifecycleOwner(), moviesLists -> {
//
//            listsAdapter.setDataMovieList(moviesLists);
//
//            listsAdapter.setOnItemClickListener((v, position) -> {
//                Toast.makeText(requireActivity(), "test", Toast.LENGTH_SHORT).show();
//
////                        Toast.makeText(v.getContext(), "Test name: " + moviesLists.get(position).getId(), Toast.LENGTH_SHORT).show();
//                mViewModel.getListDetails(moviesLists.get(position).getId());
//                Toast.makeText(requireActivity(), "test2", Toast.LENGTH_SHORT).show();
//
//                mViewModel.getmListDetails().observe(getViewLifecycleOwner(), listDetails -> {
////                            Toast.makeText(requireActivity(), "listDetails: " + listDetails.getName(), Toast.LENGTH_SHORT).show();
//
//                    CardView listRecycerCardItem = v.findViewById(R.id.lists_recycler_item);
//                    LinearLayout hiddenListMovieContent = v.findViewById(R.id.lists_recycler_item_hidden_movie_layout);
//                    ImageButton listExpandButton = v.findViewById(R.id.lists_recycler_item_arrow_button);
//
//                    if (hiddenListMovieContent.getVisibility() == View.VISIBLE) {
//
//                            listsAdapter.setData(moviesLists, null);
//                            listsAdapter.notifyItemChanged(position, moviesLists);
//
//                        TransitionManager.beginDelayedTransition(
//                                listRecycerCardItem, new AutoTransition());
//
//                        hiddenListMovieContent.setVisibility(View.GONE);
//                        listExpandButton.setImageResource(R.drawable.ic_arrow_collapsed_24);
//                    } else {
////                            listsAdapter.setDataMoviesInList(listDetails);
//
//                            listsAdapter.setData(moviesLists, listDetails);
//                            listsAdapter.notifyItemChanged(position, moviesLists);
//
//                        TransitionManager.beginDelayedTransition(
//                                listRecycerCardItem, new AutoTransition());
//
//                        hiddenListMovieContent.setVisibility(View.VISIBLE);
//                        listExpandButton.setImageResource(R.drawable.ic_arrow_expanded_24);
//                    }
//                });
//            });
//            listRecyclerView.setAdapter(listsAdapter);
//            listRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        });

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
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_list:
                View listAddDialog = getLayoutInflater().inflate(R.layout.profile_create_list_dialog, null);

                TextView listName = listAddDialog.findViewById(R.id.dialog_list_name);
                TextView listDesc = listAddDialog.findViewById(R.id.dialog_list_description);

                AlertDialog builder = new AlertDialog.Builder(requireActivity())
                        .setTitle("Create new list")
                        .setView(listAddDialog)
                        .setPositiveButton(android.R.string.ok, null)
                        .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                        .show();

                Button button = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ValidateListInput.validateListName(listName.getText().toString())) {
                            listName.getBackground().setColorFilter(profile.this.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                            Toast.makeText(profile.this.requireActivity(), "List name is required!", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(LOG_TAG, "List name is not empty");
                            mViewModel.getCreateListResponse(listName.getText().toString(), listDesc.getText().toString());
                            Toast.makeText(profile.this.requireActivity(), String.format("List %s created!", listName.getText().toString()), Toast.LENGTH_LONG).show();
                            builder.dismiss();
                        }
                    }
                });
//                Log.i("item id ", item.getItemId() + "");
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}