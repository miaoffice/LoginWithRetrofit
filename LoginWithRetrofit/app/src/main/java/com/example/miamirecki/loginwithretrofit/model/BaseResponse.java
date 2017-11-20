package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/17/17.
 *
 * A base response model class for common attributes in the returning JSON.
 */

public class BaseResponse {

    private String message;
    private boolean success;


    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}
