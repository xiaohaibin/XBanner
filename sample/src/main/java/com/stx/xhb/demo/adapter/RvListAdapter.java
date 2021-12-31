package com.stx.xhb.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.stx.xhb.demo.R;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RvListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RvListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.text2, s);
    }
}
