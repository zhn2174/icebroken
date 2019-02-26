package com.icebroken.ui.main.adapter;

/**
 * Created by Administrator on 2018/7/1.
 */

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icebroken.R;
import com.icebroken.bean.AddressBean;

import java.util.List;

public class PersonAdapter extends BaseQuickAdapter<AddressBean,BaseViewHolder>{
    @Override
    protected void convert(BaseViewHolder helper, final AddressBean item) {
        helper.setText(R.id.userName,item.getName());
        final String url=item.getTid();
        helper.getView(R.id.layout_first_char_hint).setVisibility(View.GONE);
//        if (!StringUtils.isEmpty(url)) {
////            AppApplication.getGameImageLoader().DisplayImage(url, image, R.drawable.person_btn);
//        }else{
            helper.getView(R.id.head_text).setVisibility(View.VISIBLE);
            helper.getView(R.id.head_icon).setVisibility(View.GONE);
            helper.setText(R.id.head_text,item.getName().substring(0,1));
//        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    public PersonAdapter(List< AddressBean> data) {
        super(R.layout.friend_item, data);
    }

}

