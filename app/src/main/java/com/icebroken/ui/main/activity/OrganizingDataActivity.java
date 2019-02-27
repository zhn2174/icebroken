package com.icebroken.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.mocuz.common.commonutils.ImageLoaderUtils;
import com.mocuz.common.commonutils.TimeUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImgSelActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
import rx.functions.Action1;

/**
 * Created by Yorashe on 19-2-18.
 */

public class OrganizingDataActivity extends BaseActivity {
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.iv_user)
    ImageView ivUser;
    @Bind(R.id.iv_choose)
    ImageView ivChoose;
    @Bind(R.id.birthday_select)
    RelativeLayout birthdaySelect;
    @Bind(R.id.home_select)
    RelativeLayout homeSelect;
    @Bind(R.id.sex_rg)
    RadioGroup sex_rg;
    @Bind(R.id.love_state_rg)
    RadioGroup love_state_rg;

    @Bind(R.id.single)
    RadioButton single;
    @Bind(R.id.love)
    RadioButton love;
    @Bind(R.id.secrecy)
    RadioButton secrecy;
    @Bind(R.id.bt_login)
    TextView btLogin;
    @Bind(R.id.birthday_text)
    TextView birthdayText;
    @Bind(R.id.home_text)
    TextView homeText;
    @Bind(R.id.name)
    EditText name;
    private Thread thread;
    private RxPermissions rxPermissions;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private TimePickerView pvCustomLunar;
    private static boolean isLoaded = false;
    private UserInfo userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.organizing_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
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

        rxPermissions = new RxPermissions(this);
        initLunarPicker();
        userInfo = AppApplication.getUserInfo();
        sex_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.man) {
                    userInfo.setSex(1);
                } else {
                    userInfo.setSex(0);
                }
            }
        });

        love_state_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.single) {
                    userInfo.setEmotionalState(0);
                } else if (checkedId == R.id.love) {
                    userInfo.setEmotionalState(1);
                } else if (checkedId == R.id.secrecy) {
                    userInfo.setEmotionalState(2);
                }
            }
        });

    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, OrganizingDataActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        BaseUtil.hideInput(mContext);
        if (TextUtils.isEmpty(name.getText())) {
            showShortToast("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(userInfo.getHeadUrl())) {
            showShortToast("请选择头像");
            return;
        }
        if (TextUtils.isEmpty(birthdayText.getText())) {
            showShortToast("请选择生日");
            return;
        }
        if (TextUtils.isEmpty(homeText.getText())) {
            showShortToast("请选择家乡");
            return;
        }
        post();
    }


    @OnClick({R.id.birthday_select, R.id.home_select})
    public void onViewClicked(View view) {
        BaseUtil.hideInput(mContext);
        switch (view.getId()) {
            case R.id.birthday_select:
                pvCustomLunar.show();
                break;
            case R.id.home_select:
                startActivityForResult(SelectAddressActivity.class, 1);
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(TimeUtil.getYear(System.currentTimeMillis()), TimeUtil.getMonth(System.currentTimeMillis()), TimeUtil.getDay(System.currentTimeMillis()));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                Toast.makeText(mContext, getTime(date), Toast.LENGTH_SHORT).show();
                birthdayText.setText(getTime(date));
                userInfo.setBirthday((int) (date.getTime() / 1000));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
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

    private int REQUEST_CODE = 120;
    private String photo = "";
    private String file;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> photos = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (null != photos && photos.size() > 0) {
                BaseUtil.showProgress(mContext, "图片处理中...");
                photo = photos.get(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        file = BaseUtil.compressFile(OrganizingDataActivity.this, photo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseUtil.dismissProgress();
                                if (!TextUtils.isEmpty(file)) {
                                    ImageLoaderUtils.displayCircle(OrganizingDataActivity.this, ivUser, file);
//                                    ImageLoaderUtils.displayBlurImage(OrganizingDataActivity.this, iv_background, file);
                                    String file1 = BaseUtil.endcodeBase64File(file);
                                    uploadPhoto(file1);
                                } else {
                                    showShortToast("头像错误，请选择其他头像");
                                }
                            }


                        });
                    }
                }).start();
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("codeId")) {
                userInfo.setHometownCode(data.getIntExtra("codeId", 0));
                homeText.setText(data.getStringExtra("address"));
            }
        }
    }

    private void uploadPhoto(String file1) {
        JSONObject map = new JSONObject();
        try {
            map.put("img", file1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).upload(map.toString())
                .compose(RxHelper.<String>handleResult()).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(String bean) {
                userInfo.setHeadUrl(bean);
            }
        });
    }

    @OnClick(R.id.iv_user)
    public void choseHead() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            BaseUtil.skipImageSelector(OrganizingDataActivity.this, "请选择图片", 1, REQUEST_CODE, true, false);
                        } else {
                            AskForPermission("当前应用读写手机存储或者相机权限被关闭,请去设置界面打开\n打开之后按两次返回键可回到该应用哦");
                        }
                    }
                });
    }

    private void AskForPermission(String message) {
        ShowAleryDialog("提示", message, "取消", "设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
    }

    //提交第一部分
    private void post() {
        com.alibaba.fastjson.JSONObject params = new com.alibaba.fastjson.JSONObject(true);
        params.put("headUrl", userInfo.getHeadUrl());
        params.put("nickname", userInfo.getNickname());
        params.put("birthday", userInfo.getBirthday());
        params.put("hometownCode", userInfo.getHometownCode());
        params.put("sex", userInfo.getSex());
        params.put("emotionalState", userInfo.getEmotionalState());

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
                hideProgressDialog();
                userInfo.setNickname(name.getText().toString());
                AppApplication.setUserInfo(userInfo);

                OrganizingData2Activity.startAction(OrganizingDataActivity.this);
                finish();
            }
        });
    }


}
