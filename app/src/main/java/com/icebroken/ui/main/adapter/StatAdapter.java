package com.icebroken.ui.main.adapter;

/**
 * Created by Administrator on 2018/7/1.
 */

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.icebroken.R;
import com.icebroken.app.AppApplication;
import com.icebroken.bean.AddressBean;

public class StatAdapter extends BaseQuickAdapter<AddressBean,BaseViewHolder>{
    @Override
    protected void convert(BaseViewHolder helper, final AddressBean item) {
        if (TextUtils.equals(item.getTid(), AppApplication.uid) && 0==getPosition(item) ){
            helper.setVisible(R.id.v1,true);
            helper.setVisible(R.id.v2,true);
            helper.setText(R.id.userName,"我");

        }else{
            helper.setVisible(R.id.v1,false);
            helper.setVisible(R.id.v2,false);
            helper.setText(R.id.title,"第"+(getPosition(item)-1)+"名");
            helper.setText(R.id.userName,item.getName());

        }
        helper.setText(R.id.head_text,item.getName().substring(0,1));
//        helper.setText(R.id.time,item.getName().substring(0,1));
//        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OtherActivity.startAction((Activity) mContext,item);
//
//            }
//        });

    }
    public StatAdapter(List< AddressBean> data) {
        super(R.layout.stat_item, data);
    }

}

