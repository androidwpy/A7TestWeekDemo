package com.example.administrator.a7testweekdemo.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.adapter.MyCollectAdapter;
import com.example.administrator.a7testweekdemo.bean.MyCollectEntrity;
import com.example.administrator.a7testweekdemo.database.TeaDatabaseHistoryHelper;
import com.example.administrator.a7testweekdemo.uri.AppInterface;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String trim;
    private TextView title;
    private ImageView back;
    private ListView listView;
    private MyCollectAdapter adapter;
    private TeaDatabaseHistoryHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent=getIntent();
        trim = intent.getStringExtra("trim");

        initView();
        title.setText(trim);
        //实例化适配器
        adapter = new MyCollectAdapter(this);
        //设置适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        helper=new TeaDatabaseHistoryHelper(this);
        //加载数据
        loadData();
    }

    private void loadData() {
        String url=String.format(AppInterface.SEARCH_URL,trim);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                MyCollectEntrity myCollectEntrity = new Gson().fromJson(response, MyCollectEntrity.class);
                List<MyCollectEntrity.DataBean> data = myCollectEntrity.getData();
                adapter.addAll(data);
            }
        });
    }

    private void initView() {
        title = ((TextView) findViewById(R.id.activity_search_titleId));
        back = ((ImageView) findViewById(R.id.activity_search_imageViewBackId));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listView = ((ListView) findViewById(R.id.activity_search_listViewId));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String id = adapter.getItem(i).getId();

        final String source = adapter.getItem(i).getSource();
        final String create_time = adapter.getItem(i).getCreate_time();
        final String title = adapter.getItem(i).getTitle();
        final String description = adapter.getItem(i).getDescription();
        final String nickname = adapter.getItem(i).getNickname();
        final String wap_thumb = adapter.getItem(i).getWap_thumb();
        final String detailUrl=String.format(AppInterface.CONTENT_URL,id);
        OkHttpUtils.get().url(detailUrl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }
            @Override
            public void onResponse(String response, int i) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data=jsonObject.optJSONObject("data");
                    String wap_content=data.optString("wap_content");
                    Intent intent=new Intent(SearchActivity.this, WebViewActivity.class);

                    intent.putExtra("wap_content",wap_content);
                    intent.putExtra("id",id);
                    intent.putExtra("source",source);
                    intent.putExtra("create_time",create_time);
                    intent.putExtra("title",title);
                    intent.putExtra("description",description);
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("wap_thumb",wap_thumb);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        db=helper.getReadableDatabase();
        //将数据存到数据库中
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("title",title);
        values.put("description",description);
        values.put("source",source);
        values.put("nickname",nickname);
        values.put("create_time",create_time);
        values.put("wap_thumb",wap_thumb);
        db.insert(TeaDatabaseHistoryHelper.TABLE_NAME,null,values);
        db.close();
    }
}
