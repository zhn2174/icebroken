package com.mocuz.common.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Synopsis     BaseAdapter
 * Author		Mosr
 * version		V4.0
 * Create 	    2017/3/14 17:17
 * Email  		intimatestranger@sina.cn
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    public List<T> mdata;
    protected LayoutInflater inflater;
    protected Context mContext;

    public BaseAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mdata == null)
            return 0;
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView=getViewOn(position, convertView, parent);
        AutoUtils.autoSize(mView);
        return mView;
    }

    /**
     * 必须实现，视图加载
     *
     * @param position
     * @param convertView
     * @param parent
     *
     * @return
     */
    protected abstract View getViewOn(int position, View convertView, ViewGroup parent);
}

