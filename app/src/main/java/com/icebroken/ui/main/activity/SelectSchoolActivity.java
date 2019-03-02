package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.adapter.SelectSchoolAdpter;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.SchoolsBean;
import com.icebroken.widget.DividerItemDecoration;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.DisplayUtil;
import com.mocuz.common.commonutils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectSchoolActivity extends BaseActivity {
    @Bind(R.id.select_list_rv)
    RecyclerView select_list_rv;
    private SelectSchoolAdpter adapter;
    @Bind(R.id.search_bar_cet)
    EditText search_bar_cet;
    private List<SchoolsBean> beans;
    private ArrayList<SchoolsBean> searchList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_school;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new SelectSchoolAdpter(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("codeId", adapter.getData().get(position).getId())
                        .putExtra("address", adapter.getData().get(position).getName()));
                finish();
            }
        });

        select_list_rv.setLayoutManager(new LinearLayoutManager(this));
        select_list_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST, DisplayUtil.dip2px(1), ContextCompat.getColor(this, R.color.form_hint_text_color)));
        select_list_rv.setHasFixedSize(true);
        select_list_rv.setAdapter(adapter);

        search_bar_cet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    findResourse(search_bar_cet.getText().toString());
                }
                return handled;
            }
        });
        search_bar_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                findResourse(s.toString());
            }
        });

        selectSchool("南京市");//3201是南京的id
    }

    @OnClick({R.id.left_text,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                finish();
                break;
        }
    }


    private void selectSchool(final String cityId) {
        Api.getDefault(HostType.MAIN).selectSchool(cityId)
                .compose(RxHelper.<List<SchoolsBean>>handleResult()).subscribe(new RxSubscriber<List<SchoolsBean>>(this, true) {
            @Override
            protected void _onNext(final List<SchoolsBean> schoolAddressBean) {
                beans = schoolAddressBean;
                adapter.setNewData(schoolAddressBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    private void findResourse(String s) {
        if (StringUtil.isBlank(s)) {
            adapter.setNewData(beans);
        } else {
            searchList = new ArrayList<>();
            search(beans, s);
            adapter.setNewData(searchList);
        }
    }

    private void search(List<SchoolsBean> list, String s) {
        if (list == null) {
            return;
        }
        for (SchoolsBean user : list) {
            if (user == null) {
                continue;
            }
            if (StringUtil.isNotBlank(user.getName()) && user.getName().contains(s)) {
                searchList.add(user);
            }
        }
    }
}
