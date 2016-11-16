package com.example.administrator.a7testweekdemo.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.database.TeaDatabaseCollectHelper;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView titelTextView;
    private TextView createTimeTextView;
    private TextView sourceTextView;

    private TeaDatabaseCollectHelper helper;
    private SQLiteDatabase db;

    private String source;
    private String create_time;
    private String title;
    private String description;
    private String nickname;
    private String wap_thumb;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();

        Intent intent=getIntent();
        String wap_content = intent.getStringExtra("wap_content");
        id=intent.getStringExtra("id");
        source = intent.getStringExtra("source");
        create_time = intent.getStringExtra("create_time");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        nickname = intent.getStringExtra("nickname");
        wap_thumb = intent.getStringExtra("wap_thumb");

        webView.loadDataWithBaseURL(null,wap_content,"text/html","utf-8",null);

        titelTextView.setText(title);
        createTimeTextView.setText("时间:"+create_time);
        sourceTextView.setText("来源"+source);

        helper=new TeaDatabaseCollectHelper(this);
        db=helper.getReadableDatabase();

    }

    private void initView() {
        titelTextView = ((TextView) findViewById(R.id.activity_web_view_titleId));
        createTimeTextView = ((TextView) findViewById(R.id.activity_web_view_createTimeId));
        sourceTextView = ((TextView) findViewById(R.id.activity_web_view_sourceId));
        webView = ((WebView) findViewById(R.id.activity_web_view_webViewId));
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_web_view_backId://返回
                finish();
                break;
            case R.id.activity_web_view_shareId://分享
                showShare();
                break;
            case R.id.activity_web_view_collectId://收藏

                Cursor cursor = db.query(TeaDatabaseCollectHelper.TABLE_NAME, new String[]{"_id","id"}, null, null, null, null, null);
                int idIndex = cursor.getColumnIndex("id");
                List<String> list=new ArrayList<>();
                while (cursor.moveToNext()){
                    String string = cursor.getString(idIndex);
                    list.add(string);
                }
                if (list.contains(id)){
                    Toast.makeText(WebViewActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                }else{
                    //将数据存到数据库中
                    ContentValues values=new ContentValues();
                    values.put("id",id);
                    values.put("title",title);
                    values.put("description",description);
                    values.put("source",source);
                    values.put("nickname",nickname);
                    values.put("create_time",create_time);
                    values.put("wap_thumb",wap_thumb);
                    db.insert(TeaDatabaseCollectHelper.TABLE_NAME,null,values);
                    Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    db.close();
                }
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

}
