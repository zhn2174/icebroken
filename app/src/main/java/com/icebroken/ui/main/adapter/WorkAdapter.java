package com.icebroken.ui.main.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.WorkMainBean;
import com.icebroken.widget.LineGridView;


/**
 * Created by Dev on 2017/3/14.
 */

public class WorkAdapter extends BaseQuickAdapter<WorkMainBean, BaseViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    public WorkAdapter(List<WorkMainBean> mFindBeanList) {
        super(R.layout.work_item_lay, mFindBeanList);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final WorkMainBean mFindBean) {
        LineGridView mLineMGridView = baseViewHolder.getView(R.id.min_module);
        LineGridView mLineDGridView = baseViewHolder.getView(R.id.gov_module);
        ImageView img_icon = baseViewHolder.getView(R.id.img_icon);
        TextView txt_module_name = baseViewHolder.getView(R.id.txt_module_name);
        View view_top = baseViewHolder.getView(R.id.view_top);
        View view_bottom = baseViewHolder.getView(R.id.view_bottom);
        int postion = getData().indexOf(mFindBean);
//        Glide.with(mContext).load("1111").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(img_icon);
//        ImageLoaderUtils.display(mContext,img_icon, "1111");
//        img_icon.setVisibility(null != url ? View.VISIBLE : View.GONE);
        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
            mLineMGridView.setmBorderLine(false);
            mLineMGridView.setmBottomLine(false);
            mLineMGridView.setmEndBottomLine(false);
            view_top.setVisibility(View.GONE);
            view_bottom.setVisibility(View.GONE);

            view_top.setVisibility(View.GONE);
            view_bottom.setVisibility(View.GONE);
            mLineDGridView.setmBorderLine(false);
            mLineDGridView.setmBottomLine(false);
            mLineDGridView.setmEndBottomLine(getData().size() - 1 == postion);

    }
}
