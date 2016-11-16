package com.example.administrator.a7testweekdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.adapter.MyViewPagerAdapter;
import com.example.administrator.a7testweekdemo.fragment.BaiKeFragment;
import com.example.administrator.a7testweekdemo.fragment.BaseOtherFragment;
import com.example.administrator.a7testweekdemo.fragment.TouTiaoFragment;
import com.softpo.viewpagertransformer.ScaleInOutTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ImageView moreData;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private MyViewPagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        int ceil = (int) Math.ceil(2.25);
//        Log.d("1608", "onCreate: "+ceil);

        initView();
        fragments = new ArrayList<>();
        fragments.add(new TouTiaoFragment());
        fragments.add(new BaiKeFragment());
        for (int i = 0; i < 3; i++) {
            fragments.add(BaseOtherFragment.newInstance(52 + i));
        }
        //实例化适配器
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        //设置适配器
        viewPager.setAdapter(adapter);

        /**
         * new AccordionTransformer():切换时有拉伸的动画效果
         * new BackgroundToForegroundTransformer():切换时有放大的动画效果
         * new CubeInTransformer():从里到外的切换效果
         * new CubeOutTransformer()：从外到里的切换效果
         * new DepthPageTransformer():切换时一个在上一个在下，在下的从透明逐渐到不透明显示的动画效果
         * new FilmPagerTransformer()：一种推挤感觉的动画效果
         * new FlipHorizontalTransformer()；以y轴为中心轴，水瓶方向进行翻转的动画效果
         * new FlipVerticalTransformer():以x轴为中心轴，垂直方向进行翻转的动画效果
         * new ForegroundToBackgroundTransformer():切换时，上一页会呈梯形逐渐消失的动画效果
         * new RotateDownTransformer():切换时会以下面的中心点进行扇形切换的动画效果
         * new RotateUpTransformer()：切换时会以上面的中心点进行扇形切换的动画效果
         * new ScaleInOutTransformer():切换时从小逐渐放大的动画效果
         * new StackTransformer()：切换时有覆盖的动画效果
         * new TabletTransformer()：从里向外的切换效果
         * new ZoomInTransformer():切换时下一页逐渐放大上一页逐渐缩小的动画效果（从里向外）
         * new ZoomOutSlideTransformer()：切换时，上一页从透明逐渐到不透明展示，下一页从透明到不透明消失
         * new ZoomOutTranformer()：切换时下一页逐渐缩小上一页逐渐放大的动画效果（从外向里）
         */
        viewPager.setPageTransformer(true,new ScaleInOutTransformer());

        tabLayout.setupWithViewPager(viewPager);

    }
    private void initView() {
        drawerLayout = ((DrawerLayout) findViewById(R.id.activity_main_drawerLayoutId));
        tabLayout = ((TabLayout) findViewById(R.id.activity_main_tabLayoutId));
        moreData = ((ImageView) findViewById(R.id.activity_main_moreId));
        moreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        viewPager = ((ViewPager) findViewById(R.id.activity_main_viewPagerId));

        searchText = ((EditText) findViewById(R.id.activity_main_drawer_SearchEdit));
    }

    //给各个控件设置监听
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.activity_main_drawer_imageViewBackId://返回
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            case R.id.activity_main_drawer_imageViewBtn://搜索
                String trim = searchText.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    Toast.makeText(MainActivity.this, "搜索的关键字不可以为空", Toast.LENGTH_SHORT).show();
                }else{
                    intent.setClass(this,SearchActivity.class);
                    intent.putExtra("trim",trim);
                    startActivity(intent);
                }
                break;
            case R.id.activity_main_drawer_myCollect://收藏
                intent.setClass(this,MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_history://历史记录
                intent.setClass(this,HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_copyrightMessage://版本信息
                intent.setClass(this,InformationActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_suggest://意见反馈
                intent.setClass(this,SuggestActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_map:
                intent.setClass(this,MapActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_login:
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_exit:
                Toast.makeText(MainActivity.this, "成功退出登录！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
