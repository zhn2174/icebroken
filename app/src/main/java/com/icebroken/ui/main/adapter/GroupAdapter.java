package com.icebroken.ui.main.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.bean.AddressBean;
import com.icebroken.bean.DepBean;
import com.icebroken.utils.SignUtil;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yorashe on 18-6-25.
 */
public class GroupAdapter extends BaseQuickAdapter<DepBean, BaseViewHolder> {


    public GroupAdapter(List<DepBean> data) {
        super(R.layout.group_adapter, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DepBean item) {
        helper.setText(R.id.title, item.getName());
        RecyclerView mRecyclerView = helper.getView(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        final PersonAdapter adapter = new PersonAdapter(new ArrayList<AddressBean>());
        mRecyclerView.setAdapter(adapter);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject map = new JSONObject();
                try {
                    map.put("State", "TelList");
                    map.put("Class", item.getPid());
                    map.put("Uid", AppApplication.uid);
                    map.put("Page", "1");
                    map.put("ShowNum", "100");
                    map.put("Sign", SignUtil.createSign(map));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Api.getDefault(HostType.MAIN).queryAddressList(map.toString())
                        .compose(RxHelper.<List<AddressBean>>handleResult()).subscribe(new RxSubscriber<List<AddressBean>>(mContext, false) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void _onError(String e) {

                    }

                    @Override
                    public void _onNext(List<AddressBean> beans) {
                        if (null != beans) {
                            helper.getView(R.id.content_lay).setVisibility(helper.getView(R.id.content_lay).getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                            adapter.setNewData(beans);
                        }
                    }
                });
            }
        });
    }

}
