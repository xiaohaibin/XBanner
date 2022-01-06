package com.stx.xhb.demo.holder;

import android.view.View;

import com.stx.xhb.androidx.holder.ViewHolder;
import com.stx.xhb.demo.R;
import com.stx.xhb.demo.entity.CustomViewsInfo;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class VideoViewHolder implements ViewHolder<CustomViewsInfo> {
    public VideoView videoView;

    @Override
    public int getLayoutId() {
        return R.layout.layout_video_view;
    }

    @Override
    public void onBind(View itemView, CustomViewsInfo data, int position) {
        videoView = itemView.findViewById(R.id.player);
        videoView.setUrl("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4");
        StandardVideoController controller = new StandardVideoController(itemView.getContext());
        controller.addDefaultControlComponent("标题", false);
        videoView.setVideoController(controller); //设置控制器
    }
}
