package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.LoginBean;
import com.icebroken.bean.UserInfo;
import com.icebroken.bean.accountExistBean;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ToastUitl;
import com.tencent.android.tpush.XGPushManager;
import com.wevey.selector.BtSelectionDialog;

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

public class LoginPwdActivity extends BaseActivity {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.ed_phone)
    EditText edPhone;
    @Bind(R.id.bt_login)
    TextView btLogin;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.main)
    RelativeLayout main;
    @Bind(R.id.need_help)
    TextView needHelp;
    @Bind(R.id.code_login)
    TextView codeLogin;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;

    @Override
    public int getLayoutId() {
        return R.layout.login_pwd_lay;
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
        edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);

                }
            }
        });
    }

    private void gotoLogin() {
        if (checkInfo()) {
            Login();
//            OrganizingDataActivity.startAction(this);
        }
    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(edPhone.getText().toString())) {
            ToastUitl.showShort("请输入密码");
            return false;
        }
        return true;
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginPwdActivity.class);
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
        showProgressDialog("正在登录");
        JSONObject map = new JSONObject();
        try {
            map.put("type", 1);
            map.put("phone", AppApplication.phone);
            map.put("password", edPhone.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).login(map.toString())
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
                UserInfo userInfo =new UserInfo();
                userInfo.setComplete(bean.getComplete());
                userInfo.setCompleteSchool(bean.getCompleteSchool());
                AppApplication.setUserInfo(userInfo);
                String errMsg = null;
                AppApplication.token = bean.getToken();
                SharedPreferences pref = LoginPwdActivity.this.getSharedPreferences("token", Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = pref.edit();
                //存入数据
                editor.putString("token", AppApplication.token);
                //提交修改
                editor.commit();
                OrganizingDataActivity.startAction(LoginPwdActivity.this);
//                finish();
                showShortToast("登录成功");
            }
        });
    }

    BtSelectionDialog btSelectionDialog;
    @OnClick({R.id.need_help, R.id.code_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.need_help:
                 btSelectionDialog = new BtSelectionDialog(mContext, null, null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FeedBackActivity.startAction(LoginPwdActivity.this);
                        btSelectionDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FeedBackActivity.startAction(LoginPwdActivity.this);
                        btSelectionDialog.dismiss();

                    }
                });
                btSelectionDialog.show();
                break;
            case R.id.code_login:
                LoginCodeActivity.startAction(LoginPwdActivity.this);
                break;
        }
    }

}
