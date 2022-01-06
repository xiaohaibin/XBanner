package com.stx.xhb.xbanner.transformers;

import android.support.v4.view.ViewCompat;
import android.view.View;

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
        view.setScaleX(scaleValue);
        view.setScaleY(scaleValue);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(1 + position * (1 - alphaValue));
        float scale = Math.max(scaleValue, 1 - Math.abs(position));
        view.setScaleX(scale);
        view.setScaleY(scale);
        ViewCompat.setTranslationZ(view, position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(1 - position * (1 - alphaValue));
        float scale = Math.max(scaleValue, 1 - Math.abs(position));
        view.setScaleX(scale);
        view.setScaleY(scale);
        ViewCompat.setTranslationZ(view, -position);
    }
}
