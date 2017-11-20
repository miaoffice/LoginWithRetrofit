package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/3/17.
 *
 * Response expected upon sucessful login. Contains token used for further authorization.
 */

public class LoginResponse {

    private boolean success;
    private String message;
    private String token;

    public LoginResponse(boolean success, String message, String token) {
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
