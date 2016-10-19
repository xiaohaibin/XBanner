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
public class CubePageTransformer extends BasePageTransformer {
    private float mMaxRotation = 90.0f;

    public CubePageTransformer() {
    }

    public CubePageTransformer(float maxRotation) {
        setMaxRotation(maxRotation);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewHelper.setPivotX(view, view.getMeasuredWidth());
        ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewHelper.setRotationY(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewHelper.setPivotX(view, view.getMeasuredWidth());
        ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewHelper.setRotationY(view, mMaxRotation * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewHelper.setPivotX(view, 0);
        ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewHelper.setRotationY(view, mMaxRotation * position);
    }

    public void setMaxRotation(float maxRotation) {
        if (maxRotation >= 0.0f && maxRotation <= 90.0f) {
            mMaxRotation = maxRotation;
        }
    }

}
