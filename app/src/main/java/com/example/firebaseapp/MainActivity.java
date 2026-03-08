package com.example.firebaseapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        setContentView(web);

        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());

        web.loadUrl("file:///android_asset/index.html");
    }
}
