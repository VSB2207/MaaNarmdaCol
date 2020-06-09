package com.example.college;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class forgotPassword extends AppCompatActivity
{
    EditText username;
    String usernameVal="",courseVal="",collegeVal="";
    Spinner college;
    Button bt_submit;
    ProgressDialog progressDialog;
    String academyUrl="http://ninfocom.co.in/narmada/narmdaAPI/MaNarmdaAcademyAPI/"
            ,highschoolUrl="http://ninfocom.co.in/narmada/narmdaAPI/marmdahighschoolAPI/"
            ,collegeofEducationUrl="http://ninfocom.co.in/narmada/narmdaAPI/NarmdacollegeofeducationAPI/";
    String finalUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        username=(EditText)findViewById(R.id.ed_frgt_username);

        college=(Spinner)findViewById(R.id.sp_frgt_college_name);

        bt_submit=(Button)findViewById(R.id.bt_frgt_submit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                usernameVal=username.getText().toString().trim();
                collegeVal=college.getSelectedItem().toString().trim();
                if (collegeVal.equals("Choose Your Instituition"))
                {
                    Log.e("TAG", "onItemSelected: " );
                }
                else if (collegeVal.equals("Maa_Narmada_Academy"))
                {

                    finalUrl=academyUrl;
                }
                else if (collegeVal.equals("Maa_Narmada_High_School"))
                {
                        finalUrl=highschoolUrl;
                }
                else if (collegeVal.equals("Maa_Narmada_college_of_education"))
                {
                    finalUrl=collegeofEducationUrl;
                }
                if (usernameVal.isEmpty() || collegeVal.isEmpty() )
                {
                    Toast.makeText(forgotPassword.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(usernameVal,collegeVal,finalUrl);
                }


            }
        });

    }

    private void register(final String usernameVal, String collegeVal, String finalUrl)
    {
        progressDialog = new ProgressDialog(forgotPassword.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("loading..");

        progressDialog.show();
        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        String url = finalUrl+"forgot_password.php?email="+usernameVal;
        //      Log.e("finalURL", url );
//        Log.e("originalURL", final_url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();

                Log.e("response:--->", response );
                if (response.equals("Email Not Exist"))
                {
                    Toast.makeText(forgotPassword.this, "No record found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   emailsend();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(sr);


    }

    public  void emailsend()
    {
        String Email=usernameVal;
        String Subject="Forgot Password";
        String Message="Here you will update your password"+"\n"+"http://ninfocom.co.in/sos/studentupdate/academy/studentupdate.php";

        javaMailAPI javaMailAPI=new javaMailAPI(this,Email,Subject,Message);
        javaMailAPI.execute();

        Toast.makeText(this, "Please See Your Registered Email", Toast.LENGTH_SHORT).show();
    }

}
