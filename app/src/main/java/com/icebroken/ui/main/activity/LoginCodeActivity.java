package com.icebroken.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.mocuz.common.baseapp.CacheUtils;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/7/4.
 */

public class LoginCodeActivity extends BaseActivity {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
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
    @Bind(R.id.ed_code1)
    EditText edCode1;
    @Bind(R.id.ed_code2)
    EditText edCode2;
    @Bind(R.id.ed_code3)
    EditText edCode3;
    @Bind(R.id.ed_code4)
    EditText edCode4;
    @Bind(R.id.tv_cue)
    TextView tvCue;
    private MineCountDownTimer mCountDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.login_code_lay;
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
        edCode1.setCursorVisible(false);
        edCode2.setCursorVisible(false);
        edCode3.setCursorVisible(false);
        edCode4.setCursorVisible(false);
        edCode2.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (edCode2.getText()!=null){
                        edCode1.setText("");
                    }
                }
                return false;
            }
        });
        edCode3.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (edCode3.getText()!=null){
                        edCode2.setText("");
                    }
                }
                return false;
            }
        });
        edCode4.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (edCode4.getText()!=null){
                        edCode3.setText("");
                    }
                }
                return false;
            }
        });
        edCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() ==1) {
                    edCode2.requestFocus();
                } else {

                }
            }
        });

        edCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() ==1) {
                    edCode3.requestFocus();
                } else {
//                    edCode1.requestFocus();

                }
            }
        });

        edCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() ==1) {
                    edCode4.requestFocus();
                } else {
//                    edCode2.requestFocus();
                }
            }
        });
        edCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() ==1) {
                    Login();
                } else {
//                    edCode3.requestFocus();

                }
            }
        });
    }

    private void gotoLogin() {
        startCountDownTimer();
        getcode();
    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(edCode4.getText())){
            return false;
        }
        return true;
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginCodeActivity.class);
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

    private void getcode() {
        JSONObject map = new JSONObject();
        String code =edCode1.getText().toString()+edCode2.getText().toString()+edCode3.getText().toString()+edCode4.getText().toString();
        try {
            map.put("countryCode", "86");
            map.put("mobilephone", AppApplication.phone);
            map.put("type",AppApplication.isExist?2:1);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).getcode(map.toString())
                .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(Object bean) {
                showShortToast("发送成功");


            }
        });
    }

    private void Login() {
        if (AppApplication.isExist){
            showProgressDialog("正在登录");
            JSONObject map = new JSONObject();
            String code =edCode1.getText().toString()+edCode2.getText().toString()+edCode3.getText().toString()+edCode4.getText().toString();
            try {
                map.put("type", 2);
                map.put("phone", AppApplication.phone);
                map.put("password",code);
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
                    showShortToast(e);
                    hideProgressDialog();
                }

                @Override
                public void _onNext(accountExistBean bean) {
                    hideProgressDialog();
                    AppApplication.token = bean.getToken();
                    UserInfo userInfo =new UserInfo();
                    userInfo.setComplete(bean.getComplete());
                    userInfo.setCompleteSchool(bean.getCompleteSchool());
                    AppApplication.setUserInfo(userInfo);
                    CacheUtils.putToken(AppApplication.token);
                    showShortToast("登录成功");
                    OrganizingDataActivity.startAction(LoginCodeActivity.this);
//                    finish();


                }
            });
        }else{
            showProgressDialog("正在校验验证码");
            JSONObject map = new JSONObject();
            String code =edCode1.getText().toString()+edCode2.getText().toString()+edCode3.getText().toString()+edCode4.getText().toString();
            AppApplication.code=code;
            try {
                map.put("mobilephone", AppApplication.phone);
                map.put("type", 1);
                map.put("code", code);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Api.getDefault(HostType.MAIN).check(map.toString())
                    .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(mContext, false) {
                @Override
                public void onCompleted() {

                }

                @Override
                public void _onError(String e) {
                    showShortToast(e);
                    hideProgressDialog();
                    edCode1.setText("");
                    edCode2.setText("");
                    edCode3.setText("");
                    edCode4.setText("");
                    edCode1.requestFocus();

                }

                @Override
                public void _onNext(Object bean) {
                    hideProgressDialog();
                    showShortToast("验证成功");
                    SetPwdActivity.startAction(LoginCodeActivity.this);
//                    finish();


                }
            });


        }

    }


    /**
     * 启动倒计时
     */
    void startCountDownTimer() {
        if (null == mCountDownTimer)
            mCountDownTimer = new MineCountDownTimer(60000, 1000, btLogin);
        mCountDownTimer.start();
    }

    /**
     * 倒计时类
     */
    class MineCountDownTimer extends CountDownTimer {
        private View view;

        public MineCountDownTimer(long millisInFuture, long countDownInterval, View view) {
            super(millisInFuture, countDownInterval);
            this.view = view;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onTick(long millisUntilFinished) {
            if (null != view)
                view.setEnabled(false);
            if (null != btLogin)
//                mGetLoginCode.setText( String.format(getResources().getString(R.string.recapture), (millisUntilFinished / 1000)+""));
                btLogin.setText("重新获取　"+(millisUntilFinished / 1000) + "s");
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            if (null != view)
                view.setEnabled(true);
            if (null != btLogin)
                btLogin.setText("获取验证码");
        }
    }

}
