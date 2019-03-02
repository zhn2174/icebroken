package com.icebroken.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icebroken.R;
import com.icebroken.bean.InstituteBean;

public class SelectInstituteAdpter extends BaseQuickAdapter<InstituteBean, BaseViewHolder> {

    public SelectInstituteAdpter() {
        super(R.layout.school_address_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, InstituteBean item) {
        helper.setText(R.id.address_name_tv, item.getDepartmentName());
    }
}
