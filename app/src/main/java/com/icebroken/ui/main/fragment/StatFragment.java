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
import com.icebroken.bean.TaskBean;
import com.icebroken.ui.main.adapter.StatAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MySwipeRefreshLayout;


/**
 * Created by wwy on 2017/3/13.
 */
public class StatFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.swipyrefreshlayout)
    MySwipeRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    StatAdapter adapter;
    List<AddressBean> dataList =new ArrayList<>();
    private int currentPage=1;
    private int maxPage=2;

    @Override
    protected int getLayoutResource() {
        return R.layout.stat_lay;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected void initView() {
        AddressBean bean =new AddressBean();
        bean.setTid(AppApplication.uid);
        bean.setName(AppApplication.user.getName());
        AddressBean bean1 =new AddressBean();
        bean1.setTid("2");
        bean1.setName("张伟");
        AddressBean bean2 =new AddressBean();
        bean2.setTid("2");
        bean2.setName("李娜");
        AddressBean bean3 =new AddressBean();
        bean3.setTid("2");
        bean3.setName(AppApplication.user.getName());
        dataList.add(bean);
        dataList.add(bean3);
        dataList.add(bean1);
        dataList.add(bean2);
        adapter =new StatAdapter(dataList);
        mSwipyRefreshLayout.setOnRefreshListener(this);
//        mSwipyRefreshLayout.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(this, mRecyclerView);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ApplyInfoActivity.startAction(getActivity(),((TaskBean)adapter.getData().get(position)).getRUN_ID(),((TaskBean)adapter.getData().get(position)).getFLOW_NAME(),1);
//            }
//        });
//        QueryData();
    }


    private void QueryData(){
        JSONObject map = new JSONObject();
        try {
            map.put("State", "TaskList");
            map.put("Page", "1");
            map.put("ShowNum", "99");
            map.put("Uid", AppApplication.uid);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryTaskList(map.toString())
                .compose(RxHelper.<List<TaskBean>>handleResult()).subscribe(new RxSubscriber<List<TaskBean>>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
//                adapter.setNewData(new ArrayList<TaskBean>());

            }

            @Override
            public void _onNext(List<TaskBean> beans) {
//                if(null!=beans){
//                    dataList.addAll(beans);
//                    if (currentPage == 1) {
//                        adapter.setNewData(dataList);
//                    } else {
//                        adapter.addData(dataList);
//                    }
//                    adapter.loadMoreComplete();
//                    mSwipyRefreshLayout.setRefreshing(false);
//                }else{
//                    adapter.setNewData(new ArrayList<TaskBean>());
//
//                }

            }
        });

    }

    private void LoadData(){
        dataList=new ArrayList<>();
        QueryData();
        mSwipyRefreshLayout.setRefreshing(false);
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

