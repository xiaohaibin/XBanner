package com.stx.xhb.xbanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MainActivity extends AppCompatActivity implements XBanner.XBannerAdapter {

    private XBanner mXBanner;
    private RadioGroup mRadioGroup;
    private List<AdvertiseEntity.OthersBean> mOthersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        RequestData();
        setAdapter();
        setListener();
    }



    private void initView() {
        mXBanner = (XBanner) findViewById(R.id.xbanner);
        mRadioGroup = (RadioGroup) findViewById(R.id.rgp);
        RadioButton rb= (RadioButton) mRadioGroup.getChildAt(3);
        rb.setChecked(true);
    }

    private void setListener() {
          mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  switch (checkedId){
                      case 1://默认切换动画
                          mXBanner.setPageTransformer(Transformer.Default);
                          break;
                      case 2:
                          mXBanner.setPageTransformer(Transformer.Alpha);
                          break;
                      case 3:
                          mXBanner.setPageTransformer(Transformer.Rotate);
                          break;
                      case 4:
                          mXBanner.setPageTransformer(Transformer.Cube);
                          break;
                      case 5:
                          mXBanner.setPageTransformer(Transformer.Flip);
                          break;
                      case 6:
                          mXBanner.setPageTransformer(Transformer.Accordion);
                          break;
                      case 7:
                          mXBanner.setPageTransformer(Transformer.ZoomFade);
                          break;
                      case 8:
                          mXBanner.setPageTransformer(Transformer.ZoomCenter);
                          break;
                      case 9:
                          mXBanner.setPageTransformer(Transformer.ZoomStack);
                          break;
                      case 10:
                          mXBanner.setPageTransformer(Transformer.Stack);
                          break;
                      case 11:
                          mXBanner.setPageTransformer(Transformer.Depth);
                          break;
                      case 12:
                          mXBanner.setPageTransformer(Transformer.Zoom);
                          break;
                      default:
                          mXBanner.setPageTransformer(Transformer.Default);
                          break;
                  }
              }
          });

            //设置广告图片点击事件
            mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, int position) {
                    Toast.makeText(MainActivity.this, "点击了第" + (position+1) + "图片", Toast.LENGTH_SHORT).show();
                }
            });
    }
    private void setAdapter() {
             //加载广告图片
             mXBanner.setmAdapter(this);
    }

    private void RequestData() {
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
                        mOthersList = advertiseEntity.getOthers();
                        List<String> tips = new ArrayList<String>();
                        for (int i = 0; i < mOthersList.size(); i++) {
                            tips.add(mOthersList.get(i).getDescription());
                        }
                        mXBanner.setData(mOthersList, tips);
                        mXBanner.setData(R.layout.xbanner_item_image,mOthersList,tips);
                    }
                });

    }

    @Override
    public void loadBanner(XBanner banner, View view, int position) {
        Log.i("--->position",position+"");
        Glide.with(this).load(mOthersList.get(position).getThumbnail()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
    }

    /** 为了更好的体验效果建议在下面两个生命周期中调用下面的方法 **/
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

    public void onClick(View view) {
        startActivity(new Intent(this,ListView.class));
    }
}
