package com.example.college;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class paymentgatway extends AppCompatActivity implements PaymentResultListener {
    Button paybtn;
    EditText name,email,phone,address;
    String success_id;
    int realvalue;
    TextView amountview;
    int feeamount=0;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentgatway);
        paybtn=(Button)findViewById(R.id.paybtn);
        Checkout.preload(getApplicationContext());
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        requestQueue= Volley.newRequestQueue(this);
        address=findViewById(R.id.address);
        // this is the clickable
        amountview=findViewById(R.id.amountview);
        /// fetch the amount from the database.......
        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
                String urls=preferences.getString("URL",null);
            urls=urls+"feeamount.php";
        Log.e("TAG", urls );
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urls, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.e("feeResponse", String.valueOf(response));
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        String rate=jsonObject.getString("feeamount");
                            feeamount= Integer.parseInt(rate);
                            // this will  be alwayes updated ...
                            SharedPreferences shrd = getSharedPreferences("feeamout",MODE_PRIVATE);
                            SharedPreferences.Editor editor = shrd.edit();

                            editor.putString("fees",rate);
                            editor.apply();
                             // this  will be commited
                            try {
                                amountview.setText("amount :"+feeamount);


                            }
                            catch (Exception e)
                            {
                                amountview.setText("Amount :"+feeamount);
                            //    paymentAmount.setAmount(rate);

                            }
//                       total.setText(pending);
//                        leftinstall.setText("you have"+" "+pending+" "+ "installation left");//pending
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
        ///// end the fetching  the amount rate from the database..
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startPayment();
                check_input();
            }
        });
    }

    void check_input()
    {

        if(name.getText().toString().isEmpty())
        {
            name.setError("Name is Empty");
            name.requestFocus();
        }
        else if(email.getText().toString().isEmpty())
        {
            email.setError("Name is Empty");
            email.requestFocus();
        }
        else  if(phone.getText().toString().isEmpty())
        {
            phone.setError("Name is Empty");
            phone.requestFocus();
        }
        else  if(address.getText().toString().isEmpty())
        {
            address.setError("Name is Empty");
            address.requestFocus();
        }
        else
        {
            startPayment();
        }
    }
    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.maa);
          final Activity activity = this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "MAA Narmada College of education");
            options.put("currency", "INR");
            realvalue=feeamount;
            int ammount= realvalue*100;
            options.put("amount", ammount);
            checkout.open(activity, options);

            JSONObject preFill= new JSONObject();
            preFill.put("email",email);
            preFill.put("phone",phone);
            options.put("preFill",preFill);
        } catch(Exception e) {
            Toast.makeText(activity, "Something Error,  please try Again", Toast.LENGTH_SHORT).show();
//            return false;
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorPayId) {
        success_id=razorPayId;
        Toast.makeText(this, "Payment done Successfully.", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(paymentgatway.this,sucessPayment.class);

        String names=name.getText().toString().trim();
        String phones=phone.getText().toString().trim();
        String emails= email.getText().toString().trim();
        String addresses=address.getText().toString().trim();


        //  here transfer the data into the another screen..
        intent.putExtra("transid",success_id);
        intent.putExtra("name", names);
        intent.putExtra("contact",phones);
        intent.putExtra("email", emails);

        intent.putExtra("fees",feeamount);
        intent.putExtra("address", addresses);
        startActivity(intent);
        finish();
    }
    @Override
    public void onPaymentError(int i, String s) {
        // AlertMessage("Your Payment Failed ..");
        Toast.makeText(this, "payment failed... ", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this,failurePayment.class);
        startActivity(intent);
        finish();
    }
}