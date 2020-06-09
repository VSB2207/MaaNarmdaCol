package com.example.college;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.appcompat.app.AlertDialog;
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

public class sign_up extends AppCompatActivity
{
    EditText ed_name,ed_mobileno,ed_username,ed_password;
    Button bt_submit;
    TextView tv_login;
    Spinner spinner_clg,classes;
    String collegVal="",courseVal="";
    String academyUrl="http://robotbook247.com/narmda/narmdaAPI/MaNarmdaAcademyAPI/"
            ,highschoolUrl="http://robotbook247.com/narmda/narmdaAPI/marmdahighschoolAPI/"
            ,collegeofEducationUrl="http://robotbook247.com/narmda/narmdaAPI/NarmdacollegeofeducationAPI/";

    String class_url_academy="http://robotbook247.com/narmda/narmdaAPI/MaNarmdaAcademyAPI/fetchClasses.php"
            ,class_url_highSchool="http://robotbook247.com/narmda/narmdaAPI/marmdahighschoolAPI/fetchClasses.php"
            ,class_url_collegeOfEducation="http://robotbook247.com/narmda/narmdaAPI/NarmdacollegeofeducationAPI/fetchClasses.php";  String final_url;
    ProgressBar progressBar;
    List<String> classArray;
    ProgressDialog progressDialog ;
    String nameVal="",mobileVal="",usernameVal="",passwordVal="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ed_name=(EditText)findViewById(R.id.ed_reg_name);
        ed_mobileno=(EditText)findViewById(R.id.ed_reg_Mobileno);
        ed_username=(EditText)findViewById(R.id.ed_reg_username);
        ed_password=(EditText)findViewById(R.id.ed_reg_password);
        bt_submit=(Button)findViewById(R.id.bt_reg_submit);
        tv_login=(TextView)findViewById(R.id.tv_login);
        spinner_clg=(Spinner)findViewById(R.id.spinner_subject);
        classes=(Spinner)findViewById(R.id.spinner_subject_college);
        classArray= new ArrayList<>();
        classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                courseVal=classes.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressBar=(ProgressBar)findViewById(R.id.progressBarSignup);

        spinner_clg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               // Toast.makeText(sign_up.this, spinner.getSelectedItem().toString().trim(), Toast.LENGTH_SHORT).show();
                collegVal=spinner_clg.getSelectedItem().toString();
                collegVal.trim();


                if (collegVal.equals("Choose Your Instituition"))
                {
                    Toast.makeText(sign_up.this, "Please Choose Instituition Name", Toast.LENGTH_SHORT).show();
                }
                else if (collegVal.equals("Maa_Narmada_Academy"))
                {
                    fetchClass(class_url_academy);


                }
                else if (collegVal.equals("Maa_Narmada_College_Of_Education"))
                {
                    fetchClass(class_url_collegeOfEducation);

                }
                else
                {
                    fetchClass(class_url_highSchool);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nameVal=ed_name.getText().toString().trim();
                usernameVal=ed_username.getText().toString().trim();
                passwordVal=ed_password.getText().toString().trim();
                mobileVal=ed_mobileno.getText().toString().trim();
                if (collegVal.equals("Choose Your College"))
                {
                    Toast.makeText(getApplicationContext(), "Please Choose Your College Name", Toast.LENGTH_SHORT).show();
                }
                else if (collegVal.equals("Maa_Narmada_Academy"))
                {
                    final_url=academyUrl;
                }
                else if(collegVal.equals("Maa_Narmada_College_Of_Education"))
                {
                    final_url=collegeofEducationUrl;
                }
                else
                {
                    final_url=highschoolUrl;
                }


                if (nameVal.isEmpty() || usernameVal.isEmpty() ||passwordVal.isEmpty() || mobileVal.isEmpty() || collegVal.isEmpty() || courseVal.isEmpty())
                {
                    Toast.makeText(sign_up.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                register();
                }
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(sign_up.this,login_activity.class);
                startActivity(intent);
                finish();
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


                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray jr = jo.getJSONArray("result");


                    for (int i = 0; i <jr.length() ; i++)
                    {
                        JSONObject jn = jr.getJSONObject(i);
                        classArray.add(jn.getString("class"));
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, classArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        classes.setAdapter(adapter);

                    }

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


    public void register()
    {
        progressDialog = new ProgressDialog(sign_up.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("loading..");

        progressDialog.show();
        String nameVal=ed_name.getText().toString();
        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        String url = final_url+"/sign_up.php?name="+nameVal+"&&email="+ed_username.getText().toString()
                +"&&phone="+ed_mobileno.getText().toString()+"&&course="+courseVal+"&&pass="+ed_password.getText().toString()+"&&clg="+collegVal;
  //      Log.e("finalURL", url );
//        Log.e("originalURL", final_url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();

                Log.e("response:--->", response );
                if (response.equals("Email Already Exist, Please Enter Another Email."))
                {
                    Toast.makeText(sign_up.this, "Email Already Exist, Please Enter Another Email.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor editor= getSharedPreferences("Login",MODE_PRIVATE).edit();
                    editor.putString("email",ed_username.getText().toString().trim());
                    editor.putString("name",ed_name.getText().toString().trim());
                    editor.putString("class",courseVal);
                    editor.putString("clg",collegVal);
                    editor.putString("URL",final_url);
                    editor.apply();
                    Intent intent = new Intent(sign_up.this,MainActivity.class);
                    startActivity(intent);
                    finish();
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
//    public void erroralertdialog()
//    {
//        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
//
//        // Setting Dialog Title
//        alertDialog.setTitle("Error");
//
//        // Setting Dialog Message
//        alertDialog.setMessage(" Empty Value are not Allowed. / Something went wrong.");
//
//        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.iconserror);
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//
//
//        // Setting OK Button
//        alertDialog.setButton("OK", new DialogInterface.OnClickListener()
//        {
//            public void onClick(DialogInterface dialog, int which)
//            {
//                // Write your code here to execute after dialog closed
//                //Intent i = new Intent(this);
//                // startActivity(i);
//                //  finish();
//
//                //Toast.make Text(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
//
//    }
}
