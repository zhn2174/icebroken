package com.icebroken.ui.main.adapter;

/**
 * Created by Administrator on 2018/7/1.
 */

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.DepPeronBean;

public class PersonSelectAdapter extends BaseQuickAdapter<DepPeronBean,BaseViewHolder>{
    private List<DepPeronBean> beanList;
    private int count=0;
    @Override
    protected void convert(final BaseViewHolder helper,final  DepPeronBean item) {
        helper.setText(R.id.userName,item.getName());
        helper.getView(R.id.head_text).setVisibility(View.VISIBLE);
        helper.getView(R.id.head_icon).setVisibility(View.GONE);
        helper.setText(R.id.head_text,item.getName().substring(0,1));
        CheckBox checkBox =helper.getView(R.id.person_check);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getView(R.id.person_check).performClick();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                }
                count+=(isChecked?1:-1);
                DepPeronBean bean=item;
                bean.setCheck(isChecked);
                setData(getPosition(item),bean);
            }
        });
        try {
            helper.setChecked(R.id.person_check,item.isCheck());
        }catch (Exception e){

        }


    }
    public PersonSelectAdapter(List< DepPeronBean> data) {
        super(R.layout.select_porson_item, data);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

