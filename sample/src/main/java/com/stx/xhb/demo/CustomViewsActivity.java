package com.stx.xhb.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stx.xhb.demo.entity.CustomViewsInfo;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义布局
 */
public class CustomViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_views);
        initView();
    }

    private void initView() {
        XBanner banner = (XBanner) findViewById(R.id.banner);
        List<CustomViewsInfo> data = new ArrayList<>();
        data.add(new CustomViewsInfo("#FFA54F"));
        data.add(new CustomViewsInfo("#8EE5EE"));
        data.add(new CustomViewsInfo("#00FA9A"));
        data.add(new CustomViewsInfo("#CD8162"));
        banner.setBannerData(R.layout.layout_custom_view, data);
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                TextView tvContent = (TextView) view.findViewById(R.id.tv);
                tvContent.setText(String.valueOf(position + 1));
                view.setBackgroundColor(Color.parseColor(((CustomViewsInfo) model).getXBannerUrl()));
            }
        });
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(CustomViewsActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
