package com.icebroken.ui.main.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.AdapterView;

import com.mocuz.tablayout.SlidingTabLayout;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.TaskBean;
import com.icebroken.widget.CustomViewPager;

/**
 * Created by Yorashe on 18-6-26.
 */

public class StatMainFragment extends BaseFragment {
    private final String[] mTitles = {"早到榜", "勤奋榜", "迟到榜"};
    @Bind(R.id.address_title)
    SlidingTabLayout tabLayout;
    @Bind(R.id.vp)
    CustomViewPager vp;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private StatFragment calendarFragment;
    private StatFragment taskFragment;
    private StatFragment meetFragment;
    List<TaskBean> dataList =new ArrayList<>();
    @Bind(R.id.nice_spinner)
    NiceSpinner niceSpinner;

    @Override
    protected int getLayoutResource() {
        return R.layout.stat_main_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        calendarFragment = new StatFragment();
        taskFragment = new StatFragment();
        meetFragment = new StatFragment();
        mFragments.add(calendarFragment);
        mFragments.add(taskFragment);
        mFragments.add(meetFragment);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
//        vp.setScanScroll(false);
        tabLayout.setViewPager(vp);
        tabLayout.setTabSpaceEqual(true);
        tabLayout.setTextSelectColor(Color.BLUE);
        tabLayout.setTextUnselectColor(Color.BLACK);
        tabLayout.setIndicatorColor(Color.BLUE);
        List<String> dataset = new LinkedList<>(Arrays.asList("2018.06", "2018.07","2018.08","2018.09","2018.10","2018.11","2018.12"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
//                        type="1,1";
                        break;
                    case 1:
//                        type="1,2";
                        break;
                    case 2:
//                        type="1,3";
                        break;
                    case 3:
//                        type="2,1";
                        break;
                    case 4:
//                        type="2,2";
                        break;
                    case 5:
//                        type="2,3";
                        break;
                }
//                QueryData();
            }
        });
        niceSpinner.setArrowDrawable();
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
