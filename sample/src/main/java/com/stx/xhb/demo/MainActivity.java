package com.stx.xhb.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private XBanner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBanner();
        initData();
    }

    private void initView() {
        mBanner = (XBanner) findViewById(R.id.banner);
        ListView listView = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pages));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this,ListViewActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,GuideActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,CustomViewsActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 初始化XBanner
     */
    private void initBanner() {
        //设置广告图片点击事件
        mBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model,View view, int position) {
                Toast.makeText(MainActivity.this, "点击了第" + (position+1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                Glide.with(MainActivity.this).load(((AdvertiseEntity.OthersBean) model).getThumbnail()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //加载网络图片资源
        String url = "http://news-at.zhihu.com/api/4/themes";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "加载广告数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AdvertiseEntity advertiseEntity = new Gson().fromJson(response, AdvertiseEntity.class);
                        List<AdvertiseEntity.OthersBean> others = advertiseEntity.getOthers();
                        List<String> tips = new ArrayList<>();
                        for (int i = 0; i < others.size(); i++) {
                            tips.add(others.get(i).getDescription() + "哈哈哈哈或或或或或或或或或或或或");
                        }
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner.setAutoPlayAble(others.size() > 1);
                        mBanner.setData(others, tips);
                    }
                });
    }

}
