package com.example.administrator.a7testweekdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText userNameEditText;

    private SharedPreferences  sharedPreferences;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        sharedPreferences=getSharedPreferences("register", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();

    }

    private void initView() {
        passwordEditText = ((EditText)findViewById(R.id.activity_register_passwordEditText));
        userNameEditText = ((EditText) findViewById(R.id.activity_register_userNameEditText));
    }
    public void registerBtn(View view) {
        String password = passwordEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();

        edit.putString("password",password);
        edit.putString("userName",userName);
        edit.commit();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
    }
}
