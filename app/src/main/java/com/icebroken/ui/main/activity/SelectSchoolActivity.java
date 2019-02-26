package com.icebroken.ui.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.icebroken.R;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.utils.BaseUtil;
import com.icebroken.widget.MyToolbar;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectSchoolActivity extends BaseActivity {
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_school;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.left_text,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                finish();
                break;
        }
    }
}
