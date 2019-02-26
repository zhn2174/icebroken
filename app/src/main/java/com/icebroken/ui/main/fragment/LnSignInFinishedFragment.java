package com.icebroken.ui.main.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.LnSignInBean;
import com.icebroken.ui.main.adapter.LnSignInAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.widget.MySwipeRefreshLayout;


/**
 * 已签收
 */
public class LnSignInFinishedFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.start_time)
    TextView start_time;
    @Bind(R.id.end_time)
    TextView end_time;

    @Bind(R.id.swipyrefreshlayout)
    MySwipeRefreshLayout mSwipyRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    LnSignInAdapter adapter;
    List<LnSignInBean> dataList = new ArrayList<>();
    private int currentPage = 1;
    private int maxPage = 2;
    private String type = "1,1";

    @Override
    protected int getLayoutResource() {
        return R.layout.ln_fragment_signin_finished;
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
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        if (isStart) {
            strat = calendar.getTime().getTime();
            start_time.setText(dateFormat.format(calendar.getTime()));
        } else {
            end = calendar.getTime().getTime();
            end_time.setText(dateFormat.format(calendar.getTime()));
        }
        if (strat != 0 && end != 0) {
            if (end - strat < 0) {
                showShortToast("结束时间不能小于开始时间!");
            }
        }
    }


    @Override
    protected void initView() {

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        start_time.setText(dateFormat.format(new Date().getTime()));
        end_time.setText(dateFormat.format(new Date().getTime()));
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                DatePickerDialog.newInstance(LnSignInFinishedFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = false;
                DatePickerDialog.newInstance(LnSignInFinishedFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");
            }
        });


        LnSignInBean lnSignInBean1 = new LnSignInBean();
        lnSignInBean1.setTitle("APP页面设计");
        lnSignInBean1.setDate("2018-08-21");
        lnSignInBean1.setBaomi_level("非密");
        lnSignInBean1.setDoc_no("33");
        lnSignInBean1.setFrom("句容xx单位");
        lnSignInBean1.setRec_type("收电");


        LnSignInBean lnSignInBean2 = new LnSignInBean();
        lnSignInBean2.setTitle("走进新时代");
        lnSignInBean2.setDate("2018-08-01");
        lnSignInBean2.setBaomi_level("加密");
        lnSignInBean2.setDoc_no("44");
        lnSignInBean2.setFrom("句容xx单位");
        lnSignInBean2.setRec_type("来信");

        LnSignInBean lnSignInBean3 = new LnSignInBean();
        lnSignInBean3.setTitle("厉害了我的锅");
        lnSignInBean3.setDate("2018-08-21");
        lnSignInBean3.setBaomi_level("相当紧密");
        lnSignInBean3.setDoc_no("32");
        lnSignInBean3.setFrom("句容xx单位");
        lnSignInBean3.setRec_type("邮件");


        dataList.add(lnSignInBean1);
        dataList.add(lnSignInBean2);
        dataList.add(lnSignInBean3);


        adapter = new LnSignInAdapter(dataList);
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

