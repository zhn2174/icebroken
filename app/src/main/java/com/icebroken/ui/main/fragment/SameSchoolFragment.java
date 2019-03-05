package com.icebroken.ui.main.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.base.BaseFragment;
import com.icebroken.ui.main.adapter.DynamicAdpter;
import com.lxj.xpopup.XPopup;

import butterknife.Bind;

public class SameSchoolFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.same_school_rv)
    RecyclerView same_school_rv;

    DynamicAdpter adapter;
    private View task_view;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_same_school;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        task_view = LayoutInflater.from(getContext()).inflate(R.layout.task_view, null);
        TextView task_select_tv = (TextView) task_view.findViewById(R.id.task_select_tv);
        task_select_tv.setOnClickListener(this);

        adapter = new DynamicAdpter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        same_school_rv.setLayoutManager(linearLayoutManager);
        same_school_rv.setNestedScrollingEnabled(true);
        same_school_rv.setHasFixedSize(true);

        same_school_rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_select_tv:
                XPopup.get(getActivity())
                        .asCustom(new DynamicSelectPopupView(getContext()))
                        .atView(task_view)
                        .show();
                break;
        }
    }
}
