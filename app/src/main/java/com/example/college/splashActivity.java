package com.example.college;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class splashActivity extends AppCompatActivity
{

String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
        name=preferences.getString("name",null);



        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {


                String nameVal=name;
             if (nameVal==null)
             {
              Intent intent  = new Intent(splashActivity.this,about.class);
                startActivity(intent);
                finish();
                }
             else{
                 Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(intent);
                 finish();
             }

            }
        },2000);
    }
}
