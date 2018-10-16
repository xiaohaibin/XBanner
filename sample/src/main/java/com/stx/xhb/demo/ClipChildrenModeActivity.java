package com.stx.xhb.demo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.utils.L;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 一屏显示多个，类似魅族Banner模式
 */
public class ClipChildrenModeActivity extends AppCompatActivity {
    private XBanner mBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_children_mode);
        mBanner = (XBanner) findViewById(R.id.banner);
        mBanner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this) / 2));
        initBanner();
        initData();
//        initLocalImage();
    }


    /**
     * 初始化XBanner
     */
    private void initBanner() {
        //设置广告图片点击事件
        mBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(ClipChildrenModeActivity.this, "点击了第" + (position+1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //此处适用Fresco加载图片，可自行替换自己的图片加载框架
                SimpleDraweeView draweeView = (SimpleDraweeView)view;
                draweeView.setImageURI(Uri.parse(((AdvertiseEntity.OthersBean) model).getThumbnail()));

//                加载本地图片展示
//                ((ImageView)view).setImageResource((Integer) model);
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
                        Toast.makeText(ClipChildrenModeActivity.this, "加载广告数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AdvertiseEntity advertiseEntity = new Gson().fromJson(response, AdvertiseEntity.class);
//                        List<AdvertiseEntity.OthersBean> others = advertiseEntity.getOthers();
                        List<AdvertiseEntity.OthersBean> others = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            others.add(advertiseEntity.getOthers().get(i));
                        }
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner.setAutoPlayAble(others.size() > 1);
                        mBanner.setData(R.layout.layout_fresco_imageview,others, null);
                    }
                });
    }

    /**
     * 加载本地图片
     */
    private void initLocalImage(){
        List<Integer> data=new ArrayList<>();
        data.add(R.drawable.banner_placeholder);
        data.add(R.drawable.banner_placeholder);
        data.add(R.drawable.banner_placeholder);
        data.add(R.drawable.banner_placeholder);
        mBanner.setData(data, null);
    }
}
