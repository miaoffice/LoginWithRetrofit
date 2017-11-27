package com.example.miamirecki.loginwithretrofit.model;

/**
 * Created by miamirecki on 11/27/17.
 *
 * Shaped to contain info about the user's profile
 */

public class ProfileInfo {

    private String _id;
    private String updatedAt;
    private String createdAt;
    private String username;
    private String password;
    private String email_address;
    private String firstName;
    private String lastName;
    private String v;

    public String getId() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email_address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

}

// TODO: Make time work
