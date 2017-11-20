package com.example.miamirecki.loginwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.miamirecki.loginwithretrofit.utilities.TokenUtils;

public class ProfilePageActivity extends AppCompatActivity {

    TextView tvServerResponse;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvServerResponse = (TextView) findViewById(R.id.tvServerResponse);

        String message = getIntent().getExtras().getString(Constants.SECOND_ACTIVITY_EXTRA);

        tvServerResponse.setText(message);

        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

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

}
