package com.icebroken.ui.main.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icebroken.R;

public class DynamicAdpter extends BaseQuickAdapter {
    public DynamicAdpter() {
        super(R.layout.dynamic_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
