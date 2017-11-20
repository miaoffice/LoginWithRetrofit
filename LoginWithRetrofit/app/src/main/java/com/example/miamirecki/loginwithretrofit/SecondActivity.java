package com.example.miamirecki.loginwithretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tvServerResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvServerResponse = (TextView) findViewById(R.id.tvServerResponse);

        String message = getIntent().getExtras().getString(Constants.SECOND_ACTIVITY_EXTRA);

        tvServerResponse.setText(message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

}
