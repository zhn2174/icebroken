package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.SignUtil;
import com.icebroken.utils.StringUtils;
import com.icebroken.utils.WwyUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baseapp.CacheUtils;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by Administrator on 2018/7/1.
 */

public class EditPwdActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.ed_password)
    EditText edPassword;
    @Bind(R.id.ed_new_password)
    EditText edNewPassword;
    @Bind(R.id.ed_again_password)
    EditText edAgainPassword;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    @Override
    public int getLayoutId() {
        return R.layout.ed_pwd_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        WwyUtil.setButtonStyleEnable(btLogin);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.hideInput(EditPwdActivity.this);
                finish();
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()) {
                    post();
                }
            }
        });
        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(edPassword.getText().toString()) && !StringUtils.isEmpty(edNewPassword.getText().toString())) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);

                }
            }
        });
        edNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(edPassword.getText().toString()) && !StringUtils.isEmpty(edNewPassword.getText().toString())) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);

                }
            }
        });
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, EditPwdActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private boolean checkInfo() {
        if (StringUtils.isEmpty(edPassword.getText().toString())) {
            ToastUitl.showShort("原始密码不能为空");
            return false;
        }
        if (StringUtils.isEmpty(edNewPassword.getText().toString())) {
            ToastUitl.showShort("新密码不能为空");
            return false;
        }
        if (StringUtils.isEmpty(edAgainPassword.getText().toString())) {
            ToastUitl.showShort("再次确认密码不能为空");
            return false;
        }
        if (!TextUtils.equals(edAgainPassword.getText().toString(), edNewPassword.getText().toString())) {
            ToastUitl.showShort("新密码两次输出不相同，请重新输入");
            return false;
        }
        return true;
    }

    private void post() {
        showProgressDialog("正在设置密码");
        JSONObject map = new JSONObject();
        try {
            map.put("State", "UpPasswd");
            map.put("Uid", AppApplication.uid);
            map.put("OldPasswd", edPassword.getText().toString());
            map.put("NewPasswd", edNewPassword.getText().toString());
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).editPwd(map.toString())
                .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(this, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                hideProgressDialog();
                showShortToast(e);
            }

            @Override
            public void _onNext(Object bean) {
                showShortToast("修改密码成功");
                hideProgressDialog();
                AppApplication.uid = null;
                CacheUtils.putString("uid", AppApplication.uid);
                LoginActivity.startAction(EditPwdActivity.this);
                finish();

            }
        });
    }
}
