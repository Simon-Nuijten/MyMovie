package com.example.mymovieapp_v1.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieapp_v1.datastorage.repository.AccountRepository;
import com.example.mymovieapp_v1.datastorage.repository.LanguageRepository;
import com.example.mymovieapp_v1.datastorage.repository.ListsRepository;
import com.example.mymovieapp_v1.domain.AccountDetails;
import com.example.mymovieapp_v1.domain.Language;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.MoviesList;
import com.example.mymovieapp_v1.domain.response.CreateListResponse;
import com.example.mymovieapp_v1.domain.response.GeneralResponse;

import java.util.HashMap;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private ListsRepository listsRepository;
    private AccountRepository accountRepository;
    private LanguageRepository languageRepository;
    private static final String LOG_TAG = ProfileViewModel.class.getSimpleName();

    private LiveData<AccountDetails> mAccountDetails;
    private LiveData<List<Language>> mLanguages;
    private LiveData<List<MoviesList>> mLists;
    private LiveData<CreateListResponse> mCreateListResponse;
    private LiveData<ListDetails> mListDetails;
    private LiveData<GeneralResponse> mRemoveMovie;

    public ProfileViewModel() {
        this.listsRepository = ListsRepository.getInstance();
        this.accountRepository = AccountRepository.getInstance();
        this.languageRepository = LanguageRepository.getInstance();

        mAccountDetails = accountRepository.getAccountDetails();
        mLanguages = languageRepository.getLanguages();
        mLists = listsRepository.getMovieListValues();
        mCreateListResponse = listsRepository.getCreateListResponse();
        mListDetails = listsRepository.getListDetailsValues();
        mRemoveMovie = listsRepository.getRemoveMovie();
    }

    public LiveData<AccountDetails> getmAccountDetails() {
        return mAccountDetails;
    }

    public LiveData<List<Language>> getmLanguages() {
        return mLanguages;
    }

    public LiveData<List<MoviesList>> getmLists() {
        return mLists;
    }

    public LiveData<ListDetails> getmListDetails() { return mListDetails; }

    public LiveData<CreateListResponse> getmCreateListResponse() {
        return mCreateListResponse;
    }

    public LiveData<GeneralResponse> getmRemoveMovie() { return mRemoveMovie; }

    public void getAccountDetailsTask() {
        this.accountRepository.getAccountDetailsTask();
    }

    public void getLanguages() {
        this.languageRepository.getAllLanguages();
    }

    public void getLists() {
        this.listsRepository.getMovieLists();
    }

    public void getListDetails(int listId) { this.listsRepository.getListDetails(listId); }

    public void getCreateListResponse(String listName, String listDesc) {
        this.listsRepository.createList(listName, listDesc);
    }

    public void getRemoveMovie(int listId, int movieId) {
        this.listsRepository.deleteMovie(listId, movieId);
    }
}