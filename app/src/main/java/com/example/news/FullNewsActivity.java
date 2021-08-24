package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FullNewsActivity extends AppCompatActivity {
    WebView webFullNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        webFullNews = findViewById(R.id.webFullNews);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");

        webFullNews.loadUrl(link);
        webFullNews.setWebViewClient(new WebViewClient());

    }
}