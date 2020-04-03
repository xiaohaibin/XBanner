package com.stx.xhb.demo.entity;


import com.stx.xhb.xbanner.entity.BaseBannerInfo;

/**
 * @author: xiaohaibin.
 * @time: 2018/12/3
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: CustomViewsInfo 继承 SimpleBannerInfo 根据个人情况重载两个方法
 */
public class CustomViewsInfo implements BaseBannerInfo {

    private String info;

    public CustomViewsInfo(String info) {
        this.info = info;
    }

    @Override
    public String getXBannerUrl() {
        return info;
    }

    @Override
    public String getXBannerTitle() {
        return null;
    }
}
