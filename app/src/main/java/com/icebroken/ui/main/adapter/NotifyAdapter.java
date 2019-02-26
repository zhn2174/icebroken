package com.icebroken.ui.main.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mocuz.common.commonutils.ImageLoaderUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.NotiBean;

/**
 * Created by Yorashe on 18-6-25.
 */

public class NotifyAdapter extends BaseQuickAdapter< NotiBean,BaseViewHolder>{


    public NotifyAdapter(List< NotiBean> data) {
        super(R.layout.main_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,  NotiBean item) {
        helper.setText(R.id.text,item.getTitle());
        SimpleDateFormat format =new SimpleDateFormat("HH:mm");
        Date date =new Date(Integer.parseInt(item.getTime()));
        helper.setText(R.id.time,format.format(date));
        ImageView head = helper.getView(R.id.head_icon);
        ImageLoaderUtils.display(mContext,head,item.getImgurl());

    }


}
