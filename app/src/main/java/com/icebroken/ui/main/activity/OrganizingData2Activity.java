package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.JsonBean;
import com.icebroken.bean.UserInfo;
import com.icebroken.utils.BaseUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.TimeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Yorashe on 19-2-18.
 */

public class OrganizingData2Activity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.school_text)
    TextView schoolText;
    @Bind(R.id.school_select)
    RelativeLayout schoolSelect;
    @Bind(R.id.school_ed)
    EditText schoolEd;
    @Bind(R.id.start_school_text)
    TextView startSchoolText;
    @Bind(R.id.start_school_select)
    RelativeLayout startSchoolSelect;
    @Bind(R.id.identity_text)
    TextView identityText;
    @Bind(R.id.identity_select)
    RelativeLayout identitySelect;
    @Bind(R.id.student_text)
    TextView studentText;
    @Bind(R.id.student_select)
    RelativeLayout studentSelect;
    @Bind(R.id.talk_ck)
    CheckBox talkCk;
    @Bind(R.id.talk_select)
    RelativeLayout talkSelect;
    @Bind(R.id.bt_login)
    TextView btLogin;
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private TimePickerView pvCustomLunar;
    private static boolean isLoaded = false;
    private UserInfo userInfo;
    private String[] mIdentity_str = {"大学生", "研究生", "博士生"};
    private List<String> mIdentitys = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.organizing_s_lay;
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
        initLunarPicker();
        userInfo = AppApplication.getUserInfo();
        initData();
    }

    private void initData() {
        mIdentitys.add("0");
        mIdentitys.add("1");
        mIdentitys.add("2");

        //男性默认关闭，女性打开
        if (userInfo != null && userInfo.getSex() == 1) {
            talkCk.setChecked(false);
        }
    }

    private void post() {
        if (TextUtils.isEmpty(schoolText.getText())) {
            showShortToast("请填写学校");
            return;
        }
        if (TextUtils.isEmpty(schoolEd.getText())) {
            showShortToast("请填写院系");
            return;
        }
        if (TextUtils.isEmpty(startSchoolText.getText())) {
            showShortToast("请选择入学年");
            return;
        }
        if (TextUtils.isEmpty(identityText.getText())) {
            showShortToast("请选择身份");
            return;
        }
        if (!userInfo.isCertify()) {
            showShortToast("请先认证身份");
            return;
        }

        com.alibaba.fastjson.JSONObject params = new com.alibaba.fastjson.JSONObject(true);
        params.put("schoolId", userInfo.getSchoolId());
        params.put("department", schoolEd.getText().toString());
        params.put("enterSchoolYear", userInfo.getEnterSchoolYear());
        for (int i = 0; i < mIdentity_str.length; i++) {
            if (mIdentity_str[i].equals(identityText.getText().toString())) {
                params.put("identity", i);
            }
        }
        params.put("isAllowDirectChat", talkCk.isChecked() ? "1" : "0");

        showProgressDialog("正在完善资料");
        Api.getDefault(HostType.MAIN).completeInformation(params.toJSONString())
                .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                hideProgressDialog();
            }

            @Override
            public void _onNext(Object bean) {
                userInfo.setDepartment(schoolEd.getText().toString());
                userInfo.setIdentity(identityText.getText().toString());
                userInfo.setIsAllowDirectChat(talkCk.isChecked() ? "1" : "0");
                AppApplication.setUserInfo(userInfo);

                hideProgressDialog();
                showShortToast("资料完善成功");
            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(TimeUtil.getYear(System.currentTimeMillis()), 1, 1);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                startSchoolText.setText(getTime(date));
                userInfo.setEnterSchoolYear(Integer.valueOf(getTime(date)));
            }
        })
                .setDate(endDate)
                .setRangDate(startDate, endDate)
                .setType(new boolean[]{true, false, false, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();

    }


    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void queryInfo() {
        Api.getDefault(HostType.MAIN).getUserInfo()
                .compose(RxHelper.<UserInfo>handleResult()).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                hideProgressDialog();
            }

            @Override
            public void _onNext(UserInfo bean) {
                userInfo.setCertify(bean.isCertify());
                try {
                    if (null != userInfo && userInfo.isCertify()) {
                        studentText.setText("已认证");
                        studentSelect.setEnabled(false);
                    } else {
                        studentText.setText("去认证");
                        studentSelect.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppApplication.setUserInfo(userInfo);
            }
        });
    }

    @OnClick({R.id.bt_login, R.id.school_select, R.id.start_school_select, R.id.identity_select, R.id.student_select, R.id.talk_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                post();
                break;
            case R.id.school_select:
                startActivityForResult(SelectSchoolActivity.class, 1);
                break;
            case R.id.start_school_select:
                pvCustomLunar.show();
                BaseUtil.hideInput(mContext);
                break;
            case R.id.identity_select:
                ShowPop(mIdentity_str, mIdentitys, "选择身份", identityText);
                break;
            case R.id.student_select:
                AuthenticationActivity.startAction(this);
                break;
            case R.id.talk_select:
                talkCk.performClick();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryInfo();
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, OrganizingData2Activity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("codeId")) {
                userInfo.setSchoolId(data.getIntExtra("codeId", 0));
                schoolText.setText(data.getStringExtra("address"));
            }
        }
    }
}
