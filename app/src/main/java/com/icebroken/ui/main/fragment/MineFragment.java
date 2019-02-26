package com.icebroken.ui.main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.UserBean;
import com.icebroken.ui.main.activity.SettingActivity;
import com.icebroken.ui.x5.X5WebViewActivity;
import com.icebroken.utils.SignUtil;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ImageLoaderUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Yorashe on 18-6-27.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.image_user)
    ImageView imageUser;
    @Bind(R.id.layout_code)
    RelativeLayout layoutCode;
    @Bind(R.id.layout_work)
    RelativeLayout layoutWork;
    @Bind(R.id.layout_help)
    RelativeLayout layoutHelp;
    @Bind(R.id.layout_setting)
    RelativeLayout layoutSetting;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_jop)
    TextView userJop;
    @Bind(R.id.icon1)
    TextView icon1;
    @Bind(R.id.icon2)
    TextView icon2;
    @Bind(R.id.icon3)
    TextView icon3;
    @Bind(R.id.icon4)
    TextView icon4;

    @Override
    protected int getLayoutResource() {
        return R.layout.mine_lay;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        queryUserInfo();
    }

    @OnClick({R.id.layout_code, R.id.layout_work, R.id.layout_help, R.id.layout_setting})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_code:
                break;
            case R.id.layout_work:
                break;
            case R.id.layout_help:
                X5WebViewActivity.startAction(getActivity());
                break;
            case R.id.layout_setting:
                SettingActivity.startAction(getActivity());
                break;

        }
    }
    private void initInfo(){
        ImageLoaderUtils.displayCircle(getActivity(), imageUser, AppApplication.user.getHeadurl());
        userName.setText(AppApplication.user.getName());
        userJop.setText("职称："+AppApplication.user.getDepartment()+AppApplication.user.getRole());
    }

    private void queryUserInfo() {
        JSONObject map = new JSONObject();
        try {
            map.put("State", "MyInfo");
            map.put("Uid", AppApplication.uid);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryUserInfo(map.toString())
                .compose(RxHelper.<UserBean>handleResult()).subscribe(new RxSubscriber<UserBean>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast("获取用户信息错误");
            }

            @Override
            public void _onNext(UserBean bean) {
                AppApplication.user = bean;
                if (bean!=null){
                    initInfo();
                }

            }
        });
    }

}
