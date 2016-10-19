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
public class RotatePageTransformer extends BasePageTransformer {
    private float mMaxRotation = 15.0f;

    public RotatePageTransformer() {
    }

    public RotatePageTransformer(float maxRotation) {
        setMaxRotation(maxRotation);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
        ViewHelper.setPivotY(view, view.getMeasuredHeight());
        ViewHelper.setRotation(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        float rotation = (mMaxRotation * position);
        ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
        ViewHelper.setPivotY(view, view.getMeasuredHeight());
        ViewHelper.setRotation(view, rotation);
    }

    @Override
    public void handleRightPage(View view, float position) {
        handleLeftPage(view, position);
    }

    public void setMaxRotation(float maxRotation) {
        if (maxRotation >= 0.0f && maxRotation <= 40.0f) {
            mMaxRotation = maxRotation;
        }
    }
}