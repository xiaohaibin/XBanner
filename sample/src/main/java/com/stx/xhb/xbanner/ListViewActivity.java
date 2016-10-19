package com.stx.xhb.xbanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.mylibrary.XBanner;
import com.stx.xhb.mylibrary.transformers.Transformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * listview 添加headview使用 RecycleView上也是同样的
 */
public class ListViewActivity extends AppCompatActivity implements XBanner.XBannerAdapter, XBanner.OnItemClickListener {

    private XBanner mBannerNet;
    private ListView mLv;
    private List<String> mDataList;
    private ArrayAdapter<String> mAdapter;
    private List<AdvertiseEntity.OthersBean> mOthersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        initData();
        initView();
        setAdapter();
        setListener();
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDataList);
        mLv.setAdapter(mAdapter);
        mBannerNet.setmAdapter(this);
    }

    /**
     * 初始化listview数据
     */
    private void initData() {
        //模拟网络列表数据
        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataList.add("小明" + i);
        }
        //加载网络图片资源
        String url = "http://news-at.zhihu.com/api/4/themes";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ListViewActivity.this, "加载广告数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AdvertiseEntity advertiseEntity = new Gson().fromJson(response, AdvertiseEntity.class);
                        mOthersList = advertiseEntity.getOthers();
                        List<String> tips = new ArrayList<String>();
                        for (int i = 0; i < mOthersList.size(); i++) {
                            tips.add(mOthersList.get(i).getDescription());
                        }
                        mBannerNet.setData(mOthersList, tips);
                    }
                });
    }

    /**
     * 初始化View
     */
    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.ad_head, null);
        mBannerNet = (XBanner) headerView.findViewById(R.id.banner_1);
        mBannerNet.setPageChangeDuration(1000);
        mBannerNet.setPageTransformer(Transformer.Flip);
        mLv.addHeaderView(headerView);
    }

    /**
     * 初始化XBanner
     */
    private void setListener() {
        mBannerNet.setOnItemClickListener(this);
    }

    @Override
    public void loadBanner(XBanner banner, View view, int position) {
        Glide.with(this).load(mOthersList.get(position).getThumbnail()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
    }

    @Override
    public void onItemClick(XBanner banner, int position) {
        Toast.makeText(ListViewActivity.this, "点击了第" + (position) + "图片", Toast.LENGTH_SHORT).show();
    }


    /** 为了更好的体验效果建议在下面两个生命周期中调用下面的方法 **/
    @Override
    protected void onResume() {
        super.onResume();
        mBannerNet.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerNet.stopAutoPlay();
    }
}
