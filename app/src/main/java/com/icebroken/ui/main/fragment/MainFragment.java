package com.icebroken.ui.main.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.NotiBean;
import com.icebroken.bean.TabEntity;
import com.icebroken.ui.main.adapter.NotifyAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.CustomViewPager;
import com.icebroken.widget.DividerItemDecoration;
import com.icebroken.widget.MySwipeRefreshLayout;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.tablayout.CommonTabLayout;
import com.mocuz.tablayout.SegmentTabLayout;
import com.mocuz.tablayout.listener.CustomTabEntity;
import com.mocuz.tablayout.listener.OnTabSelectListener;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by wwy on 2017/3/13.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.main_head_iv)
    ImageView main_head_iv;
    @Bind(R.id.main_search_iv)
    ImageView main_search_iv;
    @Bind(R.id.main_fragment_ctl)
    CommonTabLayout main_fragment_ctl;
    @Bind(R.id.main_vp)
    CustomViewPager main_vp;

    private String[] mTitles = new String[]{"新鲜事", "找朋友"};//tab 文字
    private ArrayList<Fragment> fragments;


    @Override
    protected int getLayoutResource() {
        return R.layout.index_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new FreshNewsFragment());
            fragments.add(new FindFriendsFragment());
        }
        main_vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        main_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (main_fragment_ctl.getCurrentTab() != position) {
                    main_fragment_ctl.setCurrentTab(position);
                }
            }
        });
        initTab();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        main_fragment_ctl.setTabData(mTabEntities);
        //点击监听
        main_fragment_ctl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                main_vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

}

