package com.icebroken.ui.main.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.icebroken.R;
import com.icebroken.adapter.DynamicSelectAdapter;
import com.lxj.xpopup.impl.PartShadowPopupView;

public class DynamicSelectPopupView extends PartShadowPopupView {

    private DynamicSelectAdapter adapter;

    public DynamicSelectPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dynamic_select_popup_view; // 编写你自己的布局
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        // 实现一些UI的初始和逻辑处理
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dynamic_select_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapter = new DynamicSelectAdapter();
        adapter.bindToRecyclerView(recyclerView);
    }

    //拉取筛选列表
    private void getSelectData() {
    }
}


