package com.example.college;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class sucessPayment extends AppCompatActivity {

    TextView successmsg;
    RequestQueue requestQueue;
    String name,email,phone,address,ammount,transcationID;
    ImageView successpaymentimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess_payment);
        successmsg=findViewById(R.id.successmsg);
        requestQueue= Volley.newRequestQueue(this);
        successpaymentimage= findViewById(R.id.sucesspay);
        // this is the for the aimate the  success animation image view..
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        successpaymentimage.startAnimation(animation);
        successmsg.startAnimation(animation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation((float) 10.0);
        actionBar.setTitle("Payment  Status.");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#c33764"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        Intent intent=getIntent();
        name=intent.getStringExtra("name");
       email=intent.getStringExtra("email");
         phone=intent.getStringExtra("contact");
        address=intent.getStringExtra("address");
//        Toast.makeText(this, ""+ammount, Toast.LENGTH_SHORT).show();
         transcationID = intent.getStringExtra("transid");

//        setdata.setText(name+"\n\n"+email+"\n\n"+transcationID+"\n\n"+address+"\n\n"+phone+"\n\n"+ammount+"\n\n");
//         pending data fetch
//

        // get the value

        SharedPreferences getshared = getSharedPreferences("feeamout",MODE_PRIVATE);

        final SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
        String url=preferences.getString("URL",null);
        ammount=getshared.getString("fees","0");
        ////
//
//        Toast.makeText(this, ""+ammount, Toast.LENGTH_SHORT).show();

        // this is the  API for the send the details to the server..

        //API..............

        url=url+"paymetndetails.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

//                        Toast.makeText(sucessPayment.this, ""+ServerResponse, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        ///erroralertdialog();
//                        Toast.makeText(sucessPayment.this, ""+v, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);
                params.put("Payment_Amount", ammount);
                params.put("Payment_RefNo", transcationID);
                params.put("course",preferences.getString("class",null)); // enter  here the  course name..... using your shareprefences ...
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(sucessPayment.this);
        requestQueue.add(stringRequest);
    }
}
