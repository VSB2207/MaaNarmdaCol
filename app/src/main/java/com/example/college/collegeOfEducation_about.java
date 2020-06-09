package com.example.college;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class collegeOfEducation_about extends AppCompatActivity {
    Button admission,enquiry,gallery;
    ImageView image1,image2,image3,image4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_of_education_about);

        admission=(Button)findViewById(R.id.bt_collegeOfEducation_admission);
        enquiry=(Button)findViewById(R.id.bt_collegeOfEducation_enquiry);

        gallery=(Button)findViewById(R.id.bt_collegeOfEducation_gallery);
        image1=(ImageView)findViewById(R.id.IV_collegeOfEducation_image1);
        image2 =(ImageView)findViewById(R.id.IV_collegeOfEducation_image2);
        image3=(ImageView)findViewById(R.id.IV_collegeOfEducation_image3);
        image4=(ImageView)findViewById(R.id.IV_collegeOfEducation_image4);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
                image4.setVisibility(View.VISIBLE);
            }
        });
        admission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),admission_activity.class);
                startActivity(intent);

            }
        });

        enquiry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),enquiry_activity.class);
                startActivity(intent);
            }
        });
    }}
