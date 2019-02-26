package com.icebroken.ui.main.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.LnSignInBean;

/**
 *  发文管理
 */

public class LnDocAdapter extends BaseQuickAdapter<LnSignInBean,BaseViewHolder>{


    public LnDocAdapter(List<LnSignInBean> data) {
        super(R.layout.ln_list_item_doc, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,  LnSignInBean item) {

        helper.setText(R.id.textTitle,item.getTitle());
        if (!TextUtils.isEmpty(item.getDate())){
            helper.setText(R.id.textDate,item.getDate());
        }
        helper.setText(R.id.textNigaoUser, "拟稿人："+item.getNigao_user());
        helper.setText(R.id.textLevel, "保密等级："+item.getBaomi_level());
        helper.setText(R.id.textNo, "收件文号："+item.getDoc_no());
        helper.setText(R.id.textJinjiChengdu, "紧急程度："+item.getJinji_chengdu());

    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        super.setOnItemChildClickListener(listener);
    }

}
