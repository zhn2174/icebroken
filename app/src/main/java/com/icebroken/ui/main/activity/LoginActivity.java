package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import com.icebroken.bean.accountExistBean;
import com.mocuz.common.baseapp.CacheUtils;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/7/4.
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.ed_phone)
    EditText edPhone;
    @Bind(R.id.tv_cue)
    TextView tvCue;
    @Bind(R.id.bt_login)
    TextView btLogin;
    @Bind(R.id.wx_login)
    LinearLayout wxLogin;
    @Bind(R.id.qq_login)
    LinearLayout qqLogin;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.main)
    RelativeLayout main;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tvCue.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.login_lay;
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
        String phone = CacheUtils.getString("phone", null);
        if (!TextUtils.isEmpty(phone)) {
            edPhone.setText(phone);
            btLogin.setEnabled(true);
        }
        edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCue.setVisibility(View.INVISIBLE);
                tvCue.removeCallbacks(runnable);
                if (s.length() == 11) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);
                    tvCue.postDelayed(runnable, 2000);
                }
            }
        });
    }

    private void gotoLogin() {
        if (checkInfo()) {
//            LoginPwdActivity.startAction(LoginActivity.this);
            accountExist();
        }
    }

    private boolean checkInfo() {
        if (!checkPhoneNum(edPhone.getText().toString())) {
            ToastUitl.showShort("请输入正确的手机号码");
            tvCue.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
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

    private void accountExist() {
        showProgressDialog("正在校验手机号码");
        JSONObject map = new JSONObject();
        try {
            map.put("type", 1);
            map.put("account", edPhone.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).accountExist(map.toString())
                .compose(RxHelper.<accountExistBean>handleResult()).subscribe(new RxSubscriber<accountExistBean>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                hideProgressDialog();
            }

            @Override
            public void _onNext(accountExistBean bean) {
                hideProgressDialog();
                AppApplication.phone = edPhone.getText().toString();
                AppApplication.isExist = bean.getExist();

                //存入数据
                CacheUtils.putString("phone", AppApplication.phone);

                if (bean.getExist()) {
                    LoginPwdActivity.startAction(LoginActivity.this);
                } else {
                    LoginCodeActivity.startAction(LoginActivity.this);
                }
            }
        });
    }

}
