package com.example.college;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class admission_activity extends AppCompatActivity
{
    private static final int PERMISSION_REQUEST_CODE =1234 ;
    EditText name, email, dob, address, phone, fatherName, motherName, city, state, zip_code;
    Spinner course;

    Spinner gender;
    Button submit_btn;
    final Calendar myCalendar = Calendar.getInstance();
    ProgressDialog progressDialog;
    String encode_marksheet="",encode_migration="",encode_tc="";
    private static final int CHOOSE_IMAGE=101;
    Uri profileImage;
    ImageView IV_marksheet,IV_migration_certificate,IV_TC;
    String typeOfImage=" ";
    Button bt_marksheet,bt_migration,bt_tc;
    String nameVal, emailVal, addressVal, dobVal, phoneVal, genderVal, fathernameval, motherNameVal, cityVal, stateVal, zip_codeval, courseVal;

    List<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_activity);

        name = (EditText) findViewById(R.id.ed_adm_name);
        email = (EditText)findViewById(R.id.ed_adm_email);
        address = (EditText)findViewById(R.id.ed_adm_address);
        dob = (EditText) findViewById(R.id.ed_adm_dob);
        phone = (EditText) findViewById(R.id.ed_adm_mobileno);
        gender = (Spinner) findViewById(R.id.sp_adm_gender);
        fatherName = (EditText)findViewById(R.id.ed_adm_fathername);
        motherName = (EditText)findViewById(R.id.ed_adm_mothername);
        city = (EditText)findViewById(R.id.ed_adm_city);
        state = (EditText)findViewById(R.id.ed_adm_state);
        zip_code = (EditText)findViewById(R.id.ed_adm_zip_code);
        course = (Spinner) findViewById(R.id.ed_adm_course);
        IV_marksheet=(ImageView)findViewById(R.id.IV_admission_marksheet);
        IV_migration_certificate=(ImageView)findViewById(R.id.IV_admission_migration_certificate);
        IV_TC=(ImageView)findViewById(R.id.IV_admission_TC);
        bt_marksheet=(Button)findViewById(R.id.bt_adm_marksheet);
        bt_migration=(Button)findViewById(R.id.bt_adm_migration_certificate);
        bt_tc=(Button)findViewById(R.id.bt_adm_tc);
        fetchClass();
        arrayList = new ArrayList<>();

        bt_marksheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission())
                {
                    imageChooser();
                    typeOfImage="A";

                }
                else
                {
                    requestPermission();
                }

            }
        });
        bt_migration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission())
                {
                    imageChooser();
                    typeOfImage="B";

                }
                else
                {
                    requestPermission();
                }


            }
        });
        bt_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission())
                {
                    imageChooser();
                    typeOfImage="C";

                }
                else
                {
                    requestPermission();
                }


            }
        });



        submit_btn = (Button) findViewById(R.id.bt_adm_submit);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                courseVal = course.getSelectedItem().toString().trim();
                if (nameVal.isEmpty() || emailVal.isEmpty() || addressVal.isEmpty() || dobVal.isEmpty() || phoneVal.isEmpty() || genderVal.isEmpty() || fathernameval.isEmpty() || motherNameVal.isEmpty()
                        || cityVal.isEmpty() || stateVal.isEmpty() || zip_codeval.isEmpty() || courseVal.isEmpty() || encode_marksheet.isEmpty() || encode_migration.isEmpty() || encode_tc.isEmpty() )
                {
                    Toast.makeText(admission_activity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
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
                new DatePickerDialog(admission_activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    private void register( final String nameVal, final String emailVal, final String addressVal, final String dobVal, final String phoneVal, final String genderVal, final String fathernameval, final String motherNameVal, final String cityVal, final String stateVal, final String zip_codeval, final String courseVal) {
        progressDialog= new ProgressDialog(admission_activity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        url=url+"admission_form.php";
        RequestQueue rq = Volley.newRequestQueue(admission_activity.this);
        Log.e("finalURLLogin--->", url);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e("responseFromApi", response );
                Toast.makeText(admission_activity.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                if (response.equals("done"))
                {

                    progressDialog.dismiss();

                    Intent  intent = new Intent(admission_activity.this,paymentgatway.class);
                    startActivity(intent);
                    finish();


                }
                else
                {
                    Toast.makeText(admission_activity.this, "Some technical erorr!! try again", Toast.LENGTH_SHORT).show();
                }

                init();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
               // Toast.makeText(admission_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hm1 = new HashMap<String, String>();

                hm1.put("name",nameVal);
                hm1.put("fathername",fathernameval);
                hm1.put("mothername",motherNameVal);
                hm1.put("email",emailVal);
                hm1.put("dob",dobVal);
                hm1.put("gender",genderVal);
                hm1.put("city",cityVal);
                hm1.put("address",addressVal);
                hm1.put("state",stateVal);
                hm1.put("zip_code",zip_codeval);
                hm1.put("phone",phoneVal);
                hm1.put("course",courseVal);
                hm1.put("migration",encode_migration);
                hm1.put("marksheet",encode_marksheet);
                hm1.put("tc",encode_tc);

                return hm1;

            }
        };

        rq.add(sr);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            profileImage=data.getData();

            if (typeOfImage.equals("A"))
            {
                try
                {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),profileImage);
                    IV_marksheet.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // In case you want to compress your image, here it's at 40%
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    encode_marksheet= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
                    Toast.makeText(admission_activity.this, "bhai marksheet chal rha h", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            else if (typeOfImage.equals("B"))
            {

                try
                {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),profileImage);
                    IV_migration_certificate.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // In case you want to compress your image, here it's at 40%
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    encode_migration= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
                    Toast.makeText(admission_activity.this, "bhai migration certificate chal rha h", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {

                try
                {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),profileImage);
                    IV_TC.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // In case you want to compress your image, here it's at 40%
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    encode_tc= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

                    Toast.makeText(admission_activity.this, "bhai tc chsl rha h ", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

        }


    }



    private void imageChooser()
    {
        Intent photointent = new Intent();
        photointent.setType("image/*");
        photointent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photointent,"Select profile Image"),CHOOSE_IMAGE);

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

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String s, DialogInterface.OnClickListener onClickListener) {

    }

//    public void clickphoto(View view) {
//        checkPermission();
//        AlertDialog.Builder dailog=new AlertDialog.Builder(this); // it  is  use for Conformation dialog box...
//        LayoutInflater inflater=this.getLayoutInflater(); // Layout inflater are use the show the custom box of the conformation box..
//        View dailogview=inflater.inflate(R.layout.customcamera,null);
//        dailog.setView(dailogview);
//        final AlertDialog alertDialog=dailog.create();
//        alertDialog.show();
//
//        camera=alertDialog.findViewById(R.id.camera);
//        gallerys=alertDialog.findViewById(R.id.gallery);
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,0);
//                //alertDialog.dismiss();
//            }
//        });
//        gallerys.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent  takeimage = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(takeimage,1);
//            }
//        });
//
//    }
private void fetchClass()
{
    SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
    String url = preferences.getString("URL", null);
    url=url+"fetchClasses.php";
    RequestQueue rq= Volley.newRequestQueue(admission_activity.this);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(admission_activity.this, android.R.layout.simple_spinner_item, arrayList);
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
            Toast.makeText(admission_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    rq.add(sr);


}






//    public  String imagetostring(Bitmap bitmap)
//    {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//        byte[] imgBytes = byteArrayOutputStream.toByteArray();
//        // return byteArrayOutputStream.toByteArray();
//        String encodedImage = Base64.encodeToString(imgBytes, Base64.DEFAULT);
//        // return Base64.encodeToString(imgBytes,Base64.DEFAULT);
//        return encodedImage;
//    }




}
