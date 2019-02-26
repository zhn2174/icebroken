package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.UserInfo;
import com.icebroken.bean.accountExistBean;
import com.icebroken.utils.BaseUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class SetPwdActivity extends BaseActivity {


    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.ed_pwd)
    EditText edPwd;
    @Bind(R.id.ed_pwd2)
    EditText edPwd2;
    @Bind(R.id.tv_cue)
    TextView tvCue;
    @Bind(R.id.bt_login)
    TextView btLogin;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.main)
    RelativeLayout main;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.eye)
    ImageView eye;
    @Bind(R.id.eye1)
    ImageView eye1;

    @Override
    public int getLayoutId() {
        return R.layout.login_set_pwd;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
//                MainActivity.startAction(LoginActivity.this);
            }
        });
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.hideInput(mContext);
                finish();
            }
        });

        edPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edPwd.getText().toString()) && !TextUtils.isEmpty(edPwd2.getText().toString())) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);

                }
            }
        });
        edPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edPwd.getText().toString()) && !TextUtils.isEmpty(edPwd2.getText().toString())) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);

                }
            }
        });

//        edPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        edPwd2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }

    private void gotoLogin() {
        if (checkInfo()) {
            Login();
//            OrganizingDataActivity.startAction(this);
        }
    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(edPwd.getText().toString())) {
            ToastUitl.showShort("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(edPwd2.getText().toString())) {
            ToastUitl.showShort("请输入密码");
            return false;
        }
        if (!TextUtils.equals(edPwd.getText().toString(), edPwd2.getText().toString())) {
            ToastUitl.showShort("两次输入的密码不同");
            tvCue.setText("两次输入的密码不同");
            return false;
        }
        return true;
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SetPwdActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 匹配号码是否是11为数字
     *
     * @param phoneNum
     * @return 不是11位数字，返回false
     */
    public static boolean checkPhoneNum(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            String format = "^\\d{11}$";
            Pattern p = Pattern.compile(format);
            Matcher m = p.matcher(phoneNum);
            return m.matches();
        } else {
            return false;
        }

    }

    private void Login() {
        showProgressDialog("正在设置密码");
        JSONObject map = new JSONObject();
        try {
            map.put("phone", AppApplication.phone);
            map.put("countryCode", "86");
            map.put("code", AppApplication.code);
            map.put("password", edPwd.getText().toString());
//            map.put("qqId", edPwd.getText().toString());
//            map.put("wechatId", edPwd.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).register(map.toString())
                .compose(RxHelper.<accountExistBean>handleResult()).subscribe(new RxSubscriber<accountExistBean>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                hideProgressDialog();
                showShortToast(e);
            }

            @Override
            public void _onNext(accountExistBean bean) {
                hideProgressDialog();
                AppApplication.token = bean.getToken();
                UserInfo userInfo = new UserInfo();
                userInfo.setComplete(bean.getComplete());
                userInfo.setCompleteSchool(bean.getCompleteSchool());
                AppApplication.setUserInfo(userInfo);
                SharedPreferences pref = SetPwdActivity.this.getSharedPreferences("token", Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = pref.edit();
                //存入数据
                editor.putString("token", AppApplication.token);
                //提交修改
                editor.commit();
                OrganizingDataActivity.startAction(SetPwdActivity.this);
                showShortToast("登录成功");
            }
        });
    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        gotoLogin();
    }


    @OnClick({R.id.eye, R.id.eye1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eye:
                int cursorPosition = edPwd.length();
                int isVisible = edPwd.getInputType();
                if (isVisible == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
//                    mPwdIsVisible.setImageResource(R.drawable.password_visible);
                    edPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edPwd.setSelection(cursorPosition);
                }else{
//                    mPwdIsVisible.setImageResource(R.drawable.password_unvisible);
                    edPwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPwd.setSelection(cursorPosition);
                }
                break;
            case R.id.eye1:
                int cursorPosition2 = edPwd2.length();
                int isVisible2 = edPwd2.getInputType();
                if (isVisible2 == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
//                    mPwdIsVisible.setImageResource(R.drawable.password_visible);
                    edPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edPwd2.setSelection(cursorPosition2);
                }else{
//                    mPwdIsVisible.setImageResource(R.drawable.password_unvisible);
                    edPwd2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPwd2.setSelection(cursorPosition2);
                }
                break;
        }
    }
}
