package com.stx.xhb.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
        final List<String> data = new ArrayList<>();
        data.add("#FFA54F");
        data.add("#8EE5EE");
        data.add("#00FA9A");
        data.add("#CD8162");
        banner.setData(R.layout.layout_custom_view,data, null);
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                TextView tvContent = (TextView) view.findViewById(R.id.tv);
                tvContent.setText(String.valueOf(position + 1));
                view.setBackgroundColor(Color.parseColor((String) model));
            }
        });
    }

}
