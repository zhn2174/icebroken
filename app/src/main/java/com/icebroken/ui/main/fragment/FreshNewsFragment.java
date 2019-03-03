package com.icebroken.ui.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.icebroken.R;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.TabEntity;
import com.icebroken.widget.CustomViewPager;
import com.mocuz.tablayout.CommonTabLayout;
import com.mocuz.tablayout.listener.CustomTabEntity;
import com.mocuz.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;

public class FreshNewsFragment extends BaseFragment {

    @Bind(R.id.fresh_news_tl)
    CommonTabLayout fresh_news_tl;
    @Bind(R.id.fresh_news_tl2)
    CommonTabLayout fresh_news_tl2;
    @Bind(R.id.fresh_news_vp)
    CustomViewPager fresh_news_vp;

    private String[] mTitles = new String[]{"新鲜事", "找朋友"};//tab 文字
    private String[] subTitle1 = new String[]{"同校", "好友", "同城", "漫游"};
    private String[] subTitle2;
    private ArrayList<Fragment> fragments;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fresh_news;
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
        fresh_news_vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        fresh_news_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (fresh_news_tl.getCurrentTab() != position) {
                    fresh_news_tl.setCurrentTab(position);
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
        fresh_news_tl.setTabData(mTabEntities);
        //点击监听
        fresh_news_tl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (fresh_news_vp.getCurrentItem() != position)
                    fresh_news_vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }
}
