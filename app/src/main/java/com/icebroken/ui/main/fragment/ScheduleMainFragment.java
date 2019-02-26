package com.icebroken.ui.main.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.icebroken.R;
import com.icebroken.base.BaseFragment;
import com.icebroken.widget.CustomViewPager;
import com.mocuz.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Yorashe on 18-6-26.
 */

public class ScheduleMainFragment extends BaseFragment implements View.OnClickListener{
    @Bind(R.id.vp)
    CustomViewPager vp;
    @Bind(R.id.address_title)
    SlidingTabLayout tabLayout;
    @Bind(R.id.menu_red)
    FloatingActionMenu menuRed;
    @Bind(R.id.fab_task)
    FloatingActionButton fab_task;
    @Bind(R.id.fab_work)
    FloatingActionButton fab_work;
    @Bind(R.id.fab_meeting)
    FloatingActionButton fab_meet;
    private final String[] mTitles = {"日程","任务","会议"};
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ScheduleFragment calendarFragment;
    private TaskFragment taskFragment;
    private MeetingFragment meetFragment;
    @Override
    protected int getLayoutResource() {
        return R.layout.schedule_main_lay;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    protected void initView() {
        calendarFragment = new ScheduleFragment();
        taskFragment = new TaskFragment();
        meetFragment = new MeetingFragment();
        mFragments.add(calendarFragment);
        mFragments.add(taskFragment);
        mFragments.add(meetFragment);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
        vp.setScanScroll(false);
        tabLayout.setViewPager(vp);
        tabLayout.setTabSpaceEqual(true);
        tabLayout.setTextSelectColor(Color.BLUE);
        tabLayout.setTextUnselectColor(Color.BLACK);
        tabLayout.setIndicatorColor(Color.BLUE);
        fab_task.setVisibility(View.GONE);
    }

    @OnClick({R.id.fab_task, R.id.fab_work,R.id.fab_meeting})
    @Override
    public void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.fab_task:
                break;
            case R.id.fab_work:
                break;
            case R.id.fab_meeting:
            default:
                break;
        }
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
