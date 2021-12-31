package com.stx.xhb.androidx.transformers;

import android.view.View;

import androidx.core.view.ViewCompat;

/**
 * author: xiaohaibin.
 * time: 2018/10/9
 * mail:xhb_199409@163.com
 * github:https://github.com/xiaohaibin
 * describe: 适用于一屏显示多个模式
 */
public class OverLapPageTransformer extends BasePageTransformer {

    private float scaleValue = 0.8F;
    private float alphaValue = 1f;

    public OverLapPageTransformer() {
    }

    public OverLapPageTransformer(float scaleValue, float alphaValue) {
        this.scaleValue = scaleValue;
        this.alphaValue = alphaValue;
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(1);
        view.setScaleY(scaleValue);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(1 + position * (1 - alphaValue));
        ViewCompat.setTranslationZ(view, position);
        float scale = Math.max(scaleValue, 1 - Math.abs(position));
        view.setScaleY(scale);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(1 - position * (1 - alphaValue));
        ViewCompat.setTranslationZ(view, -position);
        float scale = Math.max(scaleValue, 1 - Math.abs(position));
        view.setScaleY(scale);
    }
}
