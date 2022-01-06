package com.stx.xhb.demo.holder;

import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.stx.xhb.demo.R;
import com.stx.xhb.demo.entity.CustomViewsInfo;
import com.stx.xhb.xbanner.holder.ViewHolder;

public class ImageViewHolder implements ViewHolder<CustomViewsInfo> {

    @Override
    public int getLayoutId() {
        return R.layout.layout_image_view;
    }

    @Override
    public void onBind(View itemView, CustomViewsInfo data, int position) {
        ImageView imageView = itemView.findViewById(R.id.iv);
        Glide.with(itemView.getContext()).load(data.getXBannerUrl()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(imageView);
    }
}
