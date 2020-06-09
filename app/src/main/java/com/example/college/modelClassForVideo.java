package com.example.college;

public class modelClassForVideo
{
    String url,description;


    public modelClassForVideo(String url, String description) {
        this.url = url;
        this.description = description;
    }



    public modelClassForVideo() {
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
