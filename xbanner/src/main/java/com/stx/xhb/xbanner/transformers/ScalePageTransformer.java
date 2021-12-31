package com.stx.xhb.xbanner.transformers;

import android.view.View;

/**
 * author: xiaohaibin.
 * time: 2018/10/9
 * mail:xhb_199409@163.com
 * github:https://github.com/xiaohaibin
 * describe: 适用于一屏显示多个模式
 */
public class ScalePageTransformer extends BasePageTransformer {
    private static final float MIN_SCALE = 0.8F;

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setScaleY(MIN_SCALE);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
        view.setScaleY(scale);
    }

    @Override
    public void handleRightPage(View view, float position) {
        float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
        view.setScaleY(scale);
    }
}
