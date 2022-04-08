package com.example.mymovieapp_v1.datastorage.interfaces;

import com.example.mymovieapp_v1.domain.AccountDetails;
import com.example.mymovieapp_v1.domain.Language;
import com.example.mymovieapp_v1.domain.ListDetails;
import com.example.mymovieapp_v1.domain.body.AddMovieBody;
import com.example.mymovieapp_v1.domain.body.CreateListBody;
import com.example.mymovieapp_v1.domain.response.CreateListResponse;
import com.example.mymovieapp_v1.domain.response.AddMovieResponse;
import com.example.mymovieapp_v1.domain.response.AuthResponse;
import com.example.mymovieapp_v1.domain.response.ConfigurationResponse;
import com.example.mymovieapp_v1.domain.response.GeneralResponse;
import com.example.mymovieapp_v1.domain.response.GenreResponse;
import com.example.mymovieapp_v1.domain.response.ListResponse;
import com.example.mymovieapp_v1.domain.response.MovieResponse;
import com.example.mymovieapp_v1.domain.response.ReviewResponse;
import com.example.mymovieapp_v1.domain.response.TrailerResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TmdbApiService {
    //For Proxy hostname use: 10.0.2.2
    //    Proxy port:         8888

    String API_KEY = "b741cc71fa77884b19c3cb27c9d66b15";
    String SESSION_ID = "cc9a49ae231f2c392bf7b82fb7dc75d33e16908d";
    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("configuration/languages?api_key=" + API_KEY)
    Call<List<Language>> getAllLanguages();

    @GET("movie/popular?api_key=" + API_KEY)
    Call<MovieResponse> getPopularMovies();

    @GET("configuration?api_key=" + API_KEY)
    Call<ConfigurationResponse> getConfiguration();

    @GET("genre/movie/list?api_key=" + API_KEY)
    Call<GenreResponse> getGenres();

    @GET("movie/{movie_id}/reviews?api_key=" + API_KEY)
    Call<ReviewResponse> getReviews(@Path("movie_id")int movieId);

    //Get trailer link
    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailer(@Path("movie_id")int movieId);

    //Get Session_id token
    @GET("authentication/token/new?api_key=" + API_KEY)
    Call<AuthResponse> getRequestToken();

    //Get Created Lists
    @GET("account/{account_id}/lists?api_key=" + API_KEY + "&session_id=" + SESSION_ID)
    Call<ListResponse> getAllMovieLists(@Path("account_id")int accountId);

    //Get account details
    @GET("account?api_key=" + API_KEY + "&session_id=" + SESSION_ID)
    Call<AccountDetails> getAccountInfo();

    //Get list details with movies inside
    @GET("list/{list_id}?api_key=" + API_KEY)
    Call<ListDetails> getListDetails(@Path("list_id")int listId);

    //Add Movie
    @POST("list/{list_id}/add_item?api_key=" + API_KEY + "&session_id=" + SESSION_ID)
    Call<AddMovieResponse> addMovie(@Path("list_id")int listId, @Body AddMovieBody body);

    //Create List
    @POST("list?api_key=" + API_KEY + "&session_id=" + SESSION_ID)
    Call<CreateListResponse> createList(@Body CreateListBody body);

    //Delete movie from list
    @POST("list/{list_id}/remove_item?api_key=" + API_KEY + "&session_id=" + SESSION_ID)
    Call<GeneralResponse> deleteList(@Path("list_id")int listId, @Body AddMovieBody body);
}
