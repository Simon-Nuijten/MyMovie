package com.example.mymovieapp_v1.presentation.validation;

import com.example.mymovieapp_v1.domain.Genre;
import com.example.mymovieapp_v1.presentation.MovieDto;

import java.util.List;

public class ValidateGenresListCallToApi {
    /**
     * @desc Validate if the list is getting filled or not.
     *
     * @subcontract list is empty {
     * @requires listName.length() = 0
     * @ensures false;
     * }
     */

    public static Boolean listIsEmpty(List<Genre> listname) {
        if (listname.size() == 0) {
            return false;
        }

        return true;
    }
}
