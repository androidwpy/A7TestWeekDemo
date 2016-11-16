package com.example.administrator.a7testweekdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;

public class SuggestActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private EditText titleEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        initView();
        back.setOnClickListener(this);
    }

    private void initView() {
        back = ((ImageView) findViewById(R.id.activity_suggest_imageViewBackId));
        titleEditText = ((EditText) findViewById(R.id.activity_suggest_titleId));
        contentEditText = ((EditText) findViewById(R.id.activity_suggest_contentId));
    }

    public void commitBtn(View view) {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        if (TextUtils.isEmpty(title)||TextUtils.isEmpty(content)){
            Toast.makeText(SuggestActivity.this, "标题和内容不可以为空", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SuggestActivity.this, "意见反馈提交失败，请重新尝试！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
