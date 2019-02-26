package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.utils.BaseUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baseapp.CacheUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/1.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.notiManager)
    RelativeLayout notiManager;
    @Bind(R.id.about)
    RelativeLayout about;
    @Bind(R.id.feedback)
    RelativeLayout feedback;
    @Bind(R.id.layoutVersion)
    RelativeLayout layoutVersion;
    @Bind(R.id.password)
    RelativeLayout password;
    @Bind(R.id.edit_password)
    RelativeLayout edit_password;
    @Bind(R.id.login_out)
    LinearLayout loginOut;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.setting_acty;
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
                BaseUtil.hideInput(SettingActivity.this);
                finish();
            }
        });
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppApplication.uid = null;
                CacheUtils.putString("uid", AppApplication.uid);

                LoginActivity.startAction(SettingActivity.this);
                finish();
                showShortToast("退出登录");
            }
        });
    }


    @OnClick({R.id.about, R.id.edit_password, R.id.feedback, R.id.password})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about:
                AboutActivity.startAction(SettingActivity.this);
                break;
            case R.id.edit_password:
                EditPwdActivity.startAction(SettingActivity.this);
                break;
            case R.id.feedback:
                FeedBackActivity.startAction(SettingActivity.this);
                break;

        }
    }
}
