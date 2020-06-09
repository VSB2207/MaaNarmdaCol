package com.example.college;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



/**
 * A simple {@link Fragment} subclass.
 */
public class enquiry_frag extends Fragment
{

    Spinner course;
    EditText name,email,contact;
    List<String> arrayList;
    Button submit_btn;
    ProgressDialog progressDialog;
    public enquiry_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enquiry_frag, container, false);
        course=(Spinner)view.findViewById(R.id.sp_enq_spinner_subject);
        name=(EditText)view.findViewById(R.id.ed_enq_name);
        email=(EditText)view.findViewById(R.id.ed_enq_email);
        contact=(EditText)view.findViewById(R.id.ed_enq_contact);
        submit_btn=(Button)view.findViewById(R.id.bt_enq_submit);

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
                    Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(courseVal,nameVal,emailVal,contactVal);

                }

            }
        });
        arrayList= new ArrayList<>();

        fetchClass();


        return view;
    }

    private void register(String courseVal, String nameVal, String emailVal, String contactVal)
    {
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        url=url+"enquiry.php?"+"name="+nameVal+"&&email="+emailVal+"&&phone="+contactVal+"&&enquiry_course="+courseVal;
        RequestQueue rq= Volley.newRequestQueue(getContext());
        Log.e("finalURL", url );
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Request Submitted", Toast.LENGTH_SHORT).show();

                Log.e("response:--->", response );


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(sr);

    }


    private void fetchClass()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        url=url+"fetchClasses.php";
        RequestQueue rq= Volley.newRequestQueue(getContext());
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(sr);


    }


}

