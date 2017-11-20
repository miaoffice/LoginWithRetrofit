package com.example.miamirecki.loginwithretrofit.utilities;

import android.content.SharedPreferences;

import com.example.miamirecki.loginwithretrofit.Constants;

/**
 * Created by miamirecki on 11/20/17.
 *
 */

public class TokenUtils {

    public static void writeTokenToSharedPreferences(SharedPreferences preferences, String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_TOKEN, token);
        editor.apply();
    }

    public static String getTokenFromSharedPreferences(SharedPreferences preferences) {
        return preferences.getString(Constants.SHARED_PREFERENCES_TOKEN, null);
    }

    public static void deleteTokenFromSharedPreferences(SharedPreferences preferences) {
        // TODO: Make logout possible
    }

    public static void refreshTokenInSharedPreferences(SharedPreferences preferences) {
        // TODO: Refresh??
    }

}
