package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/3/17.
 */

public class User {

    private boolean success;
    private String message;
    private String token;

    public User(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public boolean getSuccess() { return success; }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

}
