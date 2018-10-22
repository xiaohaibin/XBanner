package com.stx.xhb.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.stx.xhb.demo.entity.TuchongEntity;
import com.stx.xhb.xbanner.XBanner;
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
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean) model).getEntry();
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                draweeView.setImageURI(Uri.parse(url));

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
                        List<TuchongEntity.FeedListBean> data=new ArrayList<>();
                        for (int i = 0; i < others.size(); i++) {
                            TuchongEntity.FeedListBean feedListBean = others.get(i);
                            if ("post".equals(feedListBean.getType())){
                                data.add(feedListBean);
                            }
                        }
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        mBanner.setAutoPlayAble(data.size() > 1);
                        mBanner.setData(R.layout.layout_fresco_imageview, data, null);
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
