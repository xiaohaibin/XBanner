package com.stx.xhb.demo.entity;


import com.stx.xhb.androidx.entity.SimpleBannerInfo;

/**
 * author: xiaohaibin.
 * time: 2018/12/3
 * mail:xhb_199409@163.com
 * github:https://github.com/xiaohaibin
 * describe: CustomViewsInfo 继承 SimpleBannerInfo 根据个人情况重载两个方法
 */
public class CustomViewsInfo extends SimpleBannerInfo {

    private String info;

    public CustomViewsInfo(String info) {
        this.info = info;
    }

    @Override
    public String getXBannerUrl() {
        return info;
    }
}
