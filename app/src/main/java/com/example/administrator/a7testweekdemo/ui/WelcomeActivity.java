package com.example.administrator.a7testweekdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.fragment.Welcome1Fragment;
import com.example.administrator.a7testweekdemo.fragment.Welcome2Fragment;
import com.example.administrator.a7testweekdemo.fragment.Welcome3Fragment;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager = ((ViewPager) findViewById(R.id.activity_welcome_viewPagerId));
        linearLayout = ((LinearLayout) findViewById(R.id.activity_welcome_linearLayoutId));

        fragmentList=new ArrayList<>();
        fragmentList.add(new Welcome1Fragment());
        fragmentList.add(new Welcome2Fragment());
        fragmentList.add(new Welcome3Fragment());
        //实例化适配器
        FragmentPagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        //设置适配器
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        showAtPosition(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        showAtPosition(position);
    }

    private void showAtPosition(int position) {
        for (int i=0;i<linearLayout.getChildCount();i++){
            if (position==i){
                ((ImageView) linearLayout.getChildAt(i)).setImageResource(R.drawable.page_now);
            }else{
                ((ImageView) linearLayout.getChildAt(i)).setImageResource(R.drawable.page);
            }
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
