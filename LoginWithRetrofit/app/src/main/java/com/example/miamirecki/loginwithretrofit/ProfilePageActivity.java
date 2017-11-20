package com.example.miamirecki.loginwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miamirecki.loginwithretrofit.service.ServiceGenerator;
import com.example.miamirecki.loginwithretrofit.service.UserClient;
import com.example.miamirecki.loginwithretrofit.utilities.TokenUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePageActivity extends AppCompatActivity {

    TextView tvServerResponse;
    SharedPreferences preferences;

    UserClient userClient = ServiceGenerator.createService(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        tvServerResponse = (TextView) findViewById(R.id.tvServerResponse);

        // Get a referece to SharedPreferences
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        // Connect to REST server and get profile information
        getProfileInfo();

        // TODO: Show profile info

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logoutMenuItem:
                TokenUtils.deleteTokenFromSharedPreferences(preferences);
                Intent backToLogin = new Intent(ProfilePageActivity.this, LoginActivity.class);
                startActivity(backToLogin);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Extracts token from SharedPreferences and sends the user to another activity
    where a message from the server is displayed if the token is okay
     */
    public void getProfileInfo() {
        // get the value of token
        String token = TokenUtils.getTokenFromSharedPreferences(preferences);
        if(token == null) {
            Toast.makeText(ProfilePageActivity.this, "Token is empty", Toast.LENGTH_SHORT).show();
            goToLoginPage();
        }
        // make an http call to the server
        Call<ResponseBody> call = userClient.seeProfilePage(token);
        // enque the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // if there is a response from the server, cast it into a string and
                // send it as an Extra to ProfilePageActivity
                if(response.isSuccessful()) {
                    try {
                        String serverMessage = response.body().string();
                        tvServerResponse.setText(serverMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    goToLoginPage();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(
                        ProfilePageActivity.this,
                        "Failed to get response via showProfileInfo() - onFailure",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void goToLoginPage() {
        Intent goToLoginIntent = new Intent(ProfilePageActivity.this, LoginActivity.class);
        startActivity(goToLoginIntent);
    }

}
