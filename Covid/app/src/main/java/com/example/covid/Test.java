package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.BulletSpan;

public class Test extends AppCompatActivity {

    String token;
    String tokenRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        token = extras.getString("token");
        tokenRefresh = extras.getString("token_refresh");


    }
}