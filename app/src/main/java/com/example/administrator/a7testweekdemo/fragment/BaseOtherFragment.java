package com.example.administrator.a7testweekdemo.fragment;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.adapter.BaseOtherAdapter;
import com.example.administrator.a7testweekdemo.bean.BaseOtherEntrity;
import com.example.administrator.a7testweekdemo.database.TeaDatabaseHistoryHelper;
import com.example.administrator.a7testweekdemo.ui.WebViewActivity;
import com.example.administrator.a7testweekdemo.uri.AppInterface;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseOtherFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private PullToRefreshListView ptrListView;
    private BaseOtherAdapter adapter;
    private int page=1;
    private int type;
    private TeaDatabaseHistoryHelper helper;
    private SQLiteDatabase db;
    private LoadingLayoutProxy loadingLayoutProxy;

    public BaseOtherFragment() {
    }

    public static BaseOtherFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        BaseOtherFragment fragment = new BaseOtherFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        type = bundle.getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_other, container, false);
        ptrListView = ((PullToRefreshListView) view.findViewById(R.id.fragment_base_other_listViewId));

        loadingLayoutProxy= (LoadingLayoutProxy) ptrListView.getLoadingLayoutProxy();
        loadingLayoutProxy.setRefreshingLabel("拼命加载中");
        loadingLayoutProxy.setReleaseLabel("下拉刷新");
        loadingLayoutProxy.setPullLabel("释放更新");
        loadingLayoutProxy.setLastUpdatedLabel("上次更新时间：");

        //设置监听
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String time = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_ABBREV_TIME |DateUtils.FORMAT_SHOW_YEAR| DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE);

                loadingLayoutProxy.setLastUpdatedLabel("上次更新时间："+time);

                //清除源数据
                adapter.clear();
                page=1;
                loadData(page);
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page+=1;
                loadData(page);
            }
        });
        adapter = new BaseOtherAdapter(getContext());
        ptrListView.setAdapter(adapter);

        ptrListView.setOnItemClickListener(this);

        ptrListView.setOnItemLongClickListener(this);
        loadData(page);

        helper=new TeaDatabaseHistoryHelper(getContext());

        return view;
    }

    private void loadData(int page) {
        String url=String.format(AppInterface.BASE_URL,type,page);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                BaseOtherEntrity baseOtherEntrity = new Gson().fromJson(response, BaseOtherEntrity.class);
                List<BaseOtherEntrity.DataBean> data = baseOtherEntrity.getData();
                ptrListView.onRefreshComplete();
                adapter.addAll(data);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String id = adapter.getItem(i-1).getId();

        final String source = adapter.getItem(i-1).getSource();
        final String create_time = adapter.getItem(i-1).getCreate_time();
        final String title = adapter.getItem(i-1).getTitle();
        final String description = adapter.getItem(i-1).getDescription();
        final String nickname = adapter.getItem(i-1).getNickname();
        final String wap_thumb = adapter.getItem(i-1).getWap_thumb();
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
                    Intent intent=new Intent(getActivity(), WebViewActivity.class);
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

        Cursor cursor = db.query(TeaDatabaseHistoryHelper.TABLE_NAME, new String[]{"_id", "id"}, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex("id");
        List<String> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String string = cursor.getString(idIndex);
            list.add(string);
        }
        if (!list.contains(id)){
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
        }
        db.close();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        showDialog(i-1,view);
        return true;
    }

    private void showDialog(final int position, final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("亲，确定要删除吗？");
        builder.setIcon(R.drawable.icon_dialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem(position,view);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void deleteItem(final int position, final View view) {

        TranslateAnimation translateAnimation = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        translateAnimation.setDuration(800);
        translateAnimation.setFillAfter(false);
        view.startAnimation(translateAnimation);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 注意这个开始坐标要写成View此时此刻的坐标，然后它再挪到我们ListView排列后的位置上
                TranslateAnimation upAnimation = new TranslateAnimation(0, 0, view.getHeight(), 0);
                upAnimation.setDuration(500);
                upAnimation.setInterpolator(getContext(), android.R.anim.linear_interpolator);
//							upAnimation.setFillAfter(true);
                for (int i = 0+2; i < ptrListView.getChildCount(); i++) {
                    if (ptrListView.getChildAt(i).getTop()>= view.getTop()) {
                        ptrListView.getChildAt(i).startAnimation(upAnimation);
                    }
                }
                adapter.remove(position);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
