package com.icebroken.ui.main.fragment;

import android.support.v7.widget.RecyclerView;

import com.icebroken.R;
import com.icebroken.base.BaseFragment;

import butterknife.Bind;

public class SameSchoolFragment extends BaseFragment {
    @Bind(R.id.same_school_rv)
    RecyclerView same_school_rv;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_same_school;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }
}
