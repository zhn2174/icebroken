package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.base.BaseActivity;
import com.icebroken.widget.MyToolbar;

/**
 * Created by Administrator on 2018/7/1.
 */

public class AboutActivity extends BaseActivity {
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Override
    public int getLayoutId() {
        return R.layout.about_lay;
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
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
