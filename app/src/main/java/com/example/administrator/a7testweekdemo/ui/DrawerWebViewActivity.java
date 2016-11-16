package com.example.administrator.a7testweekdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.a7testweekdemo.R;

public class DrawerWebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView titleTextView;
    private TextView sourceTextView;
    private TextView createTimeTextView;
    private String title;
    private String source;
    private String create_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_web_view);
        initView();

        Intent intent=getIntent();
        String wap_content = intent.getStringExtra("wap_content");
        title = intent.getStringExtra("title");
        source = intent.getStringExtra("source");
        create_time = intent.getStringExtra("create_time");

        webView.loadDataWithBaseURL(null,wap_content,"text/html","utf-8",null);

        titleTextView.setText(title);
        sourceTextView.setText("时间"+source);
        createTimeTextView.setText("来源"+create_time);
    }

    private void initView() {
        webView = ((WebView) findViewById(R.id.activity_drawer_web_view_webViewId));
        titleTextView = ((TextView) findViewById(R.id.activity_drawer_web_view_titleId));
        sourceTextView = ((TextView) findViewById(R.id.activity_drawer_web_view_sourceId));
        createTimeTextView = ((TextView) findViewById(R.id.activity_drawer_web_view_createTimeId));
    }
}
