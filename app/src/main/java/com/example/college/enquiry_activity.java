package com.example.college;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class enquiry_activity extends AppCompatActivity
{
    Spinner course;
    EditText name,email,contact;
    List<String> arrayList;
    Button submit_btn;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_activity);
        course=(Spinner)findViewById(R.id.sp_enq_spinner_subject);
        name=(EditText)findViewById(R.id.ed_enq_name);
        email=(EditText)findViewById(R.id.ed_enq_email);
        contact=(EditText)findViewById(R.id.ed_enq_contact);
        submit_btn=(Button)findViewById(R.id.bt_enq_submit);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String courseVal,nameVal,emailVal,contactVal;
                courseVal=course.getSelectedItem().toString().trim();
                nameVal=name.getText().toString().trim();
                emailVal=email.getText().toString().trim();
                contactVal=contact.getText().toString().trim();

                if (courseVal.isEmpty() || nameVal.isEmpty()||emailVal.isEmpty()||contactVal.isEmpty())
                {
                    Toast.makeText(enquiry_activity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(courseVal,nameVal,emailVal,contactVal);

                }

            }
        });
        arrayList= new ArrayList<>();

        fetchClass();
    }
    private void register(String courseVal, String nameVal, String emailVal, String contactVal)
    {
        progressDialog= new ProgressDialog(enquiry_activity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        url=url+"enquiry.php?"+"name="+nameVal+"&&email="+emailVal+"&&phone="+contactVal+"&&enquiry_course="+courseVal;
        RequestQueue rq= Volley.newRequestQueue(enquiry_activity.this);
        Log.e("finalURL", url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                Toast.makeText(enquiry_activity.this, "Request Submitted", Toast.LENGTH_SHORT).show();

                Log.e("response:--->", response );


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(enquiry_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(sr);

    }


    private void fetchClass()
    {
        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        url=url+"fetchClasses.php";
        RequestQueue rq= Volley.newRequestQueue(enquiry_activity.this);
        Log.e("finalURL", url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Log.e("response---->" , response );
                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray jr = jo.getJSONArray("result");


                    for (int i = 0; i <jr.length() ; i++)
                    {
                        JSONObject jn = jr.getJSONObject(i);
                        arrayList.add(jn.getString("class"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(enquiry_activity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course.setAdapter(adapter);

                }
                catch (JSONException e)
                {

                }

                Log.e("response:--->", response );


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(enquiry_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(sr);


    }


}
