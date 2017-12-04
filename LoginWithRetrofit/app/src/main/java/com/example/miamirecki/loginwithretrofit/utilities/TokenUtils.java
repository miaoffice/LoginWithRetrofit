package com.example.miamirecki.loginwithretrofit.utilities;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.miamirecki.loginwithretrofit.Constants;

/**
 * Created by miamirecki on 11/20/17.
 *
 * Utility class containing common actions performed on the token
 *
 */

public class TokenUtils {

    public static final String LOG_TAG = "Token";

    public static void writeTokenToSharedPreferences(SharedPreferences preferences, String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_TOKEN_KEY, token);
        Log.i(LOG_TAG, token);
        editor.apply();
    }

    public static String getTokenFromSharedPreferences(SharedPreferences preferences) {
        String token = preferences.getString(Constants.SHARED_PREFERENCES_TOKEN_KEY, null);
        if (token == null) {
            Log.i(LOG_TAG, "Token is null - getTokenFromSharedPreferences()");
        } else {
            Log.i(LOG_TAG, " Token is: " + token  + " - getTokenFromSharedPreferences()");
        }
        return token;
    }

    public static void deleteTokenFromSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Constants.SHARED_PREFERENCES_TOKEN_KEY);
        editor.apply();
    }

    public static void refreshTokenInSharedPreferences(SharedPreferences preferences) {
        // TODO: Refresh??
    }

}
