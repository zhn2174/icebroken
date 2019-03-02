package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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
import com.mocuz.common.baseapp.CacheUtils;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ToastUitl;
import com.wevey.selector.BtSelectionDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class LoginPwdActivity extends BaseActivity {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.ed_password)
    EditText ed_password;
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
        ed_password.addTextChangedListener(new TextWatcher() {
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
        if (TextUtils.isEmpty(ed_password.getText().toString())) {
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

    private void Login() {
        showProgressDialog("正在登录");
        JSONObject map = new JSONObject();
        try {
            map.put("type", 1);
            map.put("phone", AppApplication.phone);
            map.put("password", ed_password.getText().toString());
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
                UserInfo userInfo = new UserInfo();
                userInfo.setComplete(bean.getComplete());
                userInfo.setCompleteSchool(bean.getCompleteSchool());
                AppApplication.setUserInfo(userInfo);
                AppApplication.token = bean.getToken();
                CacheUtils.putToken(AppApplication.token);
                showShortToast("登录成功");
                if (!bean.getComplete()) {
                    OrganizingDataActivity.startAction(LoginPwdActivity.this);
                } else if (!bean.getCompleteSchool()) {
                    OrganizingData2Activity.startAction(LoginPwdActivity.this);
                } else {
                    //fixme:登陆主界面
//                    MainActivity.startAction(LoginPwdActivity.this);
                }
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
                        startActivity(new Intent(LoginPwdActivity.this, LoginCodeActivity.class)
                                .putExtra("isForgetPassword", true));
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
