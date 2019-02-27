package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.adapter.SelectAddressAdpter;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.CityAddressBean;
import com.icebroken.widget.DividerItemDecoration;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.DisplayUtil;
import com.mocuz.common.commonutils.ToastUitl;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectAddressActivity extends BaseActivity {
    @Bind(R.id.select_address_rv)
    RecyclerView select_address_rv;

    private SelectAddressAdpter adapter;
    private int level = 0;//最高三级
    private int parentCode;
    private String[] address = new String[3];


    @Override
    public int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new SelectAddressAdpter(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                address[level - 1] = adapter.getData().get(position).getName();
                if (level == 3) {
                    StringBuffer str = new StringBuffer();
                    for (String s : address) {
                        str.append(s);
                    }
                    setResult(Activity.RESULT_OK, new Intent()
                            .putExtra("codeId", adapter.getData().get(position).getCode())
                            .putExtra("address", str.toString()));
                    finish();
                } else {
                    level += 1;
                    parentCode = adapter.getData().get(position).getParentCode();
                    selecAddress(adapter.getData().get(position).getCode());
                }
            }
        });

        select_address_rv.setLayoutManager(new LinearLayoutManager(this));
        select_address_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST, DisplayUtil.dip2px(1), ContextCompat.getColor(this, R.color.form_hint_text_color)));
        select_address_rv.setHasFixedSize(true);
        select_address_rv.setAdapter(adapter);

        selecAddress(0);
        level = 1;

    }

    @OnClick({R.id.left_text,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                if (level > 1 && adapter.getItemCount() > 0) {
                    level -= 1;
                    address[level] = "";
                    selecAddress(parentCode);
                } else {
                    finish();
                }
                break;
        }
    }

    private void selecAddress(final int codeId) {
        //type=1 选择学校使用，type=0 正常使用
        Api.getDefault(HostType.MAIN).selectAddress(codeId, 0)
                .compose(RxHelper.<List<CityAddressBean>>handleResult()).subscribe(new RxSubscriber<List<CityAddressBean>>(this, true) {
            @Override
            protected void _onNext(List<CityAddressBean> schoolAddressBean) {
                if (schoolAddressBean != null && schoolAddressBean.size() > 0) {
                    adapter.setNewData(schoolAddressBean);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (level > 1 && adapter.getItemCount() > 0) {
                level -= 1;
                address[level] = "";
                selecAddress(parentCode);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
