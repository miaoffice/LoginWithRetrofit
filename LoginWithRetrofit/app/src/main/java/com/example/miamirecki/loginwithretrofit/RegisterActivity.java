package com.example.miamirecki.loginwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miamirecki.loginwithretrofit.model.BaseResponse;
import com.example.miamirecki.loginwithretrofit.model.Login;
import com.example.miamirecki.loginwithretrofit.model.LoginResponse;
import com.example.miamirecki.loginwithretrofit.model.RegisterDetails;
import com.example.miamirecki.loginwithretrofit.service.ServiceGenerator;
import com.example.miamirecki.loginwithretrofit.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etNewUsername;
    EditText etNewPassword;
    Button bRegister;
    TextView tvLoginHere;

    SharedPreferences preferences;

    UserClient userClient = ServiceGenerator.createService(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find UI elements
        etFirstName = (EditText) findViewById(R.id.etFirstNameRegister);
        etLastName = (EditText) findViewById(R.id.etLastNameRegister);
        etEmail = (EditText) findViewById(R.id.etEmailRegister);
        etNewUsername = (EditText) findViewById(R.id.etUsernameRegister);
        etNewPassword = (EditText) findViewById(R.id.etPasswordRegister);
        bRegister = (Button) findViewById(R.id.bRegister);
        tvLoginHere = (TextView) findViewById(R.id.tvLoginHere);

        // Set onClickListeners to Buttons
        tvLoginHere.setOnClickListener(loginHerePressed);
        bRegister.setOnClickListener(registerButtonPressed);

        // Get a reference to SharedPreferences to get token
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    View.OnClickListener registerButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String username = etNewUsername.getText().toString();
            String password = etNewPassword.getText().toString();

            if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                    || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        RegisterActivity.this,
                        "You must fill all fields.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                RegisterDetails registerDetails = new RegisterDetails(firstName,
                        lastName, email, username, password);
                register(registerDetails);
            }
        }
    };

    /*
    Allows user to go to login page if they already have an account
     */
    View.OnClickListener loginHerePressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    };

    private void register(RegisterDetails registerDetails) {

        // call the login function from UserClient interface
        Call<BaseResponse> call = userClient.register(registerDetails);

        // enqueue this call and decide how to treat the response
        call.enqueue(new Callback<BaseResponse>() {
            // is the login is successful, send user to the next Activity
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful() && response.body().getSuccess()) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // TODO: Show next page after successful login


                } else {
                    Toast.makeText(
                            RegisterActivity.this,
                            "Wrong username or password",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
            // if login fails, show a Toast message
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Login failed (onFailure)", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
