package com.example.college;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class
   readPdf extends AppCompatActivity
{
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    WebView myWebView;
    ProgressDialog progressDialog;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);
        progressDialog = new ProgressDialog(this);
        myWebView=(WebView) findViewById(R.id.webView_pdf);
        progressDialog.setTitle("Loading..");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        Intent intent =getIntent();
        url=intent.getStringExtra("url");

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setVerticalScrollBarEnabled(true);
            myWebView.setHorizontalScrollBarEnabled(true);
            myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
            //myWebView.setScrollbarFadingEnabled(true);

            WebSettings webSettings=myWebView.getSettings();



            myWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100) {
                        progressDialog.show();
                    }
                    if (progress == 100) {
                        progressDialog.dismiss();
                    }
                }
            });


            if (Build.VERSION.SDK_INT < 18) {
                //speed webview
                myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            }



            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    view.loadUrl(url);
                    view.isVerticalScrollBarEnabled();
                    return true;
                }
            });
            myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
        }
        else
        {
            Toast.makeText(this, "Please Check your network status .", Toast.LENGTH_SHORT).show();
            finish();

        }

    }


    public void onBackPressed() {
        if (myWebView.isFocused() && myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

//    WebView webView;
//    ProgressBar progressBar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_read_pdf);
//        progressBar=(ProgressBar)findViewById(R.id.pdf_pd);
//        webView=(WebView)findViewById(R.id.webView_pdf);
//        progressBar.setVisibility(View.VISIBLE);
//
//
//        Intent intent =getIntent();
//        String url=intent.getStringExtra("url");
//        Toast.makeText(this, url, Toast.LENGTH_LONG).show();
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(false);
//        webView.setWebChromeClient(new WebChromeClient(){
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//
//                getSupportActionBar().setTitle("Loading..");
//                if (newProgress==100)
//                {
//                    progressBar.setVisibility(View.GONE);
//                    getSupportActionBar().setTitle(R.string.app_name);
//                }
//            }
//        });
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
//
////        webView.setWebViewClient(new WebViewClient(){
////
////            @Override
////            public void onPageFinished(WebView view, String url) {
////
////                webView.loadUrl("javascript:(function() { " + "document.querySelector('[role=\"toolbar\"]').remove();})()");
////                progressBar.setVisibility(View.GONE);
////
////
////            }
////        });
//
//
//
//    }
}
