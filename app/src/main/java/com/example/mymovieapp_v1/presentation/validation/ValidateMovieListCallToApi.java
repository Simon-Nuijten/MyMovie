package com.example.mymovieapp_v1.presentation.validation;

import com.example.mymovieapp_v1.presentation.MovieDto;

import java.util.List;

public class ValidateMovieListCallToApi {

    /**
     * @desc Validate if the list is getting filled or not.
     *
     * @subcontract list is empty {
     * @requires listName.length() = 0
     * @ensures false;
     * }
     */

    public static Boolean listIsEmpty(List<MovieDto> listname) {
        if (listname.size() == 0) {
            return false;
        }

        return true;
    }

}
