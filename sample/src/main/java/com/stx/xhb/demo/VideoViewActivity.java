package com.stx.xhb.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.demo.entity.CustomViewsInfo;
import com.stx.xhb.demo.holder.BannerHolderCreator;
import java.util.ArrayList;
import java.util.List;


/**
 * 视频图片混合轮播
 * Demo只是给个例子参考，更多自定义功能需要根据自身需求去实现
 * 框架本身只是个轮播框架，支持各种自定义布局
 */
public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_views);
        initView();
    }

    private void initView() {
        XBanner banner = (XBanner) findViewById(R.id.banner);
        List<CustomViewsInfo> data = new ArrayList<>();
        data.add(new CustomViewsInfo("https://photo.tuchong.com/250829/f/31548923.jpg"));
        data.add(new CustomViewsInfo("https://photo.tuchong.com/46728/f/20138526.jpg"));
        data.add(new CustomViewsInfo("https://photo.tuchong.com/392724/f/16858773.jpg"));
        data.add(new CustomViewsInfo("https://photo.tuchong.com/408963/f/18401047.jpg"));
        final BannerHolderCreator holderCreator = new BannerHolderCreator();
        banner.setBannerData(data, holderCreator);
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(VideoViewActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.i("onPageScrolled=", i + "");
            }

            @Override
            public void onPageSelected(int i) {
                Log.i("onPageSelected=", i + "");
                if (i == 0) {
                    holderCreator.videoViewHolder.videoView.start();
                } else {
                    holderCreator.videoViewHolder.videoView.pause();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.i("ScrollStateChanged=", i + "");
            }
        });
    }

}
