package com.example.miamirecki.loginwithretrofit.service;

import com.example.miamirecki.loginwithretrofit.model.Login;
import com.example.miamirecki.loginwithretrofit.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by miamirecki on 11/3/17.
 */

public interface UserClient {


    /*
    "login" is the url endpoint
    Call<User> means we expect a User object back
    --- User object contains a token in it!
    The @Body of the request is a Login object, containing a username and a password
     */
    @POST("home/login")
    Call<User> login(@Body Login login);

    /*
    "secondpage" is a URL endpoint for which we need a token to be able to access it
     Call<ResponseBody> means that we expect a simple object (ResponseBody) back (in the tutorial,
     it's a single String), and there is not need to cast it into a data model
     In the Header of the call, we state that it is an authorization call, and send in a token
     to identify the user
     */
    // TODO: Send token as Header
    @GET("home/login/secret")
    Call<ResponseBody> seeNextPage(@Query("token") String authToken);

}
