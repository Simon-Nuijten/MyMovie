package com.example.mymovieapp_v1;

import org.junit.Assert;
import org.junit.Test;

import com.example.mymovieapp_v1.presentation.validation.ValidateListInput;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ValidateListInputTest {

    // Validate listName
    @Test
    public void TestValidateListNameIsNull() {
        //Arrange
        String listName = "";

        //Act
        Boolean result = ValidateListInput.validateListName(listName);

        //Assert
        Assert.assertEquals(false, result);
    }

    //Listname has maxLength of 50
    @Test
    public void TestValidateListNameMoreThanMaxCharacters() {
        //Arrange
        String listName = "U7fB58saIvIid2IIlZIUf6C9ulnVMgtFyAGoT1sAwx4SWvNLhpv";

        //Act
        Boolean result = ValidateListInput.validateListName(listName);

        //Assert
        Assert.assertEquals(false, result);
    }

    //Validate list description
    @Test
    public void TestValidateListDescIsEmpty() {
        //Arrange
        String listDesc = "";

        //Act
        Boolean result = ValidateListInput.validateDescription(listDesc);

        //Assert
        Assert.assertEquals(false, result);
    }

}