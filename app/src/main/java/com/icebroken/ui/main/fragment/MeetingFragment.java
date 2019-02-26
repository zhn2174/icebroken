package com.icebroken.ui.main.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.MeetingBean;
import com.icebroken.ui.main.adapter.MeetingAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MySwipeRefreshLayout;


/**
 * Created by wwy on 2017/3/13.
 */
public class MeetingFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.swipyrefreshlayout)
    MySwipeRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    MeetingAdapter adapter;
    List<MeetingBean> dataList =new ArrayList<>();

    private int currentPage=1;
    private int maxPage=2;
    private int type=1;

    @Override
    protected int getLayoutResource() {
        return R.layout.meet_lay;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected void initView() {
        adapter =new MeetingAdapter(dataList);
        mSwipyRefreshLayout.setOnRefreshListener(this);
//        mSwipyRefreshLayout.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(this, mRecyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String id=((MeetingBean)adapter.getData().get(position)).getMid();
                if (TextUtils.isEmpty(id)){
                    showShortToast("会议id错误");
                }else{
                }
            }
        });
        List<String> dataset = new LinkedList<>(Arrays.asList("未开始", "已结束"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type=position+1;
                QueryData();
            }
        });
        niceSpinner.setArrowDrawable();
        LoadData();

    }

    private void QueryData(){
        JSONObject map = new JSONObject();
        try {
            map.put("State", "MeetingList");
            map.put("Uid", AppApplication.uid);
            map.put("Page", "1");
            map.put("ShowNum", "10");
            map.put("Time", new Date().getTime()/1000 +"");
            map.put("Type", type+"");
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryMeettingList(map.toString())
                .compose(RxHelper.<List<MeetingBean>>handleResult()).subscribe(new RxSubscriber<List<MeetingBean>>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(List<MeetingBean> beans) {
                if(null!=beans){
                    dataList.addAll(beans);
                    if (currentPage == 1) {
                        adapter.setNewData(dataList);
                    } else {
                        adapter.addData(dataList);
                    }
                    adapter.loadMoreComplete();
                    mSwipyRefreshLayout.setRefreshing(false);
                }

            }
        });

    }

    private void LoadData(){
        dataList=new ArrayList<>();
        QueryData();

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

