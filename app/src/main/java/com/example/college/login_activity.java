package com.example.college;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class login_activity extends AppCompatActivity
{
    EditText ed_username,ed_password;
    Button bt_submit;
    TextView tv_register;
    String courseVal="",collegeVal,username_val,password_val,final_url="";
    Spinner clg_name,course;

    String academyUrl="http://robotbook247.com/narmda/narmdaAPI/MaNarmdaAcademyAPI/"
            ,highschoolUrl="http://robotbook247.com/narmda/narmdaAPI/marmdahighschoolAPI/"
            ,collegeofEducationUrl="http://robotbook247.com/narmda/narmdaAPI/NarmdacollegeofeducationAPI/";

    ProgressBar progressBar;
    String class_url_academy="http://robotbook247.com/narmda/narmdaAPI/MaNarmdaAcademyAPI/fetchClasses.php"
            ,class_url_highSchool="http://robotbook247.com/narmda/narmdaAPI/marmdahighschoolAPI/fetchClasses.php"
            ,class_url_collegeOfEducation="http://robotbook247.com/narmda/narmdaAPI/NarmdacollegeofeducationAPI/fetchClasses.php";
    List<String> classArray;
    TextView frgtPassword;

    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        ed_username=(EditText)findViewById(R.id.editTextEmail);
        ed_password=(EditText)findViewById(R.id.editTextPassword);
        bt_submit=(Button)findViewById(R.id.cirLoginButton);
        tv_register=(TextView)findViewById(R.id.tv_register);
        classArray= new ArrayList<>();
        frgtPassword=(TextView)findViewById(R.id.TV_login_frgtpassword);
        frgtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(login_activity.this,forgotPassword.class);
                startActivity(intent);
                finish();

            }
        });
        clg_name=(Spinner)findViewById(R.id.spinner_login_college);
        course=(Spinner)findViewById(R.id.spinner_login_course);

        clg_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                collegeVal=clg_name.getSelectedItem().toString();

                if (collegeVal.equals("Choose Your Instituition"))
                {
                    Log.e("TAG", "onItemSelected: " );
                  }
                else if (collegeVal.equals("Maa_Narmada_Academy"))
                {
                    fetchClass(class_url_academy);


                }
                else if (collegeVal.equals("Maa_Narmada_High_School"))
                {
                    fetchClass(class_url_highSchool);

                }
                else if (collegeVal.equals("Maa_Narmada_college_of_education"))
                {
                    fetchClass(class_url_collegeOfEducation);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                courseVal=course.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressBar=findViewById(R.id.pd_login);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(login_activity.this,sign_up.class);
                startActivity(intent);
                finish();

            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (collegeVal.equals("Choose"))
                {
                    Toast.makeText(login_activity.this, "Please Choose Your College Name", Toast.LENGTH_SHORT).show();
                }
                else if (collegeVal.equals("Maa_Narmada_Academy"))
                {
                    final_url=academyUrl;

                }

                else if(collegeVal.equals("Maa_Narmada_College_Of_Education"))
                {
                    final_url=collegeofEducationUrl;

                }
                else
                    {
                        final_url=highschoolUrl;

                    }


                username_val=ed_username.getText().toString();
                password_val=ed_password.getText().toString();
                if (username_val.equals(null))
                {
                    ed_username.setError("Empty");
                    ed_username.setFocusable(true);
                }
                else if (password_val.equals(null))
                {
                    ed_password.setError("Empty");
                    ed_password.setFocusable(true);
                }
                else if (collegeVal.equals(null))
                {
                    Toast.makeText(login_activity.this, "Please select one college", Toast.LENGTH_SHORT).show();
                }
                else if (courseVal.equals(null)){
                    Toast.makeText(login_activity.this, "Please Choose Course", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Login(username_val,password_val,collegeVal,courseVal);

                }
                           }
        });

    }

    private void fetchClass(String value)
    {
        classArray.clear();
        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        String url = value;
        Log.e("finalURL", url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray jr = jo.getJSONArray("result");


                    for (int i = 0; i <jr.length() ; i++)
                    {
                        JSONObject jn = jr.getJSONObject(i);
                        classArray.add(jn.getString("class"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, classArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course.setAdapter(adapter);

                }
                catch (JSONException e)
                {

                }

                Log.e("response:--->", response );
                if (response.equals("done"))
                {
                    SharedPreferences.Editor editor= getSharedPreferences("",MODE_PRIVATE).edit();
                    editor.putString("email",highschoolUrl);
                    editor.apply();

                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(sr);

    }

    private void Login(final String username_val, final String password_val, final String collegeVal, final String courseVal)
    {
        progressDialog = new ProgressDialog(login_activity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("loading..");
        progressDialog.show();

        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        String url =final_url+"login.php"+"?email="+username_val+"&&pass="+password_val+"&&course="+courseVal+"&&clg="+collegeVal ;
        Log.e("finalURLLogin--->", url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                try
                {
                    JSONObject jo = new JSONObject(response);
                    JSONArray jr = jo.getJSONArray("result");
                    JSONObject jn = jr.getJSONObject(0);
                    String name=jn.getString("Name");
                    String email=jn.getString("email");
                    String phone=jn.getString("phone");
                    String class_sem=jn.getString("class");


                    if (name.equals("null"))
                    {
                        Toast.makeText(login_activity.this, "Wrong Details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(login_activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        SharedPreferences.Editor editor= getSharedPreferences("Login",MODE_PRIVATE).edit();
                        editor.putString("URL",final_url);
                        editor.putString("email",email);
                        editor.putString("name",name);
                        editor.putString("phone",phone);
                        editor.putString("class",class_sem);
                        editor.apply();

                    }

                }
                catch (JSONException e)
                {
                    Toast.makeText(login_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            //    Toast.makeText(getApplicationContext(), "DATA REGISTER", Toast.LENGTH_SHORT).show();
                Log.e("finalURL:---->", final_url );
                Log.e("response:-->", response );
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(login_activity.this, "Some Technical Error!! Try Again", Toast.LENGTH_SHORT).show();
                // Toast.makeText(third.this, "DATA NOT REGISTER", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hm1=new HashMap<String,String>();

                hm1.put("email",username_val);
                hm1.put("pass",password_val);
                return hm1;

            }
        };

        rq.add(sr);
    }


}
