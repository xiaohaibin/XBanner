package com.stx.xhb.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.demo.entity.TuchongEntity;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.utils.L;

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
        mBanner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this) / 2));
        ListView listView = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pages));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, GuideActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, CustomViewsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ClipChildrenModeActivity.class));
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
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(MainActivity.this, "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean) model).getEntry();
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                Glide.with(MainActivity.this).load(url).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //加载网络图片资源
        String url = "https://api.tuchong.com/2/wall-paper/app";
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
                        TuchongEntity advertiseEntity = new Gson().fromJson(response, TuchongEntity.class);
                        List<TuchongEntity.FeedListBean> others = advertiseEntity.getFeedList();
                        List<TuchongEntity.FeedListBean> data=new ArrayList<>();
                        List<String> tips=new ArrayList<>();
                        for (int i = 0; i < others.size(); i++) {
                            TuchongEntity.FeedListBean feedListBean = others.get(i);
                            if ("post".equals(feedListBean.getType())){
                                data.add(feedListBean);
                                tips.add(feedListBean.getEntry().getTitle());
                            }
                        }
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner.setAutoPlayAble(data.size() > 1);
                        mBanner.setData(data, tips);
                    }
                });
    }

}
