package com.stx.xhb.mylibrary.transformers;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by jxnk25 on 2016/10/18.
 *
 * @link https://xiaohaibin.github.io/
 * @email： xhb_199409@163.com
 * @github: https://github.com/xiaohaibin
 * @description：
 */
public class DepthPageTransformer extends BasePageTransformer {
    private float mMinScale = 0.8f;

    public DepthPageTransformer() {
    }

    public DepthPageTransformer(float minScale) {
        setMinScale(minScale);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewHelper.setAlpha(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewHelper.setAlpha(view, 1);
        ViewHelper.setTranslationX(view, 0);
        ViewHelper.setScaleX(view, 1);
        ViewHelper.setScaleY(view, 1);
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewHelper.setAlpha(view, 1 - position);
        ViewHelper.setTranslationX(view, -view.getWidth() * position);
        float scale = mMinScale + (1 - mMinScale) * (1 - position);
        ViewHelper.setScaleX(view, scale);
        ViewHelper.setScaleY(view, scale);
    }

    public void setMinScale(float minScale) {
        if (minScale >= 0.6f && minScale <= 1.0f) {
            mMinScale = minScale;
        }
    }

}