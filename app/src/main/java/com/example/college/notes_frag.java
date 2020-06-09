package com.example.college;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class notes_frag extends Fragment {


    ListView listView;
    List<modelClassOfPdf> dataList;
    public notes_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notes_frag, container, false);

        listView=view.findViewById(R.id.ListViewPdf);
        dataList= new ArrayList<>();
//
//        dataList.add(new modelClassOfPdf("ABCD","ABCD.pdf"));
//        dataList.add(new modelClassOfPdf("ABCDE","ABCDE.pdf"));
//        dataList.add(new modelClassOfPdf("ABCDEF","ABCDEF.pdf"));
//        dataList.add(new modelClassOfPdf("ABCDEFG","https://www.tutorialspoint.com/css/css_tutorial.pdf"));

        fetchPdf();
        return view;
    }

    private void fetchPdf()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        String courseVal=preferences.getString("class",null);
        url=url+"pdfView.php?&&class="+courseVal;
        RequestQueue rq = Volley.newRequestQueue(getContext());
        Log.e("finalURLPdf--->", url);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                String pdfUrl,pdfDescription,pdfTitle;
                Log.e("responsePDF", response );
                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray jr = jo.getJSONArray("result");


                    for (int i = 0; i <jr.length() ; i++)
                    {
                        JSONObject jn = jr.getJSONObject(i);

                        dataList.add(new modelClassOfPdf(jn.getString("title") ,jn.getString("url"),jn.getString("subject")));
                    }
                    adapterForPdf adapter = new adapterForPdf(getContext(),R.layout.layoutforpdf,dataList);
                    listView.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    Log.e("volleyError::", e.getMessage() );
                }


                Log.e("response", response );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

}
