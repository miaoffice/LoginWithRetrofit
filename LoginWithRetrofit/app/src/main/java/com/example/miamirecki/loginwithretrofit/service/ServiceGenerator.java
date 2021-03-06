package com.example.miamirecki.loginwithretrofit.service;

import com.example.miamirecki.loginwithretrofit.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by miamirecki on 11/20/17.
 *
 * Creates a Retrofit client which is used in every network call
 *
 */

public class ServiceGenerator {

    // build a RetroFit client
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
