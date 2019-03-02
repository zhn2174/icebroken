package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.adapter.SelectInstituteAdpter;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.InstituteBean;
import com.icebroken.widget.DividerItemDecoration;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.DisplayUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectInstituteActivity extends BaseActivity {

    @Bind(R.id.select_list_rv)
    RecyclerView select_list_rv;
    private SelectInstituteAdpter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_institute;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new SelectInstituteAdpter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("codeId", adapter.getData().get(position).getId())
                        .putExtra("address", adapter.getData().get(position).getDepartmentName()));
                finish();
            }
        });

        select_list_rv.setLayoutManager(new LinearLayoutManager(this));
        select_list_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST, DisplayUtil.dip2px(1), ContextCompat.getColor(this, R.color.form_hint_text_color)));
        select_list_rv.setHasFixedSize(true);
        select_list_rv.setAdapter(adapter);
        if (getIntent() != null && getIntent().hasExtra("schoolId"))
            selectInstitute(getIntent().getIntExtra("schoolId", 0));
    }

    @OnClick({R.id.left_text,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                finish();
                break;
        }
    }


    private void selectInstitute(final int schoolId) {
        Api.getDefault(HostType.MAIN).selectInstitute(schoolId)
                .compose(RxHelper.<List<InstituteBean>>handleResult()).subscribe(new RxSubscriber<List<InstituteBean>>(this, true) {
            @Override
            protected void _onNext(final List<InstituteBean> schoolAddressBean) {
                adapter.setNewData(schoolAddressBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
