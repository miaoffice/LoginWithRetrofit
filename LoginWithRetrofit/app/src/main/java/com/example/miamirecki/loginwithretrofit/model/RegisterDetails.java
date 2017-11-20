package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/20/17.
 */

public class RegisterDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public RegisterDetails(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
