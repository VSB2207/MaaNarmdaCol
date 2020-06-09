package com.example.college;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class lectures_frag extends Fragment {

    RecyclerView recyclerView;
    Vector<modelClassForVideo> dataList = new Vector<modelClassForVideo>();
    String firstHalf="<iframe width=\"350\" height=\"250\"  src=\"https://www.youtube.com/embed/", id="",endUrl="frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
    public lectures_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lectures_frag, container, false);

        recyclerView=view.findViewById(R.id.recycler_view_lectures);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchVideo();


        //        dataList.add(new modelClassForVideo("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/W4hTJybfU7s\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>","demo4"));
//        dataList.add(new modelClassForVideo("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/W4hTJybfU7s\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>","demo5"));
//        dataList.add(new modelClassForVideo("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/W4hTJybfU7s\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>","demo6"));

        //  listView.setAdapter(adapter);
        return view;
    }

    private void fetchVideo()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String url = preferences.getString("URL", null);
        String courseVal=preferences.getString("class",null);
        url = url + "videoCourse.php?" + "class=" + courseVal;
        RequestQueue rq = Volley.newRequestQueue(getContext());
        Log.e("finalURLLogin--->", url);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray jr = jo.getJSONArray("result");


                    for (int i = 0; i <jr.length() ; i++)
                    {
                        JSONObject jn = jr.getJSONObject(i);
                        id=jn.getString("id");
                        dataList.add(new modelClassForVideo(firstHalf+id+"\""+endUrl,jn.getString("description")));

                    }
                    adapterOfVideo adapter = new adapterOfVideo(dataList);
                    recyclerView.setAdapter(adapter);

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
