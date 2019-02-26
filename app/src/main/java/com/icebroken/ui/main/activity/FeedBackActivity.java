package com.icebroken.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.base.BaseActivity;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.StringUtils;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.commonutils.ImageLoaderUtils;
import com.mocuz.common.commonutils.ToastUitl;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/7/1.
 */

public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.ed_content)
    EditText edContent;
    @Bind(R.id.pic_img)
    ImageView picImg;
    @Bind(R.id.ed_phone)
    EditText edPhone;
    @Bind(R.id.bt_login)
    Button btLogin;
    private RxPermissions rxPermissions;

    @Override
    public int getLayoutId() {
        return R.layout.feed_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.hideInput(FeedBackActivity.this);
                finish();
            }
        });
        rxPermissions = new RxPermissions(this);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()) {
                    showShortToast("反馈成功");
                    finish();
                }
            }
        });
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private boolean checkInfo() {
        if (StringUtils.isEmpty(edContent.getText().toString())) {
            ToastUitl.showShort("请先填写意见反馈");
            return false;
        }
        if (StringUtils.isEmpty(edPhone.getText().toString())) {
            ToastUitl.showShort("请先填写手机号码");
            return false;
        }

        return true;
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
                        file = BaseUtil.compressFile(mContext, photo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseUtil.dismissProgress();
                                if (!TextUtils.isEmpty(file)) {
                                    ImageLoaderUtils.displayCircle(mContext, picImg, file);
//                                    ImageLoaderUtils.displayBlurImage(mContext, iv_background, file);
                                    String file1 = BaseUtil.endcodeBase64File(file);
//                                    uploadPhoto(file1);
                                } else {
                                    showShortToast("头像错误，请选择其他头像");
                                }
                            }
                        });
                    }
                }).start();
            }
        }
    }


    @OnClick(R.id.pic_img)
    public void choseHead() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            BaseUtil.skipImageSelector(FeedBackActivity.this, "请选择图片", 1, REQUEST_CODE, true, false);
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
}
