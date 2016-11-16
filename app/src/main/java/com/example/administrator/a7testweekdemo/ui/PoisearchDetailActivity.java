package com.example.administrator.a7testweekdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.a7testweekdemo.R;

public class PoisearchDetailActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisearch_detail);
        initView();

        Intent intent=getIntent();
        String detailUrl = intent.getStringExtra("detailUrl");

        //设置，初始化操作
        //设置webView在本应用中加载数据，不进行跳转
        webView.setWebViewClient(new WebViewClient());
        //设置webView实现js动画效果
        webView.setWebChromeClient(new WebChromeClient());
        //允许js交互
        webView.getSettings().setJavaScriptEnabled(true);
        //允许缓存数据
        webView.getSettings().setAppCacheEnabled(true);
        //开启DomStorageAPI功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启DatabaseAPI功能
        webView.getSettings().setDatabaseEnabled(true);

        webView.loadUrl(detailUrl);
    }

    private void initView() {
        webView = ((WebView) findViewById(R.id.activity_poisearch_detail_webViewId));
    }
}
