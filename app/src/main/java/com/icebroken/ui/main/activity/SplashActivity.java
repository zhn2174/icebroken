package com.icebroken.ui.main.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.icebroken.R;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.utils.CacheConstants;
import com.icebroken.utils.CacheServerResponse;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.android.tpush.XGPushManager;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * des:启动页
 */
public class SplashActivity extends BaseActivity {
    String tag = SplashActivity.class.getName();
    private AnimatorSet animatorSet;
    @Bind(R.id.ivLogo)
    ImageView ivLogo;
    @Override
    public int getLayoutId() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.splash_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setSwipeBackEnable(false);
//        SharedPreferences pref = this.getSharedPreferences("count1", Context.MODE_WORLD_READABLE);
//        int count = pref.getInt("count1", 0);
//        if (count == 0) {
//            Intent intent = new Intent(this, SplashActivity2.class);
//            startActivity(intent);
//            SharedPreferences.Editor editor = pref.edit();
//            //存入数据
//            editor.putInt("count1", ++count);
//            //提交修改
//            editor.commit();
//            finish();
//        } else {
//            RxPermissions rxPermissions = new RxPermissions(this);
//            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
//                    .subscribe(new Action1<Boolean>() {
//                        @Override
//                        public void call(Boolean aBoolean) {
//                            if (aBoolean) {
//                            } else {
//                                ShowAleryDialog2("提示", "应用需要存储、电话、相机、麦克风权限,请去设置界面打开，不然无法使用此应用\n打开之后按两次返回键可回到该应用哦", "取消", "设置",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
//                                                startActivity(intent);
//                                            }
//                                        }, new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                finish();
//                                            }
//                                        });
//                            }
//                        }
//                    });

//        }
//        if(CacheServerResponse.isCacheDataFailure(getApplicationContext(),"BootBean")){
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(layout_jump, alpha, scaleX, scaleY);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha, scaleX, scaleY);
//        layout_jump.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataLoadFinish = true;
//                goNext();
//            }
//        });
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator2);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.setDuration(2000);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    goNext();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

//        }
    }



    private void goNext() {
//        SharedPreferences pref =this.getSharedPreferences("uid", Context.MODE_WORLD_READABLE);
//        String uid = pref.getString("uid",null);
//        if (TextUtils.isEmpty(uid)){
            LoginActivity.startAction(this);
            finish();
//        }else {
//            AppApplication.uid = uid;
//            XGPushManager.setTag(SplashActivity.this,AppApplication.uid);
//            if (null!= CacheServerResponse.readObject(getApplicationContext(), CacheConstants.PWDBEAN)){
//                finish();
//            }else{
//                MainActivity.startAction(this);
//            }
//
//        }
    }
    /**
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


}
