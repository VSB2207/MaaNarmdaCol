package com.example.college;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapterOfVideo extends RecyclerView.Adapter<adapterOfVideo.VideoViewHolder>
{
    List<modelClassForVideo> videoList;

    public adapterOfVideo() {

    }

    public adapterOfVideo(List<modelClassForVideo> videoList)
    {
        this.videoList = videoList;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutforvideo,parent,false);
        return new VideoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position)
    {
        holder.videoWeb.loadData(videoList.get(position).getUrl(),"text/html","utf-8");
        holder.description.setText(videoList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder{
        WebView videoWeb;
        TextView description;
        public VideoViewHolder(View itemView)
        {
            super(itemView);
            videoWeb=(WebView)itemView.findViewById(R.id.webView_youtube);
            description=(TextView)itemView.findViewById(R.id.TV_youtube);
            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient());

        }
    }
}
