package com.example.administrator.a7testweekdemo.fragment;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.adapter.TouTiaoAdapter;
import com.example.administrator.a7testweekdemo.adapter.TouTiaoHeadViewAdapter;
import com.example.administrator.a7testweekdemo.bean.TouTiaoEntrity;
import com.example.administrator.a7testweekdemo.bean.TouTiaoHeadEntrity;
import com.example.administrator.a7testweekdemo.database.TeaDatabaseHistoryHelper;
import com.example.administrator.a7testweekdemo.ui.WebViewActivity;
import com.example.administrator.a7testweekdemo.uri.AppInterface;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
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
public class TouTiaoFragment extends Fragment implements ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private PullToRefreshListView prtListView;
    private TouTiaoAdapter adapter;
    private int page=1;
    private View headView;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private List<View> viewList;
    private View view;
    private BitmapUtils bitmapUtils;
    private TeaDatabaseHistoryHelper helper;
    private SQLiteDatabase db;

    private int currentPosition=Integer.MAX_VALUE/2;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(this.hasMessages(1)){
                        //移出多个Message，保证只有一个
                        this.removeMessages(1);
                    }
                    currentPosition++;
                    viewPager.setCurrentItem(currentPosition);
                    this.sendEmptyMessageDelayed(1,3000);
                    break;
                case 2:
                    if(this.hasMessages(1)){
                        //移出了Message，自动的切换就会停止
                        this.removeMessages(1);
                    }
                    break;
                case 3:
                    //手滑动的时候，页码变，需要对页码重新赋值
                    currentPosition = msg.arg1;
                    this.sendEmptyMessageDelayed(1,3000);
                    break;
            }
        }
    };

    public TouTiaoFragment() {
    }

    private LoadingLayoutProxy loadingLayoutProxy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tou_tiao, container, false);
        bitmapUtils=new BitmapUtils(getContext());
        prtListView = ((PullToRefreshListView) view.findViewById(R.id.fragment_tou_tiao_listViewId));

        loadingLayoutProxy= (LoadingLayoutProxy) prtListView.getLoadingLayoutProxy();
        loadingLayoutProxy.setRefreshingLabel("拼命加载中");
        loadingLayoutProxy.setReleaseLabel("下拉刷新");
        loadingLayoutProxy.setPullLabel("释放更新");
        loadingLayoutProxy.setLastUpdatedLabel("上次更新时间：");
        loadingLayoutProxy.setTextTypeface(Typeface.MONOSPACE);

        //设置监听
        prtListView.setMode(PullToRefreshBase.Mode.BOTH);
        prtListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                String time = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_ABBREV_TIME |DateUtils.FORMAT_SHOW_YEAR| DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE);

                loadingLayoutProxy.setLastUpdatedLabel("上次更新时间："+time);
                //清除源数据
                adapter.clear();
                page=1;
                //加载新数据
                loadData(page);
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page+=1;
                loadData(page);
            }
        });

        ListView listView = prtListView.getRefreshableView();
        //添加头视图
        headView = inflater.inflate(R.layout.toutiao_headview,listView,false);
        //实例化头视图
        initHeadView();
        listView.addHeaderView(headView);

        //实例化适配器
        adapter = new TouTiaoAdapter(getContext());
        //设置适配器
        prtListView.setAdapter(adapter);

        prtListView.setOnItemClickListener(this);

        prtListView.setOnItemLongClickListener(this);

        //加载数据
        loadData(page);

        helper=new TeaDatabaseHistoryHelper(getContext());

        return view;
    }
    //初始化头布局
    private void initHeadView() {
        linearLayout = (LinearLayout) headView.findViewById(R.id.toutiao_headview_linearLayoutId);
        viewPager = (ViewPager) headView.findViewById(R.id.toutiao_headview_viewPagerId);
        viewList=new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            view = getActivity().getLayoutInflater().inflate(R.layout.item_head,viewPager,false);
            viewList.add(view);
        }
        //实例化适配器
        TouTiaoHeadViewAdapter headAdapter = new TouTiaoHeadViewAdapter(viewList);
        //设置适配器
        viewPager.setAdapter(headAdapter);

        viewPager.setCurrentItem(Integer.MAX_VALUE/2);

        handler.sendEmptyMessageDelayed(1,3000);

        viewPager.addOnPageChangeListener(this);

        //加载图片
        OkHttpUtils.get().url(AppInterface.HEADERIMAGE_URL).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
            }
            @Override
            public void onResponse(String response, int id) {
                TouTiaoHeadEntrity touTiaoHeadEntrity = new Gson().fromJson(response, TouTiaoHeadEntrity.class);
                List<TouTiaoHeadEntrity.DataBean> data= touTiaoHeadEntrity.getData();
                for (int i = 0; i < data.size(); i++) {
                    String image = data.get(i).getImage();
                    String title = data.get(i).getTitle();
                    ImageView imageView = (ImageView) viewList.get(i).findViewById(R.id.item_head_imageViewId);
                    TextView textView = (TextView) viewList.get(i).findViewById(R.id.item_head_titleId);
                    textView.setText(title);
                    bitmapUtils.display(imageView,image);
                }
            }
        });
        showAtPosition(Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%viewList.size());
    }
    private void loadData(int page) {
        String url=String.format(AppInterface.HEADLINE_URL,page);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                TouTiaoEntrity touTiaoEntrity = new Gson().fromJson(response, TouTiaoEntrity.class);
                List<TouTiaoEntrity.DataBean> data = touTiaoEntrity.getData();
                prtListView.onRefreshComplete();
                adapter.addAll(data);
            }
        });
    }

    //滑动中
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    //选中状态
    @Override
    public void onPageSelected(int position) {
//        handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
        handler.sendMessage(Message.obtain(handler,3,position,0));
        showAtPosition(position);
    }

    //滑动状态发生改变
    private void showAtPosition(int position) {
        for (int i=0;i<linearLayout.getChildCount();i++){
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (position%linearLayout.getChildCount()==i){
                imageView.setImageResource(R.drawable.dot);
            }else {
                imageView.setImageResource(R.drawable.dot_1);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state==ViewPager.SCROLL_STATE_DRAGGING){
            handler.sendEmptyMessage(2);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String id = adapter.getItem(i-2).getId();
        final String source = adapter.getItem(i-2).getSource();
        final String create_time = adapter.getItem(i-2).getCreate_time();
        final String title = adapter.getItem(i-2).getTitle();
        final String description = adapter.getItem(i-2).getDescription();
        final String nickname = adapter.getItem(i-2).getNickname();
        final String wap_thumb = adapter.getItem(i-2).getWap_thumb();
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
        showDialog(i-2,view);
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
                for (int i = 0+2; i < prtListView.getChildCount(); i++) {
                    if (prtListView.getChildAt(i).getTop()>= view.getTop()) {
                        prtListView.getChildAt(i).startAnimation(upAnimation);
                    }
                }
                adapter.remove(position);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
