package com.example.administrator.a7testweekdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    private SharedPreferences sharedPreferences;
    private String password;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        sharedPreferences=getSharedPreferences("register", Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password","");
        userName = sharedPreferences.getString("userName","");

    }

    private void initView() {
        userNameEditText = ((EditText) findViewById(R.id.activity_login_userNameEditText));
        passwordEditText = ((EditText) findViewById(R.id.activity_login_passwordEditText));
    }

    public void btnClick(View view) {
        String name = userNameEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.activity_login_dengluBtn:
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(pass)){
                    if (password.equals(pass)&&userName.equals(name)){

                        intent.setClass(this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "登录成功，欢迎进入茶百科！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "用户名和密码不正确，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_login_zhuceBtn:
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
