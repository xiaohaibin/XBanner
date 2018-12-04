package com.stx.xhb.xbanner.transformers;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by jxnk25 on 2016/10/18.
 * <p>
 * link https://xiaohaibin.github.io/
 * email： xhb_199409@163.com
 * github: https://github.com/xiaohaibin
 * description：AccordionPageTransformer
 */
public class AccordionPageTransformer extends BasePageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setPivotX(view.getWidth());
        view.setScaleX(1.0f + position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setPivotX(0);
        view.setScaleX(1.0f - position);
        view.setAlpha(1);
    }

}