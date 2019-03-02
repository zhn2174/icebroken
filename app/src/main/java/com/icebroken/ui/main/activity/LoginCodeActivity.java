package com.icebroken.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.tuo.customview.VerificationCodeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class LoginCodeActivity extends BaseActivity {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.main)
    RelativeLayout main;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.tv_cue)
    TextView tvCue;
    @Bind(R.id.icv)
    VerificationCodeView icv;
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
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.hideInput(mContext);
                finish();
            }
        });
        tvCue.setHint(String.format("发送验证码至%s", AppApplication.phone));
        tvCue.setHintTextColor(Color.parseColor("#CBCBCB"));

        icv.setInputCompleteListener(new VerificationCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                if (icv.getInputContent().length() == 4) {
                    Login();
                }
            }

            @Override
            public void deleteContent() {

            }
        });
    }

    private void gotoLogin() {
        startCountDownTimer();
        getcode();
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginCodeActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
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

    @OnClick({R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                gotoLogin();
                break;
        }
    }

    private void getcode() {
        JSONObject map = new JSONObject();
        try {
            map.put("countryCode", "86");
            map.put("mobilephone", AppApplication.phone);
            map.put("type", AppApplication.isExist ? 2 : 1);
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
        if (AppApplication.isExist) {
            showProgressDialog("正在登录");
            JSONObject map = new JSONObject();
            try {
                map.put("type", 2);
                map.put("phone", AppApplication.phone);
                map.put("password", icv.getInputContent());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Api.getDefault(HostType.MAIN).login(map.toString())
                    .compose(RxHelper.<accountExistBean>handleResult()).subscribe(new RxSubscriber<accountExistBean>(mContext, false) {
                @Override
                public void onCompleted() {
                    icv.clearInputContent();
                    hideProgressDialog();
                    showShortToast("登录成功");
                    tvCue.setText("");
                }

                @Override
                public void _onError(String e) {
                    showShortToast(e);
                    hideProgressDialog();
                    icv.clearInputContent();
                    tvCue.setText("短信验证码不正确");
                }

                @Override
                public void _onNext(accountExistBean bean) {
                    hideProgressDialog();
                    AppApplication.token = bean.getToken();
                    UserInfo userInfo = new UserInfo();
                    userInfo.setComplete(bean.getComplete());
                    userInfo.setCompleteSchool(bean.getCompleteSchool());
                    AppApplication.setUserInfo(userInfo);
                    CacheUtils.putToken(AppApplication.token);
                    if (!bean.getComplete()) {
                        OrganizingDataActivity.startAction(LoginCodeActivity.this);
                    } else if (!bean.getCompleteSchool()) {
                        OrganizingData2Activity.startAction(LoginCodeActivity.this);
                    } else {
//                        MainActivity.startAction(LoginCodeActivity.this);
                        //fixme:登陆主界面
                    }

                }
            });
        } else {
            showProgressDialog("正在校验验证码");
            JSONObject map = new JSONObject();
            AppApplication.code = icv.getInputContent();
            try {
                map.put("mobilephone", AppApplication.phone);
                map.put("type", 1);
                map.put("code", icv.getInputContent());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Api.getDefault(HostType.MAIN).check(map.toString())
                    .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(mContext, false) {
                @Override
                public void onCompleted() {
                    icv.clearInputContent();
                    hideProgressDialog();
                    showShortToast("验证成功");
                    tvCue.setText("");

                }

                @Override
                public void _onError(String e) {
                    showShortToast(e);
                    hideProgressDialog();
                    icv.clearInputContent();
                    tvCue.setText("短信验证码不正确");
                }

                @Override
                public void _onNext(Object bean) {
                    SetPwdActivity.startAction(LoginCodeActivity.this);
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
                btLogin.setText("重新获取　" + (millisUntilFinished / 1000) + "s");
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
