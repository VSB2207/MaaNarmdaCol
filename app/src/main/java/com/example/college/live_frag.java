package com.example.college;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class live_frag extends Fragment
{
    RequestQueue rq;
    TextView errorvideo;
    Button lvbtn;


    public live_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     final View view=inflater.inflate(R.layout.fragment_live_frag, container, false);

        rq= Volley.newRequestQueue(getContext());
        lvbtn=(Button)view.findViewById(R.id.livebtn);
        errorvideo=(TextView)view.findViewById(R.id.errotvideo);

        lvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinclass();
            }
        });
         return view;
    }

    public void joinclass() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String urls = preferences.getString("URL", null);
        String courseVal = preferences.getString("class", null);

        urls = urls + "liveAPI.php?class=" + courseVal;
        // pending data fetch

        Log.e("liveURL", urls );
        RequestQueue rq = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e("response", response );

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jo = new JSONObject(response);
                        JSONArray jr = jo.getJSONArray("result");
                        JSONObject jn = jr.getJSONObject(i);
                        String urlvideo = jn.getString("Video_url");


                        if (urlvideo== "")
                        {

                            Toast.makeText(getContext(), "demo toast", Toast.LENGTH_SHORT).show();

                        } else
                            {

                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlvideo));
                                startActivity(intent);
                                Toast.makeText(getContext(), "" + urlvideo, Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {

                                Toast.makeText(getContext(), "Live Class are ended", Toast.LENGTH_SHORT).show();
                                lvbtn.setVisibility(View.GONE);
                                errorvideo.setText("Live class are ended0");
                            }
                        }
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
        rq.add(sr);

    }
}
