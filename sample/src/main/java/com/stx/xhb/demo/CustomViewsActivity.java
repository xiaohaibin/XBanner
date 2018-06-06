package com.stx.xhb.demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class CustomViewsActivity extends AppCompatActivity {

    private XBanner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_views);
        initView();
    }

    private void initView() {
        mBanner = (XBanner) findViewById(R.id.banner);
        List<View> data = new ArrayList<>();
        final List<String> colors = new ArrayList<>();
        colors.add("#FFA54F");
        colors.add("#8EE5EE");
        colors.add("#00FA9A");
        colors.add("#CD8162");
        for (int position = 0; position < 4; position++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null);
            TextView tvContent = (TextView) view.findViewById(R.id.tv);
            tvContent.setText(String.valueOf(position + 1));
            view.setBackgroundColor(Color.parseColor(colors.get(position)));
            data.add(view);
        }
        mBanner.setData(R.layout.layout_custom_view,data, null);
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                TextView tvContent = (TextView) view.findViewById(R.id.tv);
                tvContent.setText(String.valueOf(position + 1));
                view.setBackgroundColor(Color.parseColor(colors.get(position)));
            }
        });
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

}
