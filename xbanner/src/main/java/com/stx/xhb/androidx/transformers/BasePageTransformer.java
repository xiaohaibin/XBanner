package com.stx.xhb.androidx.transformers;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by jxnk25 on 2016/10/18.
 * <p>
 * link https://xiaohaibin.github.io/
 * email： xhb_199409@163.com
 * github: https://github.com/xiaohaibin
 * description：
 */
public abstract class BasePageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        ViewPager viewPager;
        if (view.getParent() instanceof ViewPager) {
            viewPager = (ViewPager) view.getParent();
        } else {
            return;
        }
        position = getRealPosition(viewPager, view);
        if (position < -1.0f) {
            handleInvisiblePage(view, position);
        } else if (position <= 0.0f) {
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            handleRightPage(view, position);
        } else {
            handleInvisiblePage(view, position);
        }
    }

    /**
     * 重新计算position
     *
     * @param viewPager
     * @param page
     * @return
     */
    private float getRealPosition(ViewPager viewPager, View page) {
        int width = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
        return (float) (page.getLeft() - viewPager.getScrollX() - viewPager.getPaddingLeft()) / width;
    }

    public abstract void handleInvisiblePage(View view, float position);

    public abstract void handleLeftPage(View view, float position);

    public abstract void handleRightPage(View view, float position);

    public static BasePageTransformer getPageTransformer(Transformer effect) {
        switch (effect) {
            case Alpha:
                return new AlphaPageTransformer();
            case Rotate:
                return new RotatePageTransformer();
            case Cube:
                return new CubePageTransformer();
            case Flip:
                return new FlipPageTransformer();
            case Accordion:
                return new AccordionPageTransformer();
            case ZoomFade:
                return new ZoomFadePageTransformer();
            case ZoomCenter:
                return new ZoomCenterPageTransformer();
            case ZoomStack:
                return new ZoomStackPageTransformer();
            case Stack:
                return new StackPageTransformer();
            case Depth:
                return new DepthPageTransformer();
            case Zoom:
                return new ZoomPageTransformer();
            case Scale:
                return new ScalePageTransformer();
            case OverLap:
                return new OverLapPageTransformer();
            default:
                return new DefaultPageTransformer();
        }
    }
}