package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/3/17.
 *
 * An object sent via login() and register() functions
 */

public class Login {

    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
