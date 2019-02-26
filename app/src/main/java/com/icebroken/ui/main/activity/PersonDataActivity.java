package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.UserBean;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ImageLoaderUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import butterknife.Bind;

/**
 * Created by Yorashe on 18-6-27.
 */

public class PersonDataActivity extends BaseActivity {
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.imageHead)
    ImageView imageHead;
    @Bind(R.id.layoutHead)
    RelativeLayout layoutHead;
    @Bind(R.id.textName)
    TextView textName;
    @Bind(R.id.layoutName)
    RelativeLayout layoutName;
    @Bind(R.id.textAge)
    TextView textAge;
    @Bind(R.id.layoutAge)
    RelativeLayout layoutAge;
    @Bind(R.id.textSex)
    TextView textSex;
    @Bind(R.id.layoutSex)
    RelativeLayout layoutSex;
    @Bind(R.id.layoutSign)
    RelativeLayout layoutSign;
    @Bind(R.id.textPhone)
    TextView textPhone;
    @Bind(R.id.layoutPhone)
    RelativeLayout layoutPhone;
    @Bind(R.id.textEar)
    TextView textEar;
    @Bind(R.id.layoutEar)
    RelativeLayout layoutEar;

    @Override
    public int getLayoutId() {
        return R.layout.my_data_acty;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.btn_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        if (null!= AppApplication.user)
        initInfo();
        layoutSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, PersonDataActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
        intent.putExtras(bundle);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.fade_in,
//                com.mocuz.common.R.anim.fade_out);
    }
    private void post() {

        JSONObject map = new JSONObject();
        try {
            map.put("State", "MyCode");
            map.put("Uid", AppApplication.uid);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryCode(map.toString())
                .compose(RxHelper.<UserBean>handleResult()).subscribe(new RxSubscriber<UserBean>(this, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast("获取用户信息错误");
            }

            @Override
            public void _onNext(UserBean bean) {
                UserBean user=AppApplication.user;
                user.setCodeurl(bean.getCodeurl());
                AppApplication.user = user;
            }
        });
    }
    private void initInfo(){
        ImageLoaderUtils.displayCircle(this, imageHead, AppApplication.user.getHeadurl());
        textName.setText(AppApplication.user.getName());
        String sex="";
        if (TextUtils.equals(AppApplication.user.getSex(),"0")){
            sex="男";
        }else {
            sex="女";
        }
        textSex.setText(sex);
        int time= Integer.parseInt(AppApplication.user.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        textAge.setText(dateFormat.format(time));
        textPhone.setText(AppApplication.user.getTel());
        textEar.setText(AppApplication.user.getDepartment());
    }
}
