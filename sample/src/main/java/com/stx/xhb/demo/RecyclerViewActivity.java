package com.stx.xhb.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.demo.adapter.RvListAdapter;
import com.stx.xhb.demo.entity.TuchongEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;

/**
 * Use In RecyclerView
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private XBanner mXBanner;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initView();
        setListener();
        requsetData();
    }


    /**
     * 初始化View
     */
    private void initView() {
        recyclerView = findViewById(R.id.rv);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RvListAdapter rvListAdapter = new RvListAdapter(R.layout.list_item_text, Arrays.asList(getResources().getStringArray(R.array.transforms)));
        recyclerView.setAdapter(rvListAdapter);
        mRefreshLayout = findViewById(R.id.refresh_layout);

        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.ad_head, null);
        mXBanner = headerView.findViewById(R.id.banner);
        mXBanner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this) / 2));
        rvListAdapter.addHeaderView(headerView);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requsetData();
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 初始化XBanner
     */
    private void setListener() {
        //设置广告图片点击事件
        mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean.EntryBean) model);
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                Glide.with(RecyclerViewActivity.this).load(url).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });
    }

    /**
     * 加载网络数据
     */
    private void requsetData() {
        //加载网络图片资源
        String url = "https://api.tuchong.com/2/wall-paper/app";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(RecyclerViewActivity.this, "加载广告数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        TuchongEntity advertiseEntity = new Gson().fromJson(response, TuchongEntity.class);
                        List<TuchongEntity.FeedListBean> others = advertiseEntity.getFeedList();
                        List<TuchongEntity.FeedListBean.EntryBean> data = new ArrayList<>();
                        for (int i = 0; i < others.size(); i++) {
                            TuchongEntity.FeedListBean feedListBean = others.get(i);
                            if ("post".equals(feedListBean.getType())) {
                                data.add(feedListBean.getEntry());
                            }
                        }
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mXBanner.setAutoPlayAble(data.size() > 1);
                        mXBanner.setBannerData(data);
                    }
                });
    }

}
