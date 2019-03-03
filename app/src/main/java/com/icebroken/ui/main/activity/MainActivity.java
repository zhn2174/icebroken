package com.icebroken.ui.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mocuz.common.baseapp.AppManager;
import com.mocuz.common.commonutils.LogUtils;
import com.mocuz.common.commonutils.ToastUitl;
import com.mocuz.common.commonwidget.FloatingDragger;
import com.mocuz.tablayout.CommonTabLayout;
import com.mocuz.tablayout.listener.CustomTabEntity;
import com.mocuz.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;

import com.icebroken.R;
import com.icebroken.api.AppConstant;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.TabEntity;
import com.icebroken.ui.main.fragment.AddressMainFragment;
import com.icebroken.ui.main.fragment.MainFragment;
import com.icebroken.ui.main.fragment.MineFragment;
import com.icebroken.ui.main.fragment.ScheduleMainFragment;
import com.icebroken.ui.main.fragment.WorkFragment;

/**
 * des:主界面
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.ctab_layout)
    CommonTabLayout ctab_layout;

    private String[] mTitles;//tab 文字
    private int[] mIconUnselectIds = {
            R.mipmap.tab_notice, R.mipmap.tab_schedule, R.mipmap.tab_job, R.mipmap.tab_mail, R.mipmap.tab_me};//tab 默认背景
    private int[] mIconSelectIds = {
            R.mipmap.tab_notice_select, R.mipmap.tab_schedule_select, R.mipmap.tab_job_select, R.mipmap.tab_mail_select, R.mipmap.tab_me_select};//tab选中背景
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MainFragment indexFragment;//首页
    private ScheduleMainFragment dayFragment;//"兴趣湾",
    private WorkFragment workFragment;// "消息",
    private AddressMainFragment phnoeFragment;// "聚宝盆",
    private MineFragment mineFragment;// "更多"
    private static int tabLayoutHeight;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(new FloatingDragger(this, layoutResID).getView());
    }

    /**
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.fade_in,
                com.mocuz.common.R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        getSwipeBackLayout().setEnableGesture(false);
        //初始化菜单
        mTitles = new String[]{"首页", "兴趣湾", "消息", "聚宝盆", "更多"};
        initTab();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    private boolean isback;//连续2次返回键退出APP 2秒之内

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isback) {
                AppManager.getAppManager().AppExit(MainActivity.this, true);
                moveTaskToBack(false);
            } else {
                //监听全屏视频时返回键
                isback = true;
                ToastUitl.showShort("再次点击返回键退出");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isback = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayoutHeight = ctab_layout.getMeasuredHeight();
        //监听菜单显示或隐藏
    }


    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        ctab_layout.setTabData(mTabEntities);
        //点击监听
        ctab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            indexFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("indexFragment");
            dayFragment = (ScheduleMainFragment) getSupportFragmentManager().findFragmentByTag("dayFragment");
            workFragment = (WorkFragment) getSupportFragmentManager().findFragmentByTag("workFragment");
            phnoeFragment = (AddressMainFragment) getSupportFragmentManager().findFragmentByTag("phnoeFragment");
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            indexFragment = new MainFragment();
            dayFragment = new ScheduleMainFragment();
            workFragment = new WorkFragment();
            phnoeFragment = new AddressMainFragment();
            mineFragment = new MineFragment();

            transaction.add(R.id.fl_body, indexFragment, "indexFragment");
            transaction.add(R.id.fl_body, dayFragment, "dayFragment");
            transaction.add(R.id.fl_body, workFragment, "workFragment");
            transaction.add(R.id.fl_body, phnoeFragment, "phnoeFragment");
            transaction.add(R.id.fl_body, mineFragment, "mineFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        ctab_layout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private int currentPosition;

    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        currentPosition = position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //通知
            case 0:
                transaction.hide(dayFragment);
                transaction.hide(workFragment);
                transaction.hide(phnoeFragment);
                transaction.hide(mineFragment);
                transaction.show(indexFragment);
                transaction.commitAllowingStateLoss();
                break;
            //日程
            case 1:
                transaction.hide(indexFragment);
                transaction.hide(workFragment);
                transaction.hide(phnoeFragment);
                transaction.hide(mineFragment);
                transaction.show(dayFragment);
                transaction.commitAllowingStateLoss();
                break;
            //work
            case 2:
                transaction.hide(indexFragment);
                transaction.hide(dayFragment);
                transaction.hide(phnoeFragment);
                transaction.hide(mineFragment);
                transaction.show(workFragment);
                transaction.commitAllowingStateLoss();
                break;
            //通讯录
            case 3:
                transaction.hide(indexFragment);
                transaction.hide(dayFragment);
                transaction.hide(workFragment);
                transaction.hide(mineFragment);
                transaction.show(phnoeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 4:
                transaction.hide(indexFragment);
                transaction.hide(dayFragment);
                transaction.hide(workFragment);
                transaction.hide(phnoeFragment);
                transaction.show(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (ctab_layout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, ctab_layout.getCurrentTab());
        }
    }

    String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
