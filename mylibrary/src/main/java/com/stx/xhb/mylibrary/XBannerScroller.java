package com.stx.xhb.mylibrary;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by jxnk25 on 2016/10/7.
 *
 * @link https://xiaohaibin.github.io/
 * @email： xhb_199409@163.com
 * @github: https://github.com/xiaohaibin
 * @description：
 */
public class XBannerScroller extends Scroller {

    private int mScrollDuration = 800;// 滑动速度,值越大滑动越慢

    public XBannerScroller(Context context) {
        super(context);
    }

    public XBannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void setScrollDuration(int scrollDuration) {
        mScrollDuration = scrollDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

}
