package com.icebroken.ui.main.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.NotiBean;
import com.icebroken.ui.main.adapter.NotifyAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.DividerItemDecoration;
import com.icebroken.widget.MySwipeRefreshLayout;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
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
public class MainFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.search_view)
    EditText search_view;
    @Bind(R.id.swipyrefreshlayout)
    MySwipeRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    NotifyAdapter adapter;
    List<NotiBean> dataList =new ArrayList<>();
    List<NotiBean> searchList =new ArrayList<>();

    private int currentPage=1;
    private int maxPage=2;

    @Override
    protected int getLayoutResource() {
        return R.layout.index_lay;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected void initView() {
//        dataList.add(new NotiBean());
//        dataList.add(new NotiBean());
//        dataList.add(new NotiBean());
//        dataList.add(new NotiBean());
//        dataList.add(new NotiBean());
//        dataList.add(new NotiBean());
        adapter =new NotifyAdapter(dataList);
        mSwipyRefreshLayout.setOnRefreshListener(this);
//        mSwipyRefreshLayout.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
//        adapter.setOnLoadMoreListener(this, mRecyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        LoadData();

        search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataList.size()==0){
                    return;
                }else if (null!=s && s.toString().length()>0){
                    searchList.clear();
                    for (NotiBean notiBean :dataList){
                        if (!TextUtils.isEmpty(notiBean.getTitle()) &&notiBean.getTitle().contains(s.toString()) ){
                            searchList.add(notiBean);
                        }
                    }
                    adapter.setNewData(searchList);
                }else{
                    if (dataList!=null && dataList.size()>0)
                    adapter.setNewData(dataList);
                }
            }
        });
    }


    private void QueryData(){
        JSONObject map = new JSONObject();
        try {
            map.put("State", "NoticeList");
            map.put("Uid", AppApplication.uid);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryNoti(map.toString())
                .compose(RxHelper.<List<NotiBean>>handleResult()).subscribe(new RxSubscriber<List<NotiBean>>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(List<NotiBean> beans) {
                if (null!= beans)
//                dataList.addAll(beans);
                adapter.addData(beans);
//
            }
        });

    }
    private void LoadData(){
        dataList=new ArrayList<>();
        QueryData();
        if (currentPage == 1) {
            adapter.setNewData(dataList);
        } else {
            adapter.addData(dataList);
        }
        adapter.loadMoreComplete();
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

