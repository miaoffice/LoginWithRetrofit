package com.example.miamirecki.loginwithretrofit.service;

import com.example.miamirecki.loginwithretrofit.model.BaseResponse;
import com.example.miamirecki.loginwithretrofit.model.Login;
import com.example.miamirecki.loginwithretrofit.model.LoginResponse;
import com.example.miamirecki.loginwithretrofit.model.ProfileInfo;
import com.example.miamirecki.loginwithretrofit.model.RegisterDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by miamirecki on 11/3/17.
 *
 */

public interface UserClient {


    /*
    "login" is the url endpoint
    Call<User> means we expect a User object back
    --- User object contains a token in it!
    The @Body of the request is a Login object, containing a username and a password
     */
    @POST("home/login")
    Call<LoginResponse> login(@Body Login login);

    /*
    registers new user by sending a Login object in the body of the request
    expects a RegisterResponse object in return, with RegisterResponse.success either true or false
     */
    @POST("home/register")
    Call<LoginResponse> register(@Body RegisterDetails registerDetails);

    /*
    "home/login/profile" is a URL endpoint for which we need a token to be able to access it
     Call<ResponseBody> means that we expect a simple object (ResponseBody) back (in the tutorial,
     it's a single String), and there is not need to cast it into a data model
     In the Header of the call, we state that it is an authorization call, and send in a token
     to identify the user
     */
    @GET("home/login/profile")
    Call<ProfileInfo> seeProfilePage(@Header("x-access-token") String authToken);

}
