package com.example.administrator.a7testweekdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.a7testweekdemo.R;


public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private Animation animation;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences=getSharedPreferences("app.config", Context.MODE_PRIVATE);

        imageView = ((ImageView) findViewById(R.id.activity_splash_imageViewId));
        animation= AnimationUtils.loadAnimation(this,R.anim.anim_scale);
        imageView.setAnimation(animation);
        toWelcomeActivity();
    }
    //跳转到欢迎页面
    public void toWelcomeActivity(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean isFirstLogin=sharedPreferences.getBoolean("isFirstLogin",true);
                Intent intent=new Intent();
                if (isFirstLogin){
                    intent.setClass(SplashActivity.this,WelcomeActivity.class);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("isFirstLogin",false);
                    editor.commit();
                }else{
                    intent.setClass(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
