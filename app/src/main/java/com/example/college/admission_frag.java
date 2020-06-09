package com.example.college;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class admission_frag extends Fragment {

    EditText name, email, dob, address, phone, fatherName, motherName, city, state, zip_code, course;
    Spinner gender;
    Button submit_btn;
    final Calendar myCalendar = Calendar.getInstance();

    ProgressDialog progressDialog;
    public admission_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admission_frag, container, false);
        name = (EditText) view.findViewById(R.id.ed_adm_name);
        email = (EditText) view.findViewById(R.id.ed_adm_email);
        address = (EditText) view.findViewById(R.id.ed_adm_address);
        dob = (EditText) view.findViewById(R.id.ed_adm_dob);
        phone = (EditText) view.findViewById(R.id.ed_adm_mobileno);
        gender = (Spinner) view.findViewById(R.id.sp_adm_gender);
        fatherName = (EditText) view.findViewById(R.id.ed_adm_fathername);
        motherName = (EditText) view.findViewById(R.id.ed_adm_mothername);
        city = (EditText) view.findViewById(R.id.ed_adm_city);
        state = (EditText) view.findViewById(R.id.ed_adm_state);
        zip_code = (EditText) view.findViewById(R.id.ed_adm_zip_code);
        course = (EditText) view.findViewById(R.id.ed_adm_course);

        submit_btn = (Button) view.findViewById(R.id.bt_adm_submit);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameVal, emailVal, addressVal, dobVal, phoneVal, genderVal, fathernameval, motherNameVal, cityVal, stateVal, zip_codeval, courseVal;
                nameVal = name.getText().toString().trim();
                emailVal = email.getText().toString().trim();
                addressVal = address.getText().toString().trim();
                dobVal = dob.getText().toString().trim();
                phoneVal = phone.getText().toString().trim();
                genderVal = gender.getSelectedItem().toString().trim();
                fathernameval = fatherName.getText().toString().trim();
                motherNameVal = motherName.getText().toString().trim();
                cityVal = city.getText().toString().trim();
                stateVal = state.getText().toString().trim();
                zip_codeval = zip_code.getText().toString().trim();
                courseVal = course.getText().toString().trim();
                if (nameVal.isEmpty() || emailVal.isEmpty() || addressVal.isEmpty() || dobVal.isEmpty() || phoneVal.isEmpty() || genderVal.isEmpty() || fathernameval.isEmpty() || motherNameVal.isEmpty()
                        || cityVal.isEmpty() || stateVal.isEmpty() || zip_codeval.isEmpty() || courseVal.isEmpty() )
                {
                    Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(nameVal, emailVal, addressVal, dobVal, phoneVal, genderVal, fathernameval, motherNameVal, cityVal, stateVal, zip_codeval, courseVal);
                }


            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    private void register(String nameVal, String emailVal, String addressVal, String dobVal, String phoneVal, String genderVal, String fathernameval, String motherNameVal, String cityVal, String stateVal, String zip_codeval, String courseVal) {
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        url = url + "admission_form.php?" + "name=" + nameVal + "&&fathername=" + fathernameval + "&&mothername=" + motherNameVal + "&&email=" + emailVal + "&&dob=" + dobVal + "&&gender="
                + genderVal + "&&city=" + cityVal + "&&address=" + addressVal + "&&state=" + stateVal + "&&zip_code=" + zip_codeval + "&&phone=" + phoneVal + "&&course=" + courseVal;
        RequestQueue rq = Volley.newRequestQueue(getContext());
        Log.e("finalURLLogin--->", url);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Request Submitted", Toast.LENGTH_SHORT).show();
                init();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                // Toast.makeText(third.this, "DATA NOT REGISTER", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hm1 = new HashMap<String, String>();

//                hm1.put("email",username_val);
//                hm1.put("pass",password_val);
                return hm1;

            }
        };

        rq.add(sr);


    }

    private void init()
    {
        name.setText(null);
        email.setText(null);
        dob.setText(null);
         address.setText(null);
         phone.setText(null);
         fatherName.setText(null);
         motherName.setText(null);
         city.setText(null);
         state.setText(null);
         zip_code.setText(null);
          course.setText(null);

    }

}