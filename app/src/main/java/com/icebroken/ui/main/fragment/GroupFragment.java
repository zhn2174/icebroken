package com.icebroken.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.AddressBean;
import com.icebroken.bean.DepBean;
import com.icebroken.ui.main.adapter.GroupAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MySwipeRefreshLayout;

/**
 * Created by Administrator on 2018/7/15.
 */

public class GroupFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.swipyrefreshlayout)
    MySwipeRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    GroupAdapter adapter;
    List<DepBean> dataList =new ArrayList<>();

    private int currentPage=1;
    private int maxPage=2;

    @Override
    protected int getLayoutResource() {
        return R.layout.group_lay;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected void initView() {
        adapter =new GroupAdapter(dataList);
        mSwipyRefreshLayout.setOnRefreshListener(this);
//        mSwipyRefreshLayout.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                NotifyActivity.startAction((Activity) getActivity());
//                queryData(((DepBean)adapter.getData().get(position)).getPid());
//            }
//        });
        queryDep();
    }
    private void queryDep() {
        JSONObject map = new JSONObject();
        try {

            map.put("State", "DepartmentList");
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryDep(map.toString())
                .compose(RxHelper.<List<DepBean>>handleResult()).subscribe(new RxSubscriber<List<DepBean>>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(List<DepBean> bean) {
                if (null==bean || bean.isEmpty()){
                    showShortToast("抱歉没有查到部门相关数据");
                    return;
                }
                dataList=bean;
//        QueryData();
                if (currentPage == 1) {
                    adapter.setNewData(dataList);
                } else {
                    adapter.addData(dataList);
                }
                adapter.loadMoreComplete();
                mSwipyRefreshLayout.setRefreshing(false);
//
            }
        });
    }


    private void queryData(String depId) {
        JSONObject map = new JSONObject();
        try {

            map.put("State", "TelList");
            map.put("Class", depId);
            map.put("Uid", AppApplication.uid);
            map.put("Page", "1");
            map.put("ShowNum", "100");
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryAddressList(map.toString())
                .compose(RxHelper.<List<AddressBean>>handleResult()).subscribe(new RxSubscriber<List<AddressBean>>(getContext(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(List<AddressBean> beans) {
//                if (null!= beans)
//                dataList.addAll(beans);
            }
        });
    }
    private void LoadData(){
        dataList=new ArrayList<>();
        queryDep();
    }
    @Override
    public void onLoadMoreRequested() {
        if (maxPage>currentPage){
            currentPage++;
            LoadData();
            adapter.loadMoreEnd();
        }else {
            adapter.loadMoreComplete();
        }

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        //下拉刷新直请求第一页数据
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            currentPage=1;
            LoadData();
        }
    }
}