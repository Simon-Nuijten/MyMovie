package com.example.mymovieapp_v1;

import com.example.mymovieapp_v1.domain.Review;
import com.example.mymovieapp_v1.presentation.validation.ValidateReviewsListCallToApi;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidateApiCallReviewstest {
    @Test
    public void TestListContainsAtleastOneObject() {
        //Arrange
        List<Review> empltyReviewList = new ArrayList<>();

        //Act
        Boolean result = ValidateReviewsListCallToApi.listIsEmpty(empltyReviewList);

        //Assert
        Assert.assertEquals(false, result);
    }
}
