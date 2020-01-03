package com.example.entertainmentcab.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.entertainmentcab.R;

public class FirstActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }
    public void btnRegistration_click(View v)
    {
        Intent i = new Intent(FirstActivity.this,RegistrationActivity.class);
        startActivity(i);
    }
    public void btnLogin_click(View v)
    {
        Intent i = new Intent(FirstActivity.this,LoginActivity.class);
        startActivity(i);
    }
}
