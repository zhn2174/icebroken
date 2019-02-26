package com.icebroken.ui.main.adapter;

/**
 * Created by Administrator on 2018/7/1.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.icebroken.R;
import com.icebroken.bean.AddressBean;
import com.icebroken.utils.StringUtils;

public class NearFriendAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<AddressBean> names;
    private ViewHolder vHolder = null;
    public NearFriendAdapter(Context context, List<AddressBean> names) {
        this.inflater = LayoutInflater.from(context);
        this.names=names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public AddressBean getItem(int arg0) {
        return names.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            vHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.friend_item, null, false);
            vHolder.userPhoto=(ImageView) convertView.findViewById(R.id.head_icon);
            vHolder.userName=(TextView) convertView.findViewById(R.id.userName);
            vHolder.head_text=(TextView) convertView.findViewById(R.id.head_text);
            vHolder.text_first_char_hint=(TextView) convertView.findViewById(R.id.text_first_char_hint);
            vHolder.layout_first_char_hint=(RelativeLayout) convertView.findViewById(R.id.layout_first_char_hint);
            convertView.setTag(vHolder);
        }else{
            vHolder=(ViewHolder) convertView.getTag();
        }
        vHolder.userName.setText(names.get(position).getName());

        final String url=names.get(position).getTid();
        String newPreviewChar=null;
        if (position!=0) {
            newPreviewChar =StringUtils.getPinYin(names.get(position-1).getName()).substring(0, 1);
        }
        String newCurrentChar = StringUtils.getPinYin(names.get(position).getName()).substring(0, 1);
        if (!newCurrentChar.equals(newPreviewChar)) {
            vHolder.layout_first_char_hint.setVisibility(View.VISIBLE);
            vHolder.text_first_char_hint.setText(StringUtils.getPinYin(names.get(position).getName()).substring(0, 1));
        } else {
            vHolder.layout_first_char_hint.setVisibility(View.GONE);
        }
//        if (!StringUtils.isEmpty(url)) {
////            AppApplication.getGameImageLoader().DisplayImage(url, image, R.drawable.person_btn);
//        }else{
            vHolder.head_text.setVisibility(View.VISIBLE);
            vHolder.userPhoto.setVisibility(View.GONE);
            vHolder.head_text.setText(names.get(position).getName().substring(0,1));
//        }
        return convertView;
    }
    class ViewHolder {
        ImageView userPhoto;//头像
        TextView head_text;//头像
        TextView userName;//姓名
        RelativeLayout layout_first_char_hint;
        TextView text_first_char_hint;
    }


}

