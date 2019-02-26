package com.icebroken.ui.main.fragment;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.AddressBean;
import com.icebroken.bean.GetFriendBean;
import com.icebroken.ui.main.adapter.NearFriendAdapter;
import com.icebroken.utils.SignUtil;
import com.icebroken.utils.StringUtils;
import com.icebroken.widget.MyLetterListView;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yorashe on 18-6-27.
 */

public class AddressFragment extends BaseFragment {

    @Bind(R.id.list_contacts)
    ListView list_contacts;
    @Bind(R.id.myLetterListView01)
    MyLetterListView myLetterListView01;
    private   List<AddressBean> names = new ArrayList<AddressBean>();
    private NearFriendAdapter adapter;
    /**
     * 每个姓氏第一次出现对应的position
     */
    private int[] counts;
    private TextView txtOverlay; // 用来放在WindowManager中显示提示字符
    private WindowManager windowManager;
    private Handler handler;
    private DisapearThread disapearThread;
    private int scrollState; // 滚动的状态
    //	public static List<GetFriendBean> UserCenterFragment.names;
    private List<AddressBean> searchList;
    //	private String newNum;
//	private PullToRefreshView myPull;
    private String[] sections = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z" ,"#"};
    private static final String ALL_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#" ;
    private String mSortKey = "#";
    private EditText search;
    private CharSequence beforeSearch;
    private TextView msg_num;
    @Override
    protected int getLayoutResource() {
        return R.layout.address_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        txtOverlay = (TextView) LayoutInflater.from(getActivity())
                .inflate(R.layout.contacts_popup, null);
        // 默认设置为不可见。
        txtOverlay.setVisibility(View.INVISIBLE);
        // 设置WindowManager
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                // 设置为无焦点状态
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                // 半透明效果
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(txtOverlay, lp);

        handler = new Handler();
        disapearThread = new DisapearThread();
//        list_contacts.setOnScrollListener(this);
        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                position=position-1;
//                Intent intent=new Intent(NearFriendActivity.this,ChatRoomActivity.class);
//				intent.putExtras(bs);
//                startActivity(intent);
            }
        });
        myLetterListView01
                .setOnTouchingLetterChangedListener(new LetterListViewListener());
        // 监听软键盘是否打开
        myLetterListView01.setOnResizeListener(new MyLetterListView.OnResizeListener() {

            public void OnResize(int w, int h, int oldw, int oldh) {
                // if (h < oldh) {
                // letterListView.setVisibility(View.INVISIBLE);
                // isSoftKeyboardOn = true;
                // } else {
                // Log.e("yyy", "-----3-----");
                // letterListView.setVisibility(View.VISIBLE);
                // isSoftKeyboardOn = false;
                // }
            }
        });
        LoadData();
    }

    private void queryData() {
        JSONObject map = new JSONObject();
        try {

            map.put("State", "TelList");
            map.put("Class", "0");
            map.put("Uid", AppApplication.uid);
            map.put("Page", "1");
            map.put("ShowNum", "999");
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryAddressList(map.toString())
                .compose(RxHelper.<List<AddressBean>>handleResult()).subscribe(new RxSubscriber<List<AddressBean>>(getContext(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(List<AddressBean> beans) {
                if (null!= beans)
//                dataList.addAll(beans);
                    names.addAll(beans);
                    adapter.notifyDataSetChanged();
            }
        });
    }

    private void LoadData() {
        adapter = new NearFriendAdapter(
               getActivity(), names);
        list_contacts.setAdapter(adapter);
        myLetterListView01
                .setVisibility(View.VISIBLE);
        queryData();
        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private class DisapearThread implements Runnable {
        public void run() {
            if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
                txtOverlay.setVisibility(View.INVISIBLE);
            }
        }
    }

    // 将选中的py与stringArr的首字符进行匹配并返回对应字符串在数组中的位置
    public static int binSearch(List<AddressBean> list, String s) {
        for (int i = 0; i < list.size(); i++) {
            String name = (String) list.get(i).getName();
            if (s.equalsIgnoreCase("" + name.charAt(0))) { // 不区分大小写
                return i;
            }
        }
        return -1;

    }

    private class LetterListViewListener implements
            MyLetterListView.OnTouchingLetterChangedListener {

        public void onTouchingLetterChanged(final String s) {
            txtOverlay.setText(s);
            txtOverlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(disapearThread);
            // 提示延迟1.5s再消失
            handler.postDelayed(disapearThread, 1000);

            int localPosition = binSearch(names, s); // 接收返回值

            if (localPosition != -1) {
                // txtOverlay.setVisibility(View.INVISIBLE); //
                // 防止点击出现的txtOverlay与滚动出现的txtOverlay冲突
                list_contacts.setSelection(localPosition); // 让List指向对应位置的Item
            }
        }

    }
    protected void gotosearch(CharSequence c,List<GetFriendBean> list) {
        if (c.length()>0) {
            searchList = new ArrayList<AddressBean>();
            for (int i = 0; i < list.size(); i++) {
                String suoxie=StringUtils.getPinYinSimple(names.get(i).getName());
                String key=c.toString().toUpperCase();
                String pinyin= StringUtils.getPinYin(names.get(i).getName());
                if (pinyin.contains(key) || suoxie.contains(key)
                        ||names.get(i).getName().toUpperCase().contains(key)) {
                    searchList.add(names.get(i));
                }

            }
            adapter = new NearFriendAdapter(
                    getActivity(), searchList);
            list_contacts.setAdapter(adapter);
        }else{
            adapter = new NearFriendAdapter(
                    getActivity(), names);
            list_contacts.setAdapter(adapter);
        }
    }

//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem,
//                         int visibleItemCount, int totalItemCount) {
//        // if (isSearchState) {
//        // return;
//        // }
//        // // 开关用来处理查询模式下的冲突
//        // if (!searchSwitch && contactsList.size() > 0) {
//        // // 以中间的ListItem为标准项来显示。
//        // txtOverlay.setText(String.valueOf(
//        // ((String) contactsList.get(
//        // firstVisibleItem + (visibleItemCount >> 1))
//        // .getPinyin()).charAt(0)).toUpperCase());
//        //
//        // }
//
//    }


//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        this.scrollState = scrollState;
////        BaseUtil.hideInput(search);
//        if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
//            handler.removeCallbacks(disapearThread);
//            // 提示延迟1.5s再消失
//            handler.postDelayed(disapearThread, 1000);
//
//            // mPhotoLoader.resume();
//        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//            // mPhotoLoader.pause();
//        } else {
//            // txtOverlay.setVisibility(View.VISIBLE);
//            // mPhotoLoader.resume();
//        }
//    }
}
