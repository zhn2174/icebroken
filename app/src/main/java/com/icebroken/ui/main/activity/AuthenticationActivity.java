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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    @Bind(R.id.auth_select)
    LinearLayout authSelect;
    @Bind(R.id.bt_login)
    TextView btLogin;
    @Bind(R.id.auth_img)
    ImageView authImg;
    @Bind(R.id.auth_delete)
    ImageView authDelete;
    @Bind(R.id.auth_select_pic)
    RelativeLayout authSelectPic;
    @Bind(R.id.auth_img1)
    ImageView authImg1;
    @Bind(R.id.auth_delete1)
    ImageView authDelete1;
    @Bind(R.id.auth_select_pic1)
    RelativeLayout authSelectPic1;
    private UserInfo userInfo;
    private RxPermissions rxPermissions;
    private boolean isImage1pick;
    private String imgUrl1;
    private String imgUrl2;
    private String[] mSchool_str = {"校园一卡通", "学生证", "录取通知书", "毕业证"};
    private List<String> mSchools = new ArrayList<>();

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
            }
        });

    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        post();

    }

    private void post() {
        if (TextUtils.isEmpty(schoolEd.getText())) {
            showShortToast("请填姓名");
            return;
        }
        if (TextUtils.isEmpty(imgUrl1)) {
            showShortToast("请选择正面图片");
            return;
        }
        if (TextUtils.isEmpty(imgUrl2)) {
            showShortToast("请选择反面图片");
            return;
        }
        showProgressDialog("正在认证资料");
        JSONObject map = new JSONObject();
        try {
            map.put("realName", schoolEd.getText().toString());
            map.put("imgUrl1", imgUrl1);
            map.put("imgUrl2", imgUrl2);
            map.put("type", 1);//认证类型 1-校园一卡通 2-学生证 3-录取通知书 4-毕业证
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
                finish();
                userInfo.setCertify(true);
                AppApplication.setUserInfo(userInfo);


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
                            BaseUtil.skipImageSelector(AuthenticationActivity.this, "请选择图片", 1, REQUEST_CODE, true, false);
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

    @OnClick({R.id.auth_select, R.id.auth_img, R.id.auth_delete, R.id.auth_select_pic, R.id.auth_img1, R.id.auth_delete1, R.id.auth_select_pic1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.auth_select:
                break;
            case R.id.auth_img:
                isImage1pick = true;
                choseImg();
                break;
            case R.id.auth_delete:
                break;
            case R.id.auth_select_pic:
                break;
            case R.id.auth_img1:
                isImage1pick = false;
                choseImg();
                break;
            case R.id.auth_delete1:
                break;
            case R.id.auth_select_pic1:
                ShowPop(mSchool_str, mSchools, "选择认证方式", authName);
                break;
        }
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
                        file = BaseUtil.compressFile(AuthenticationActivity.this, photo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseUtil.dismissProgress();
                                if (!TextUtils.isEmpty(file)) {
                                    ImageLoaderUtils.display(AuthenticationActivity.this, isImage1pick ? authImg : authImg1, file);
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
                if (isImage1pick) {
                    imgUrl1 = bean;
                } else {
                    imgUrl2 = bean;
                }
            }
        });
    }
}
