package com.icebroken.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mocuz.common.commonutils.TimeUtil;

import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.MeetingBean;

/**
 * Created by Yorashe on 18-6-25.
 */

public class MeetingAdapter extends BaseQuickAdapter<MeetingBean, BaseViewHolder> {


    public MeetingAdapter(List<MeetingBean> data) {
        super(R.layout.meet_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetingBean item) {
        helper.setText(R.id.title, item.getTitle());
        try {
            helper.setText(R.id.time_end, item.getTimeTxt());
        } catch (Exception e) {
        }
        try {
            long sendTime = Long.parseLong(item.getApplyTime())*1000;
            helper.setText(R.id.time1, TimeUtil.getfriendlyTime(sendTime));
        } catch (Exception e) {
        }

        helper.setText(R.id.onwer, item.getFounder() + "发布的");
        helper.setText(R.id.num, item.getJoinNum());
    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        super.setOnItemChildClickListener(listener);
    }
}
