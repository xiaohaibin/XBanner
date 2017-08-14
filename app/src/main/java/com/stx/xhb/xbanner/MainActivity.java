package com.stx.xhb.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.demo.XBanner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XBanner.XBannerAdapter{
    private XBanner mBannerNet;
    private List<String> imgesUrl;
    private ListView lv;
    private List<String> data;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initNetBanner();
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);
    }

    /**
     * 初始化listview数据
     */
    private void initData() {
        data=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("小明"+i);
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.ad_head, null);
        mBannerNet = (XBanner) headerView.findViewById(R.id.banner_1);
        lv.addHeaderView(headerView);
    }

    /**
     * 初始化XBanner
     */
    private void initNetBanner() {
        mBannerNet = (XBanner) findViewById(R.id.banner_1);

        imgesUrl = new ArrayList<>();
        imgesUrl.add("http://img3.fengniao.com/forum/attachpics/913/114/36502745.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");
        mBannerNet.setData(imgesUrl);

        mBannerNet.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void BannerItemClick(XBanner banner, int position) {
                Toast.makeText(MainActivity.this, "点击了第"+(position+1)+"图片", Toast.LENGTH_SHORT).show();
            }
        });
        mBannerNet.setmAdapter(this);
    }

    @Override
    public void loadBanner(XBanner banner, View view, int position) {
        Glide.with(this).load(imgesUrl.get(position)).into((ImageView) view);
    }
}
