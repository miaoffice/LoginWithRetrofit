package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/20/17.
 *
 * An object containing all the data the user provides when registering
 *
 */

public class RegisterDetails {

    private String firstName;
    private String lastName;
    private String email_address;
    private String username;
    private String password;

    public RegisterDetails(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email_address = email;
        this.username = username;
        this.password = password;
    }

}
