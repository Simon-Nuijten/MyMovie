package com.example.mymovieapp_v1.presentation.validation;

import com.example.mymovieapp_v1.domain.Review;
import com.example.mymovieapp_v1.presentation.MovieDto;

import java.util.List;

public class ValidateReviewsListCallToApi {
    /**
     * @desc Validate if the list of Reviews is getting filled or not.
     *
     * @subcontract list is empty {
     * @requires listName.length() = 0
     * @ensures false;
     * }
     * @param listname
     */

    public static Boolean listIsEmpty(List<Review> listname) {
        if (listname.size() == 0) {
            return false;
        }

        return true;
    }
}
