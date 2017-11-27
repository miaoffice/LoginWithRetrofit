package com.example.miamirecki.loginwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miamirecki.loginwithretrofit.model.ProfileInfo;
import com.example.miamirecki.loginwithretrofit.service.ServiceGenerator;
import com.example.miamirecki.loginwithretrofit.service.UserClient;
import com.example.miamirecki.loginwithretrofit.utilities.TokenUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePageActivity extends AppCompatActivity {

    FrameLayout flMainFrameLayout;
    TextView tvAccountId;
    TextView tvEmail;
    TextView tvUsername;
    TextView tvJoined;
    TextView tvName;
    Button btnLogout;
    SharedPreferences preferences;

    UserClient userClient = ServiceGenerator.createService(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        flMainFrameLayout = findViewById(R.id.flMainFrameLayout);
        tvAccountId = findViewById(R.id.tvAccountIDProfile);
        tvUsername = findViewById(R.id.tvUsernameProfile);
        tvEmail = findViewById(R.id.tvEmailProfile);
        tvJoined = findViewById(R.id.tvJoinedProfile);
        tvName = findViewById(R.id.tvNameProfile);
        btnLogout = findViewById(R.id.btnLogoutProfile);

        // Set onClickListener to button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Get a reference to SharedPreferences
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        // Connect to REST server and get profile information
        getProfileInfo();

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
                logout();
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
        Call<ProfileInfo> call = userClient.seeProfilePage(token);
        // enque the call
        call.enqueue(new Callback<ProfileInfo>() {
            @Override
            public void onResponse(Call<ProfileInfo> call, Response<ProfileInfo> response) {
                // if there is a response from the server, cast it into a string and
                // send it as an Extra to ProfilePageActivity
                if(response.isSuccessful()) {
                    ProfileInfo profile = response.body();
                    fillProfileUI(profile);
                } else {
                    //Toast.makeText(ProfilePageActivity.this, "Response was not successful", Toast.LENGTH_SHORT).show();
                    goToLoginPage();
                }
            }

            @Override
            public void onFailure(Call<ProfileInfo> call, Throwable t) {
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

    private void logout() {
        TokenUtils.deleteTokenFromSharedPreferences(preferences);
        goToLoginPage();
    }

    private void fillProfileUI(ProfileInfo profile) {
        checkAndFillInfo(profile.getId(), tvAccountId);
        checkAndFillInfo(profile.getUsername(), tvUsername);
        checkAndFillInfo(profile.getEmail(), tvEmail);
        checkAndFillInfo(trimDate(profile.getCreatedAt()), tvJoined);


        String firstname = profile.getFirstName();
        String lastname = profile.getLastName();
        if(firstname == null && lastname == null) {
            tvName.setText(getResources().getString(R.string.isNull));
        } else if (firstname != null && lastname == null) {
            String firstNameCapitalized = firstname.substring(0, 1).toUpperCase() + firstname.substring(1);
            tvName.setText(firstNameCapitalized);
        } else {
            String firstNameCapitalized = firstname.substring(0, 1).toUpperCase() + firstname.substring(1);
            String lastNameCapitalized = lastname.substring(0, 1).toUpperCase() + lastname.substring(1);
            String fullName = firstNameCapitalized + " " + lastNameCapitalized;
            tvName.setText(fullName);
        }
    }

    private void checkAndFillInfo(String info, TextView textView) {
        if (info != null) {
            textView.setText(info);
        } else {
            textView.setText(getResources().getString(R.string.isNull));
        }
    }

    private String trimDate(String dateString) {
        // TODO: Apply String to Date conversion
        return dateString.substring(0, 10);
    }
}
