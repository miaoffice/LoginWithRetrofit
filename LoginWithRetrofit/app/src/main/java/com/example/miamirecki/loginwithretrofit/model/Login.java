package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/3/17.
 */

public class Login {

    private String username;
    private String password;

    public Login(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
    }
}
