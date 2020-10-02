package com.stx.xhb.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.stx.xhb.demo.entity.TuchongEntity;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 一屏显示多个，类似魅族Banner模式
 */
public class ClipChildrenModeActivity extends AppCompatActivity {

    private XBanner mBanner;
    private XBanner mBanner2;
    private XBanner mBanner3;
    private XBanner mBanner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_children_mode);
        mBanner = findViewById(R.id.banner);
        mBanner2 = findViewById(R.id.banner2);
        mBanner3 = findViewById(R.id.banner3);
        mBanner4 = findViewById(R.id.banner4);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this) / 2);

        mBanner.setLayoutParams(layoutParams);

        mBanner2.setLayoutParams(layoutParams);
        //修改切换动画
        mBanner2.setPageTransformer(Transformer.Default);

        mBanner3.setLayoutParams(layoutParams);

        mBanner4.setLayoutParams(layoutParams);

        initBanner(mBanner);
        initBanner(mBanner2);
        initBanner(mBanner3);
        initBanner(mBanner4);

        initData();
//        initLocalImage();
    }


    /**
     * 初始化XBanner
     */
    private void initBanner(XBanner banner) {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                LogUtils.i("click pos:" + position);
                ToastUtils.showShort("点击了第" + (position + 1) + "图片");
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //此处适用Fresco加载图片，可自行替换自己的图片加载框架
                SimpleDraweeView draweeView = (SimpleDraweeView) view;
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean.EntryBean) model);
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                draweeView.setImageURI(Uri.parse(url));
//                加载本地图片展示
//                ((ImageView)view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
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
                        Toast.makeText(ClipChildrenModeActivity.this, "加载广告数据失败", Toast.LENGTH_SHORT).show();
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
                        mBanner.setAutoPlayAble(data.size() > 1);
                        mBanner.setIsClipChildrenMode(true);
                        //老方法，不推荐使用
                        mBanner.setData(R.layout.layout_fresco_imageview, data, null);

                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner2.setAutoPlayAble(data.size() > 1);
                        mBanner2.setIsClipChildrenMode(true);
                        mBanner2.setBannerData(R.layout.layout_fresco_imageview, data);

                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner3.setAutoPlayAble(data.size() > 1);
                        mBanner3.setIsClipChildrenMode(true);
                        mBanner3.setBannerData(R.layout.layout_fresco_imageview, data);
                        mBanner3.setPageTransformer(Transformer.Default);
                        mBanner3.getViewPager().setOffscreenPageLimit(3);

                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner4.setAutoPlayAble(data.size() > 1);
                        mBanner4.setIsClipChildrenMode(true);
                        mBanner4.setBannerData(R.layout.layout_fresco_imageview, data);
                        mBanner4.setPageTransformer(Transformer.Default);
                    }
                });
    }

    /**
     * 加载本地图片
     */
    private void initLocalImage() {
        List<LocalImageInfo> data = new ArrayList<>();
        data.add(new LocalImageInfo(R.drawable.banner_placeholder));
        data.add(new LocalImageInfo(R.drawable.banner_placeholder));
        data.add(new LocalImageInfo(R.drawable.banner_placeholder));
        data.add(new LocalImageInfo(R.drawable.banner_placeholder));
        mBanner.setBannerData(data);
        mBanner.setAutoPlayAble(true);
    }
}
