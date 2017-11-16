package com.example.miamirecki.loginwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.miamirecki.loginwithretrofit.model.Login;
import com.example.miamirecki.loginwithretrofit.model.LoginResponse;
import com.example.miamirecki.loginwithretrofit.service.UserClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // declare fields
    Spinner spUsername;
    EditText etPassword;
    Button bLogin;
    SharedPreferences preferences;


    // build a RetroFit client
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    UserClient userClient = retrofit.create(UserClient.class);


    // get references to UI objects in onCreate() and set the OnClickListener
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spUsername = (Spinner) findViewById(R.id.spUsernameLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        bLogin = (Button) findViewById(R.id.bLogin);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnerUsernames, R.layout.spinner_item);
        spUsername.setAdapter(adapter);

        bLogin.setOnClickListener(loginOnClickListener);

        // get a reference to SharedPreferences
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);


    }

    View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = spUsername.getSelectedItem().toString();
            String password = etPassword.getText().toString();

            if(password.isEmpty()) {
                Toast.makeText( LoginActivity.this, "Please provide a password", Toast.LENGTH_SHORT).show();
            } else {
                login(username, password);
            }
        }
    };

    /*
    Attempt a login with the username and password the user provided
    If the login is successful and a token is received, send user to the next Activity
     */
    private void login(String username, String password) {

        // create a Login object
        Login loginDetails = new Login(username, password, false);

        // call the login function from UserClient interface
        Call<LoginResponse> call = userClient.login(loginDetails);

        // enqueue this call and decide how to treat the response
        call.enqueue(new Callback<LoginResponse>() {
            // is the login is successful, send user to the next Activity
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body().getSuccess()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    String token = response.body().getToken();
                    if(token != null) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Constants.SHARED_PREFERENCES_TOKEN, token);
                        editor.commit();
                        showNextPage();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
            // if login fails, show a Toast message
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed (onFailure)", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*
    Extracts token from SharedPreferences and sends the user to another activity
    where a message from the server is displayed if the token is okay
     */
    private void showNextPage() {
        // get the value of token
        String token = preferences.getString(Constants.SHARED_PREFERENCES_TOKEN, null);
        // make a http call to the server and send the token in a header
        Call<ResponseBody> call = userClient.seeNextPage(token);
        // enque the call
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // if there is a response from the server, cast it into a string and
                // send it as an Extra to SecondActivity
                if(response.isSuccessful()) {
                    try {
                        String serverMessage = response.body().string();
                        Intent toSecondActivityIntent = new Intent(LoginActivity.this, SecondActivity.class);
                        toSecondActivityIntent.putExtra(Constants.SECOND_ACTIVITY_EXTRA, serverMessage);
                        startActivity(toSecondActivityIntent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Failed to get response via showNextPage() - onResponse",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(
                        LoginActivity.this,
                        "Failed to get response via showNextPage() - onFailure",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
