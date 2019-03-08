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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.UserInfo;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.StringUtils;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ImageLoaderUtils;
import com.mocuz.common.commonutils.StringUtil;
import com.mocuz.common.commonutils.ToastUitl;
import com.mocuz.common.compressorutils.FileUpload7NiuUtil;
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
    @Bind(R.id.pic_img1)
    ImageView pic_img1;
    @Bind(R.id.pic_img2)
    ImageView pic_img2;
    @Bind(R.id.pic_img3)
    ImageView pic_img3;

    @Bind(R.id.ed_phone)
    EditText edPhone;
    @Bind(R.id.bt_login)
    Button btLogin;
    private RxPermissions rxPermissions;
    private int curpos;

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
                    uploadFiles();
                }
            }
        });
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        Bundle bundle = new Bundle();
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

    private void uploadFiles() {
        showProgressDialog("正在上传");

        final JSONObject jsonObject = new JSONObject(true);
        //第一张图
        if (StringUtil.isBlank(photo1)) {
            uploadloginProblem(jsonObject);
        } else {
            Api.getDefault(HostType.MAIN).qiniuToken()
                    .compose(RxHelper.<String>handleResult()).subscribe(new RxSubscriber<String>(mContext, false) {
                @Override
                public void onCompleted() {
                }

                @Override
                public void _onError(String e) {
                }

                @Override
                public void _onNext(String bean) {
                    FileUpload7NiuUtil.uploadImageToQiniu(photo1, bean, new FileUpload7NiuUtil.OnUpLoadCompleteListener() {
                        @Override
                        public void onUpLoadComplete(String key) {
                            jsonObject.put("imgUrl1", key);
                            //第二张图
                            if (StringUtil.isBlank(photo2)) {
                                uploadloginProblem(jsonObject);
                            } else {
                                Api.getDefault(HostType.MAIN).qiniuToken()
                                        .compose(RxHelper.<String>handleResult()).subscribe(new RxSubscriber<String>(mContext, false) {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void _onError(String e) {
                                    }

                                    @Override
                                    public void _onNext(String bean) {
                                        FileUpload7NiuUtil.uploadImageToQiniu(photo2, bean, new FileUpload7NiuUtil.OnUpLoadCompleteListener() {
                                            @Override
                                            public void onUpLoadComplete(String key) {
                                                jsonObject.put("imgUrl2", key);
                                                //第三张图
                                                if (StringUtil.isBlank(photo3)) {
                                                    uploadloginProblem(jsonObject);
                                                } else {
                                                    Api.getDefault(HostType.MAIN).qiniuToken()
                                                            .compose(RxHelper.<String>handleResult()).subscribe(new RxSubscriber<String>(mContext, false) {
                                                        @Override
                                                        public void onCompleted() {
                                                        }

                                                        @Override
                                                        public void _onError(String e) {
                                                        }

                                                        @Override
                                                        public void _onNext(String bean) {
                                                            FileUpload7NiuUtil.uploadImageToQiniu(photo3, bean, new FileUpload7NiuUtil.OnUpLoadCompleteListener() {
                                                                @Override
                                                                public void onUpLoadComplete(String key) {
                                                                    jsonObject.put("imgUrl3", key);
                                                                    uploadloginProblem(jsonObject);
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }

    private void uploadloginProblem(JSONObject jsonObject) {
        jsonObject.put("phone", edPhone.getText().toString());
        jsonObject.put("text", edContent.getText().toString());

        Api.getDefault(HostType.MAIN).uploadloginProblem()
                .compose(RxHelper.<String>handleResult()).subscribe(new RxSubscriber<String>(mContext, true) {
            @Override
            public void onCompleted() {
                hideProgressDialog();
            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                hideProgressDialog();
            }

            @Override
            public void _onNext(String bean) {

                showShortToast("反馈成功");
                finish();
            }
        });
    }


    private int REQUEST_CODE = 120;
    private String photo1, photo2, photo3;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> photos = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (null != photos && photos.size() > 0) {

                if (curpos == 1) {
                    photo1 = photos.get(0);
                    ImageLoaderUtils.display(this, pic_img1, photo1);
                    pic_img2.setVisibility(View.VISIBLE);
                } else if (curpos == 2) {
                    photo2 = photos.get(0);
                    ImageLoaderUtils.display(this, pic_img2, photo2);
                    pic_img3.setVisibility(View.VISIBLE);
                } else if (curpos == 3) {
                    photo3 = photos.get(0);
                    ImageLoaderUtils.display(this, pic_img3, photo3);
                }
            }
        }
    }


    @OnClick({R.id.pic_img1, R.id.pic_img2, R.id.pic_img3})
    public void choseHead(View view) {
        switch (view.getId()) {
            case R.id.pic_img1:
                curpos = 1;
                break;
            case R.id.pic_img2:
                curpos = 2;
                break;
            case R.id.pic_img3:
                curpos = 3;
                break;
        }
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
