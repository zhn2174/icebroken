package com.icebroken.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icebroken.R;
import com.icebroken.bean.SchoolAddressBean;
import com.mocuz.common.commonutils.LocationUtils;
import com.mocuz.common.commonutils.StringUtil;

public class SelectAddressAdpter extends BaseQuickAdapter<SchoolAddressBean, BaseViewHolder> {
    private boolean isNeedShowDistance;

    public SelectAddressAdpter(boolean isNeedShowDistance) {
        super(R.layout.school_address_item_view);
        this.isNeedShowDistance = isNeedShowDistance;
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolAddressBean item) {
        helper.setText(R.id.address_name_tv, item.getName());
        if (isNeedShowDistance && LocationUtils.getBdLocation().hasAltitude()) {
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
