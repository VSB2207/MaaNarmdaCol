package com.example.college;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class about extends AppCompatActivity
{
    ImageView academy,highSchool,educationCollege;
    Button login;
    String academyUrl="http://robotbook247.com/narmda/narmdaAPI/MaNarmdaAcademyAPI/"
            ,highschoolUrl="http://robotbook247.com/narmda/narmdaAPI/marmdahighschoolAPI/"
            ,collegeofEducationUrl="http://robotbook247.com/narmda/narmdaAPI/NarmdacollegeofeducationAPI/";
    String finalUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        academy=(ImageView)findViewById(R.id.school_about);
        highSchool=(ImageView)findViewById(R.id.mahavidhyalay_about);
        educationCollege=(ImageView)findViewById(R.id.college_education);
        login=(Button)findViewById(R.id.bt_about_login);
        academy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(about.this,academy_about.class);
                startActivity(intent);

                finalUrl=academyUrl;
                SharedPreferences.Editor editor= getSharedPreferences("Login",MODE_PRIVATE).edit();
                editor.putString("URL",finalUrl);
                editor.apply();

            }
        });

        highSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(about.this,school_about.class);
                startActivity(intent);
                finalUrl=highschoolUrl;
                SharedPreferences.Editor editor= getSharedPreferences("Login",MODE_PRIVATE).edit();
                editor.putString("URL",finalUrl);
                editor.apply();


            }
        });

        educationCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(about.this,collegeOfEducation_about.class);
                startActivity(intent);

                finalUrl=collegeofEducationUrl;

                SharedPreferences.Editor editor= getSharedPreferences("Login",MODE_PRIVATE).edit();
                editor.putString("URL",finalUrl);
                editor.apply();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(about.this,login_activity.class);
                startActivity(intent);

            }
        });


    }
}
