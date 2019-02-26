package com.icebroken.ui.main.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.LnSignInBean;
import com.icebroken.ui.main.adapter.LnDocAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MySwipeRefreshLayout;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;


/**
 * 代办
 */
public class LnDocWaitRecFragment  extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipyrefreshlayout)
    MySwipeRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    LnDocAdapter adapter;
    List<LnSignInBean> dataList = new ArrayList<>();
    private int currentPage = 1;
    private int maxPage = 2;
    private String type = "1,1";

    @Override
    protected int getLayoutResource() {
        return R.layout.ln_fragment_doc;
    }

    @Override
    public void initPresenter() {

    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private long strat;
    private long end;
    private boolean isStart;

    @Override
    protected void initView() {

        LnSignInBean lnSignInBean1 = new LnSignInBean();
        lnSignInBean1.setTitle("关于第五次协管会议相关指示意见");
        lnSignInBean1.setDate("2018-08-21");
        lnSignInBean1.setBaomi_level("非密");
        lnSignInBean1.setDoc_no("33");
        lnSignInBean1.setJinji_chengdu("相当紧急");
        lnSignInBean1.setNigao_user("小王");


        LnSignInBean lnSignInBean2 = new LnSignInBean();
        lnSignInBean2.setTitle("走进街区调查");
        lnSignInBean2.setDate("2018-08-01");
        lnSignInBean2.setBaomi_level("加密");
        lnSignInBean2.setDoc_no("44");
        lnSignInBean2.setJinji_chengdu("不急");
        lnSignInBean2.setNigao_user("宁财神");

        LnSignInBean lnSignInBean3 = new LnSignInBean();
        lnSignInBean3.setTitle("厉害了我的国观影");
        lnSignInBean3.setDate("2018-08-21");
        lnSignInBean3.setBaomi_level("紧急");
        lnSignInBean3.setDoc_no("32");
        lnSignInBean3.setJinji_chengdu("相当紧急");
        lnSignInBean3.setNigao_user("秘林");

        dataList.add(lnSignInBean3);
        dataList.add(lnSignInBean1);
        dataList.add(lnSignInBean2);



        adapter = new LnDocAdapter(dataList);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ApplyInfoActivity.startAction(getActivity(), ((LnSignInBean) adapter.getData().get(position)).getRUN_ID(), ((LnSignInBean) adapter.getData().get(position)).getFLOW_NAME(), 1);
            }
        });
//        QueryData();
    }


    private void QueryData() {
        JSONObject map = new JSONObject();
        try {
            map.put("State", "TaskList");
            map.put("Page", "1");
            map.put("ShowNum", "99");
            map.put("Style", type.split(",")[0]);
            map.put("Type", type.split(",")[1]);
            map.put("Uid", AppApplication.uid);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).querySignInList(map.toString())
                .compose(RxHelper.<List<LnSignInBean>>handleResult()).subscribe(new RxSubscriber<List<LnSignInBean>>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                adapter.setNewData(new ArrayList<LnSignInBean>());

            }

            @Override
            public void _onNext(List<LnSignInBean> beans) {
                if (null != beans) {
                    dataList.addAll(beans);
                    if (currentPage == 1) {
                        adapter.setNewData(dataList);
                    } else {
                        adapter.addData(dataList);
                    }
                    adapter.loadMoreComplete();
                    mSwipyRefreshLayout.setRefreshing(false);
                } else {
                    adapter.setNewData(new ArrayList<LnSignInBean>());

                }

            }
        });

    }

    private void LoadData() {
        dataList = new ArrayList<>();
        QueryData();
        mSwipyRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        //下拉刷新直请求第一页数据
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            currentPage = 1;
//            LoadData();
        }
    }
}

