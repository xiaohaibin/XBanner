package com.stx.xhb.xbanner.transformers;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by jxnk25 on 2016/10/18.
 *
 * link https://xiaohaibin.github.io/
 * email： xhb_199409@163.com
 * github: https://github.com/xiaohaibin
 * description：
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
        ViewCompat.setAlpha(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha( 1);
        view.setTranslationX( 0);
        view.setScaleX( 1);
        view.setScaleY(1);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha( 1 - position);
        view.setTranslationX( -view.getWidth() * position);
        float scale = mMinScale + (1 - mMinScale) * (1 - position);
        view.setScaleX(scale);
        view.setScaleY( scale);
    }

    public void setMinScale(float minScale) {
        if (minScale >= 0.6f && minScale <= 1.0f) {
            mMinScale = minScale;
        }
    }

}