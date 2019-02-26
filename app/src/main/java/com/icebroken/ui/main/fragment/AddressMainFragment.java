package com.icebroken.ui.main.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mocuz.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.base.BaseFragment;

/**
 * Created by Yorashe on 18-6-27.
 */

public class AddressMainFragment extends BaseFragment {

    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.address_title)
    SlidingTabLayout tabLayout;
    private final String[] mTitles = {"全部", "选择分组"};
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private AddressFragment allAddressFragment;
    private GroupFragment addressFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.address_main_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        allAddressFragment = new AddressFragment();
        addressFragment = new GroupFragment();
        mFragments.add(allAddressFragment);
        mFragments.add(addressFragment);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
        tabLayout.setViewPager(vp);
        tabLayout.setTabSpaceEqual(true);
        tabLayout.setTextSelectColor(Color.parseColor("#3676fc"));
        tabLayout.setTextUnselectColor(Color.BLACK);
        tabLayout.setIndicatorColor(Color.parseColor("#3676fc"));
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }




}
