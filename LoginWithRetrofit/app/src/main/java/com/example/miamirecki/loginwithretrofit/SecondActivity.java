package com.example.miamirecki.loginwithretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

}
