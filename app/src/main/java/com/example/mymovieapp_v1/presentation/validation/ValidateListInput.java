package com.example.mymovieapp_v1.presentation.validation;

public class ValidateListInput {
    /**
     * @desc Validates if list name is correct.
     *
     * @subcontract no list name {
     * @requires listName.isEmpty()
     * @ensures false;
     * }
     *
     * @subcontract list to long {
     * @requires listName.length() > 50
     * @ensures false;
     * }
     */

    public static Boolean validateListName(String listname) {
        if (listname.isEmpty()) { return false; }
        if (listname.length() > 50) { return false; }

        return true;
    }

    /**
     * @desc Validate if list description is correct.
     *
     * @subcontract no description {
     * @requires description.isEmpty()
     * @ensures false;
     * }
     */

    public static Boolean validateDescription(String listDesc) {
        if (listDesc.isEmpty()) { return false; }

        return true;
    }
}
