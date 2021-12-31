package com.stx.xhb.xbanner;

import android.view.View;

/**
 * Author：xiaohaibin
 * Time：2017/9/1
 * Emil：xhb_199409@163.com
 * Github：https://github.com/xiaohaibin/
 * Describe：连续点击事件
 */

public abstract class OnDoubleClickListener implements View.OnClickListener {

    private int mThrottleFirstTime = 1000;
    private long mLastClickTime = 0;

    public OnDoubleClickListener() {
    }

    public OnDoubleClickListener(int throttleFirstTime) {
        mThrottleFirstTime = throttleFirstTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > mThrottleFirstTime) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
