package com.stx.xhb.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.demo.entity.TuchongEntity;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * listview 添加headview使用 RecycleView上也是同样的
 */
public class ListViewActivity extends AppCompatActivity {

    private XBanner mXBanner;
    private android.widget.ListView mLv;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        requsetData(false);
        initView();
        setAdapter();
        setListener();
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.transforms));
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //默认切换动画
                    case 0:
                        mXBanner.setPageTransformer(Transformer.Default);
                        break;
                    case 1:
                        mXBanner.setPageTransformer(Transformer.Alpha);
                        break;
                    case 2:
                        mXBanner.setPageTransformer(Transformer.Rotate);
                        break;
                    case 3:
                        mXBanner.setPageTransformer(Transformer.Cube);
                        break;
                    case 4:
                        mXBanner.setPageTransformer(Transformer.Flip);
                        break;
                    case 5:
                        mXBanner.setPageTransformer(Transformer.Accordion);
                        break;
                    case 6:
                        mXBanner.setPageTransformer(Transformer.ZoomFade);
                        break;
                    case 7:
                        mXBanner.setPageTransformer(Transformer.ZoomCenter);
                        break;
                    case 8:
                        mXBanner.setPageTransformer(Transformer.ZoomStack);
                        break;
                    case 9:
                        mXBanner.setPageTransformer(Transformer.Stack);
                        break;
                    case 10:
                        mXBanner.setPageTransformer(Transformer.Depth);
                        break;
                    case 11:
                        mXBanner.setPageTransformer(Transformer.Zoom);
                        break;
                    default:
                        mXBanner.setPageTransformer(Transformer.Default);
                        break;
                }
            }
        });
    }

    /**
     * 加载网络数据
     */
    private void requsetData(final boolean refresh) {
        //加载网络图片资源
        String url = "https://api.tuchong.com/2/wall-paper/app";
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
                        TuchongEntity advertiseEntity = new Gson().fromJson(response, TuchongEntity.class);
                        List<TuchongEntity.FeedListBean> others = advertiseEntity.getFeedList();
                        List<TuchongEntity.FeedListBean.EntryBean> data = new ArrayList<>();
//                        int size = refresh ? 4 : others;
                        for (int i = 0; i <others.size(); i++) {
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

    /**
     * 初始化View
     */
    private void initView() {
        mLv = findViewById(R.id.lv);
        mRefreshLayout = findViewById(R.id.refresh_layout);

        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.ad_head, null);
        mXBanner = headerView.findViewById(R.id.banner);
        mXBanner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this) / 2));
        mLv.addHeaderView(headerView);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requsetData(true);
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
                Toast.makeText(ListViewActivity.this, "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean.EntryBean) model);
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                Glide.with(ListViewActivity.this).load(url).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });
    }

    /**
     * 为了更好的体验效果建议在下面两个生命周期中调用下面的方法
     **/
    @Override
    protected void onResume() {
        super.onResume();
        mXBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mXBanner.stopAutoPlay();
    }
}
