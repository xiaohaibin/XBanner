package com.stx.xhb.xbanner.holder;

import android.support.annotation.LayoutRes;
import android.view.View;


public interface ViewHolder<T> {

    /**
     * @return 轮播条目布局文件
     */
    @LayoutRes int getLayoutId();

    /**
     * 绑定数据
     */
    void onBind(View itemView, T data, int position);
}
