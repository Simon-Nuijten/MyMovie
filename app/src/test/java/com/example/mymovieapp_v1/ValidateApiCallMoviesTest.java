package com.example.mymovieapp_v1;

import com.example.mymovieapp_v1.presentation.MovieDto;
import com.example.mymovieapp_v1.presentation.validation.ValidateMovieListCallToApi;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidateApiCallMoviesTest {
    @Test
    public void TestListContainsAtleastOneObject() {
        //Arrange
        List<MovieDto> empltyMovieDtoList = new ArrayList<>();

        //Act
        Boolean result = ValidateMovieListCallToApi.listIsEmpty(empltyMovieDtoList);

        //Assert
        Assert.assertEquals(false, result);
    }
}
