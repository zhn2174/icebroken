package com.icebroken.ui.main.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.base.BaseActivity;
import rx.functions.Action1;


/**
 * Created by wwy on 2017/5/11.
 */
public class SplashActivity2 extends BaseActivity {
    private boolean isNext = false;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.page1)
    ImageView page1;
    @Bind(R.id.page2)
    ImageView page2;
    @Bind(R.id.page3)
    ImageView page3;
    @Bind(R.id.page4)
    ImageView page4;
    @Bind(R.id.yuan_layout)
    LinearLayout yuan_layout;

    @Override
    public int getLayoutId() {
        return R.layout.act_splash2;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        SetTranslanteBar();
        initData();
        setSwipeBackEnable(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        yuan_layout.setVisibility(View.VISIBLE);
                        page1.setImageResource(R.mipmap.page_now);
                        page2.setImageResource(R.mipmap.page);
//                        page3.setImageResource(R.mipmap.page);
//                        page4.setImageResource(R.mipmap.page);
                        break;
                    case 1:
                        yuan_layout.setVisibility(View.VISIBLE);
                        page1.setImageResource(R.mipmap.page);
                        page2.setImageResource(R.mipmap.page_now);
//                        page3.setImageResource(R.mipmap.page);
//                        page4.setImageResource(R.mipmap.page);
                        break;
                    case 2:
                        LoginActivity.startAction(SplashActivity2.this);
                        finish();
                        break;
//                    case 3:
//                        yuan_layout.setVisibility(View.VISIBLE);
//                        page1.setImageResource(R.mipmap.page);
//                        page2.setImageResource(R.mipmap.page);
//                        page3.setImageResource(R.mipmap.page);
//                        page4.setImageResource(R.mipmap.page_now);
//                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean)
                            ShowAleryDialog2("提示", "应用需要存储、电话、相机、麦克风权限,请去设置界面打开，不然无法使用此应用\n打开之后按两次返回键可回到该应用哦", "取消", "设置",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                            startActivity(intent);
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                        }
                });
    }



    private void initData() {
        // 将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.whats1, null);
        View view2 = mLi.inflate(R.layout.whats2, null);
        View view3 = mLi.inflate(R.layout.whats3, null);
        // 动态填充背景
        view1.setBackgroundResource(R.mipmap.whats1);
        view2.setBackgroundResource(R.mipmap.whats2);
        view3.setBackgroundResource(R.mipmap.whats2);

        // 每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        // 填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return "title";
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

        };
        viewPager.setAdapter(mPagerAdapter);
    }


    private void goNext(String error) {
    }

}
