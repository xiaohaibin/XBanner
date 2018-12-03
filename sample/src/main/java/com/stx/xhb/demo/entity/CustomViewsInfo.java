package com.stx.xhb.demo.entity;

import com.stx.xhb.xbanner.entity.AbstractBannerInfo;

/**
 * @author: xiaohaibin.
 * @time: 2018/12/3
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
public class CustomViewsInfo extends AbstractBannerInfo {

    private String info;

    public CustomViewsInfo(String info) {
        this.info = info;
    }

    @Override
    public String getBannerUrl() {
        return info;
    }

    @Override
    public String getBannerTitle() {
        return "";
    }
}
