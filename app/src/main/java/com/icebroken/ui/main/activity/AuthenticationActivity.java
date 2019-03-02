package com.icebroken.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.UserInfo;
import com.icebroken.utils.BaseUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ImageLoaderUtils;
import com.mocuz.common.commonutils.StringUtil;
import com.mocuz.common.compressorutils.FileUpload7NiuUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImgSelActivity;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Yorashe on 19-2-18.
 */

public class AuthenticationActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.info_text)
    TextView infoText;
    @Bind(R.id.school_ed)
    EditText schoolEd;
    @Bind(R.id.auth_name)
    NiceSpinner authName;
    @Bind(R.id.bt_login)
    TextView btLogin;
    @Bind(R.id.auth_img)
    ImageView authImg;
    @Bind(R.id.auth_delete)
    ImageView authDelete;
    @Bind(R.id.auth_img1)
    ImageView authImg1;
    @Bind(R.id.auth_delete1)
    ImageView authDelete1;
    private UserInfo userInfo;
    private RxPermissions rxPermissions;
    private boolean isImage1pick;
    private List<String> mSchools = new ArrayList<>();
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.authentication_lay;
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
                finish();
            }
        });
        userInfo = AppApplication.getUserInfo();
        String content = "IceBreaking是专为大学生以及毕业生设计的社交软件， 为了维护用户群体良好的使用环境 ，需要您进行真实身 份认证。 请任选以下方式： " + "<font color=\"#D045C8\">" + "校园一卡通、学生证、录取通知书、 毕业证。" + "</font>" +
                "如平台发现身份造假行为，将采取封号处理";
        infoText.setText(Html.fromHtml(content));
        rxPermissions = new RxPermissions(this);
        mSchools.add("1");
        mSchools.add("2");
        mSchools.add("3");
        mSchools.add("4");


        //设置验证方式
        final List<String> dataset = new LinkedList<>(Arrays.asList("校园一卡通", "学生证", "录取通知书", "毕业证"));
        authName.attachDataSource(dataset);

        authName.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                authName.setTextInternal(dataset.get(i));
                type = i + 1;
            }
        });

    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        if (TextUtils.isEmpty(schoolEd.getText())) {
            showShortToast("请填姓名");
            return;
        }
        if (TextUtils.isEmpty(photo1)) {
            showShortToast("请选择正面图片");
            return;
        }
        if (TextUtils.isEmpty(photo2)) {
            showShortToast("请选择反面图片");
            return;
        }
        showProgressDialog("正在认证资料");
        imgUrl1 = "";
        imgUrl2 = "";
        for (int i = 0; i < 2; i++) {
            final int finalI = i;
            Api.getDefault(HostType.MAIN).qiniuToken()
                    .compose(RxHelper.<String>handleResult()).subscribe(new RxSubscriber<String>(mContext, false) {
                @Override
                public void onCompleted() {

                }

                @Override
                public void _onError(String e) {
                    showShortToast(e);
                    hideProgressDialog();
                }

                @Override
                public void _onNext(String bean) {

                    if (finalI == 0) {
                        FileUpload7NiuUtil.uploadImageToQiniu(photo1, bean, new FileUpload7NiuUtil.OnUpLoadCompleteListener() {
                            @Override
                            public void onUpLoadComplete(String key) {
                                imgUrl1 = key;
                                if (StringUtil.isNotBlank(imgUrl1) && StringUtil.isNotBlank(imgUrl2)) {
                                    post();
                                }
                            }
                        });
                    } else if (finalI == 1) {
                        FileUpload7NiuUtil.uploadImageToQiniu(photo2, bean, new FileUpload7NiuUtil.OnUpLoadCompleteListener() {
                            @Override
                            public void onUpLoadComplete(String key) {
                                imgUrl2 = key;
                                if (StringUtil.isNotBlank(imgUrl1) && StringUtil.isNotBlank(imgUrl2)) {
                                    post();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void post() {
        JSONObject map = new JSONObject();
        try {
            map.put("realName", schoolEd.getText().toString());
            map.put("imgUrl1", imgUrl1);
            map.put("imgUrl2", imgUrl2);
            map.put("type", type);//认证类型 1-校园一卡通 2-学生证 3-录取通知书 4-毕业证
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).applyStudent(map.toString())
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
                showShortToast("认证成功");
                userInfo.setCertify(true);
                AppApplication.setUserInfo(userInfo);
                finish();
            }
        });
    }


    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, AuthenticationActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void choseImg() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            BaseUtil.skipImageSelector(AuthenticationActivity.this, "请选择图片", 1, REQUEST_CODE, false, false);
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

    @OnClick({R.id.auth_img, R.id.auth_delete, R.id.auth_img1, R.id.auth_delete1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.auth_img:
                isImage1pick = true;
                choseImg();
                break;
            case R.id.auth_delete:
                photo1 = "";
                authImg.setImageResource(0);
                authDelete.setVisibility(View.GONE);
                break;
            case R.id.auth_img1:
                isImage1pick = false;
                choseImg();
                break;
            case R.id.auth_delete1:
                photo2 = "";
                authImg1.setImageResource(0);
                authDelete1.setVisibility(View.GONE);
                break;
        }
    }

    private int REQUEST_CODE = 120;
    private String photo1 = "", photo2 = "";
    private String imgUrl1, imgUrl2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            final ArrayList<String> photos = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (null != photos && photos.size() > 0) {
//                BaseUtil.showProgress(mContext, "图片处理中...");
                if (isImage1pick) {
                    photo1 = photos.get(0);
                    ImageLoaderUtils.displayFitcenter(AuthenticationActivity.this, authImg, photos.get(0));
                    authDelete.setVisibility(View.VISIBLE);
                } else {
                    photo2 = photos.get(0);
                    ImageLoaderUtils.displayFitcenter(AuthenticationActivity.this, authImg1, photos.get(0));
                    authDelete1.setVisibility(View.VISIBLE);
                }

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                            file1 = BaseUtil.compressFile(AuthenticationActivity.this, photos.get(0));
//                        else
//                            file2 = BaseUtil.compressFile(AuthenticationActivity.this, photos.get(0));
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                BaseUtil.dismissProgress();
//                                if (!TextUtils.isEmpty(file)) {
////                                    ImageLoaderUtils.displayBlurImage(OrganizingDataActivity.this, iv_background, file);
//                                    String file1 = BaseUtil.endcodeBase64File(file);
//                                    uploadPhoto(file1);
//                                } else {
//                                    showShortToast("头像错误，请选择其他头像");
//                                }
//                            }
//
//
//                        });
//                    }
//                }).start();
            }
        }
    }
}
