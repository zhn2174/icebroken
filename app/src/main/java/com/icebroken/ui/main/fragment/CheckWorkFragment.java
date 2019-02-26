package com.icebroken.ui.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.utils.SignUtil;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.wevey.selector.dialog.MDEditDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by Yorashe on 18-6-26.
 */

public class CheckWorkFragment extends BaseFragment{
    @Bind(R.id.head_icon)
    ImageView headIcon;
    @Bind(R.id.head_text)
    TextView headText;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.title_text)
    TextView title_text;
    @Bind(R.id.time_text)
    TextView time_text;
    @Bind(R.id.dep_text)
    TextView dep_text;
    @Bind(R.id.postbtn)
    LinearLayout postbtn;
    private boolean isUp;


    @Override
    protected int getLayoutResource() {
        return R.layout.checkwork_post_lay;
    }

    @Override
    public void initPresenter() {

    }
    private SimpleDateFormat dateFormat;
    private String address;
    private MDEditDialog dialog;
    @Override
    public void initView() {
        mLocationClient = new LocationClient(getActivity());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
//            注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        dateFormat=new SimpleDateFormat("HH:mm:ss");

        String timeStr = dateFormat.format(new Date());
        time_text.setText(timeStr);
        if (Integer.parseInt((timeStr.split(":")[0]))<12){
            title_text.setText("上班打卡");
            isUp=true;
        }
        dateFormat=new SimpleDateFormat("yyyy:MM:dd");
        time.setText(dateFormat.format(new Date()));
        name.setText(AppApplication.user.getName());
        dep_text.setText("考勤组:"+AppApplication.user.getDepartment());
        headText.setText(AppApplication.user.getName().substring(0,1));
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new MDEditDialog.Builder(getActivity())
                        .setTitleVisible(true)
                        .setTitleText(dateFormat.format(new Date()))
                        .setTitleTextColor(R.color.black_light)
                        .setContentText(address)
                        .setMaxLength(99)
                        .setContentTextColor(R.color.colorPrimary)
                        .setButtonTextSize(14)
                        .setLeftButtonTextColor(R.color.colorPrimary)
                        .setLeftButtonText("取消")
                        .setRightButtonTextColor(R.color.colorPrimary)
                        .setRightButtonText("确定")
                        .setLineColor(R.color.colorPrimary)
                        .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                            @Override
                            public void clickLeftButton(View view, String text) {
                                //text为编辑的内容
                            }
                            @Override
                            public void clickRightButton(View view, String text) {
                                //text为编辑的内容
                                post();
                                dialog.dismiss();
                            }
                        })
                        .setMinHeight(0.3f)
                        .setWidth(0.8f)
                        .build();
                dialog.show();
//                post();
            }
        });

    }

    public static void startAction(Activity activity) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable("advInfo", advInfo);
    }

    private void post() {
        JSONObject map = new JSONObject();
        try {
            map.put("State", "WorkeIn");
            map.put("Uid", AppApplication.uid);
            //1上班,2下班
            map.put("Style", isUp?"1":"2");
            map.put("Data", time.getText());
            map.put("Type", isUp?"1":"2");
            map.put("Determin ", "1");
            map.put("Address", address);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).signin(map.toString())
                .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(Object beans) {
                showShortToast("考勤成功");
                getActivity().finish();
            }
        });
    }


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            address=addr;
        }
    }



}
