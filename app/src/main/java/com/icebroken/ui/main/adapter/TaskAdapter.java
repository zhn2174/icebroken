package com.icebroken.ui.main.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mocuz.common.commonutils.TimeUtil;

import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.TaskBean;

/**
 * Created by Yorashe on 18-6-25.
 */

public class TaskAdapter extends BaseQuickAdapter<TaskBean,BaseViewHolder>{


    public TaskAdapter(List< TaskBean> data) {
        super(R.layout.task_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,  TaskBean item) {
        helper.setText(R.id.title,item.getFLOW_NAME());
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
//            String date = format.format(new Date(Long.parseLong(item.getApplyTime())))+" 结束";
//        }catch (Exception e){
//        }
        if (!TextUtils.isEmpty(item.getRUN_NAME())){
            helper.setText(R.id.end_time_text,item.getRUN_NAME());
        }

        long sendTime=item.getCREATE_TIME().getTime();
        helper.setText(R.id.time1, TimeUtil.getfriendlyTime(sendTime));
        helper.setText(R.id.onwer,item.getUSER_NAME()+"发布的");
        helper.setText(R.id.AnnotationNum,item.getAnnotationNum()+"条动态");
//        helper.setText(R.id.Percent,item.getPercent());

    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        super.setOnItemChildClickListener(listener);
    }

}
