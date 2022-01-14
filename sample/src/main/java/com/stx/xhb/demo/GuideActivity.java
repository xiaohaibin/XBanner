package com.stx.xhb.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.LocalImageInfo;

/**
 * XBanner 使用在引导页控件
 */
public class GuideActivity extends AppCompatActivity {

    private XBanner mXBanner;
    private Button mBtnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }


    private void initView() {
        mXBanner = findViewById(R.id.xbanner);
        mBtnEnter = findViewById(R.id.btn);
        List<LocalImageInfo> localImageInfoList=new ArrayList<>();
        localImageInfoList.add(new LocalImageInfo(R.mipmap.we1));
        localImageInfoList.add(new LocalImageInfo(R.mipmap.we2));
        localImageInfoList.add(new LocalImageInfo(R.mipmap.we3));
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ((ImageView) view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        }).setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mXBanner.getRealCount() - 1) {
                    mBtnEnter.setVisibility(View.VISIBLE);
                } else {
                    mBtnEnter.setVisibility(View.GONE);
                }
            }
        }).setBannerData(localImageInfoList);
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, ListViewActivity.class));
                finish();
            }
        });
    }
}
