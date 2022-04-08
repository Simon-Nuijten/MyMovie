package com.example.mymovieapp_v1;

import com.example.mymovieapp_v1.domain.Genre;
import com.example.mymovieapp_v1.presentation.validation.ValidateGenresListCallToApi;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidateApiCallGenresTest {
    @Test
    public void TestListContainsAtleastOneObject() {
        //Arrange
        List<Genre> empltyGenreList = new ArrayList<>();

        //Act
        Boolean result = ValidateGenresListCallToApi.listIsEmpty(empltyGenreList);

        //Assert
        Assert.assertEquals(false, result);
    }
}