package com.example.miamirecki.loginwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miamirecki.loginwithretrofit.model.Login;
import com.example.miamirecki.loginwithretrofit.model.LoginResponse;
import com.example.miamirecki.loginwithretrofit.service.ServiceGenerator;
import com.example.miamirecki.loginwithretrofit.service.UserClient;
import com.example.miamirecki.loginwithretrofit.utilities.TokenUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class LoginActivity extends AppCompatActivity {

    // Declare fields
    EditText etUsername;
    EditText etPassword;
    Button bLogin;
    TextView tvRegister;
    SharedPreferences preferences;


    // Create the service from which to make network calls
    UserClient userClient = ServiceGenerator.createService(UserClient.class);


    // get references to UI objects in onCreate() and set the OnClickListener
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsernameRegister);
        etPassword = findViewById(R.id.etPasswordLogin);
        bLogin = findViewById(R.id.bLogin);
        tvRegister = findViewById(R.id.tvRegisterHere);

        // Set onClickListeners to buttons
        bLogin.setOnClickListener(loginOnClickListener);
        tvRegister.setOnClickListener(registerOnClickListener);

        // get a reference to SharedPreferences
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

    }

    /*
    Checks if both username and password fields are filled,
    takes the data and sends it to login()
     */
    View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        LoginActivity.this,
                        "Please provide username and password",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                login(username, password);
            }
        }
    };

    /*
    Sends the user to the RegisterActivity
     */
    View.OnClickListener registerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToRegisterActivity();
        }
    };

    /*
    Attempt a login with the username and password the user provided
    If the login is successful and a token is received, send user to the next Activity
     */
    private void login(String username, String password) {

        // Call the login function from UserClient interface
        Call<LoginResponse> call = userClient.login(new Login(username, password));

        // Enqueue this call and decide how to treat the response
        call.enqueue(new Callback<LoginResponse>() {

            // If the login is successful onResponse, send user to the next Activity
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body().getSuccess()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    String token = response.body().getToken();
                    if(token != null) {
                        TokenUtils.writeTokenToSharedPreferences(preferences, token);
                        goToProfilePage();
                    } else {
                        Toast.makeText(LoginActivity.this, "Token is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Wrong username or password",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            // if login fails, show a Toast message
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed (onFailure)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goToProfilePage() {
        Intent showProfilePageIntent = new Intent(LoginActivity.this, ProfilePageActivity.class);
        showProfilePageIntent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(showProfilePageIntent);
        finish();
    }

    private void goToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        registerIntent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(registerIntent);
    }

}
