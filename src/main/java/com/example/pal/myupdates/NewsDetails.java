package com.example.pal.myupdates;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewsDetails extends AppCompatActivity {
    private WebView webView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        webView= (WebView) findViewById(R.id.wv_newsedetails);
        webView.setWebViewClient(new WebViewClient());
        webView.setNetworkAvailable(true);
        webView.getSettings().setJavaScriptEnabled(true);
        url=getIntent().getStringExtra("url");
        if (url!=null || !url.equals(null)){
        webView.loadUrl(url);

    }else {
            Toast.makeText(this, "Url Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}
