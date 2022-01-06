package com.stx.xhb.demo.holder;

import com.stx.xhb.xbanner.holder.HolderCreator;
import com.stx.xhb.xbanner.holder.ViewHolder;

public class BannerHolderCreator implements HolderCreator<ViewHolder> {
    public VideoViewHolder videoViewHolder;

    public BannerHolderCreator() {
        this.videoViewHolder = new VideoViewHolder();
    }

    @Override
    public ViewHolder createViewHolder(int viewType) {
        if (viewType==0){
            return videoViewHolder;
        }
        return new ImageViewHolder();
    }

    @Override
    public int getViewType(int position) {
        return position;
    }
}
