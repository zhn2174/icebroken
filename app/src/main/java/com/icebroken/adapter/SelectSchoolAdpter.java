package com.icebroken.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icebroken.R;
import com.icebroken.bean.SchoolsBean;
import com.mocuz.common.commonutils.LocationUtils;
import com.mocuz.common.commonutils.StringUtil;

public class SelectSchoolAdpter extends BaseQuickAdapter<SchoolsBean, BaseViewHolder> {
    private boolean isNeedShowDistance;

    public SelectSchoolAdpter(boolean isNeedShowDistance) {
        super(R.layout.school_address_item_view);
        this.isNeedShowDistance = isNeedShowDistance;
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolsBean item) {
        helper.setText(R.id.address_name_tv, item.getName());
        if (isNeedShowDistance && LocationUtils.getBdLocation().hasAltitude() &&
                StringUtil.isNotBlank(item.getLat()) && StringUtil.isNotBlank(item.getLng())) {
            helper.setText(R.id.address_distance_tv,
                    StringUtil.getDistance(LocationUtils.getBdLocation().getLatitude(),
                            LocationUtils.getBdLocation().getLongitude(),
                            Double.valueOf(item.getLat()),
                            Double.valueOf(item.getLng())));
        } else {
            helper.setText(R.id.address_distance_tv, "");
        }

    }
}
