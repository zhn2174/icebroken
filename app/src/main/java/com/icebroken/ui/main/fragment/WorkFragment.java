package com.icebroken.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.WorkMainBean;
import com.icebroken.ui.main.adapter.WorkAdapter;

/**
 * Created by Yorashe on 18-6-27.
 */

public class WorkFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener{
    @Bind(R.id.rec_find)
    RecyclerView recFind;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipyrefreshlayout;


    private WorkAdapter mFindAdapter;
    private WorkMainBean mFindBean;
    private int[] dataListid={R.mipmap.icon1,R.mipmap.icon2,R.mipmap.icon3,R.mipmap.icon4,R.mipmap.icon5,R.mipmap.icon6};
    private String[] dataListTitle={"考勤打卡","签到","请假","外出","出差","加班"};
    private int[] govDListid={R.mipmap.icon7,R.mipmap.icon8,R.mipmap.icon9,R.mipmap.icon10,R.mipmap.icon12,R.mipmap.icon13,R.mipmap.icon14,R.mipmap.icon15};
    private String[]  govDListTitle={"会议申请","车辆申请","印章申请","食堂申请","领导预约","卫生安保","申领物品","收发文件"};
    private List<WorkMainBean> mFindList;
    private List<WorkMainBean.ItemBean> dataList = new ArrayList<>();
    private List<WorkMainBean.ItemBean> govDataList = new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return R.layout.work_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        recFind.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recFind.setHasFixedSize(true);
        recFind.setLayoutManager(mLayoutManager);
        mFindList = new ArrayList<>();
        mFindAdapter = new WorkAdapter(mFindList);
        recFind.setAdapter(mFindAdapter);
        swipyrefreshlayout.setOnRefreshListener(this);
        getData();
//        mPresenter.loadFindData("");
    }
    private void getData(){
        for (int i=0;i<dataListid.length;i++){
            WorkMainBean.ItemBean itemBean =new WorkMainBean.ItemBean();
            itemBean.setTitle(dataListTitle[i]);
            itemBean.setResid(dataListid[i]);
            dataList.add(itemBean);
        }
        for (int i=0;i<govDListid.length;i++){
            WorkMainBean.ItemBean itemBean =new WorkMainBean.ItemBean();
            itemBean.setTitle(govDListTitle[i]);
            itemBean.setResid(govDListid[i]);
            govDataList.add(itemBean);
        }
        mFindBean =new WorkMainBean();
        mFindBean.setItems(dataList);
        mFindBean.setGvItems(govDataList);
        mFindList.add(mFindBean);
        mFindAdapter.notifyDataSetChanged();
    }
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
//        mPresenter.loadFindData("");
        swipyrefreshlayout.setRefreshing(false);
    }
}
