package com.icebroken.ui.main.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.adapter.SelectAddressAdpter;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.SchoolAddressBean;
import com.icebroken.widget.DividerItemDecoration;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.DisplayUtil;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectSchoolActivity extends BaseActivity {
    @Bind(R.id.select_province)
    NiceSpinner select_province;
    @Bind(R.id.select_city)
    NiceSpinner select_city;
    @Bind(R.id.select_school)
    NiceSpinner select_school;
    @Bind(R.id.select_list_rv)
    RecyclerView select_list_rv;
    private SelectAddressAdpter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_school;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new SelectAddressAdpter(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        select_list_rv.setLayoutManager(new LinearLayoutManager(this));
        select_list_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST, DisplayUtil.dip2px(1), ContextCompat.getColor(this, R.color.form_hint_text_color)));
        select_list_rv.setHasFixedSize(true);
        select_list_rv.setAdapter(adapter);
    }

    @OnClick({R.id.left_text,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                finish();
                break;
        }
    }


    private void selectSchool(final int codeId) {
        //type=1 选择学校使用，type=0 正常使用
        Api.getDefault(HostType.MAIN).selectAddress(codeId, 1)
                .compose(RxHelper.<List<SchoolAddressBean>>handleResult()).subscribe(new RxSubscriber<List<SchoolAddressBean>>(this, true) {
            @Override
            protected void _onNext(List<SchoolAddressBean> schoolAddressBean) {
                if (schoolAddressBean != null) {
                    if (codeId == 0) {

                    } else {

                    }
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
