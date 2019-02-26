package com.icebroken.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.bean.JsonBean;
import com.icebroken.bean.UserInfo;
import com.icebroken.utils.BaseUtil;
import com.icebroken.widget.MyToolbar;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Yorashe on 19-2-18.
 */

public class OrganizingData2Activity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.school_text)
    TextView schoolText;
    @Bind(R.id.school_select)
    RelativeLayout schoolSelect;
    @Bind(R.id.school_ed)
    EditText schoolEd;
    @Bind(R.id.start_school_text)
    TextView startSchoolText;
    @Bind(R.id.start_school_select)
    RelativeLayout startSchoolSelect;
    @Bind(R.id.identity_text)
    TextView identityText;
    @Bind(R.id.identity_select)
    RelativeLayout identitySelect;
    @Bind(R.id.student_text)
    TextView studentText;
    @Bind(R.id.student_select)
    RelativeLayout studentSelect;
    @Bind(R.id.talk_ck)
    CheckBox talkCk;
    @Bind(R.id.talk_select)
    RelativeLayout talkSelect;
    @Bind(R.id.bt_login)
    TextView btLogin;
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private TimePickerView pvCustomLunar;
    private static boolean isLoaded = false;
    private UserInfo userInfo;
    private String[] mIdentity_str = {"大学生", "研究生", "博士生"};
    private List<String> mIdentitys = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.organizing_s_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        initLunarPicker();
        userInfo = AppApplication.getUserInfo();
        initData();
    }

    private void initData() {
        mIdentitys.add("0");
        mIdentitys.add("1");
        mIdentitys.add("2");
    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        post();

    }

    private void post() {
        if (TextUtils.isEmpty(schoolEd.getText())) {
            showShortToast("请填写院系");
            return;
        }
        userInfo.setSchoolId(1);
        userInfo.setDepartment(schoolEd.getText().toString());
        userInfo.setIdentity("0");
        userInfo.setIsAllowDirectChat(talkCk.isChecked() ? "1" : "0");
        Gson gson = new Gson();
        showProgressDialog("正在完善资料");
        Api.getDefault(HostType.MAIN).completeInformation(gson.toJson(userInfo))
                .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                hideProgressDialog();
            }

            @Override
            public void _onNext(Object bean) {
                hideProgressDialog();
                showShortToast("资料完善成功");


            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 12, 31);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                startSchoolText.setText(getTime(date));
                userInfo.setEnterSchoolYear((int) (date.getTime() / 1000));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                Toast.makeText(mContext, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void queryInfo() {
        Api.getDefault(HostType.MAIN).getUserInfo()
                .compose(RxHelper.<UserInfo>handleResult()).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
                hideProgressDialog();
            }

            @Override
            public void _onNext(UserInfo bean) {
                userInfo.setCertify(bean.isCertify());
                try {
                    if (null != userInfo && userInfo.isCertify()) {
                        studentText.setText("已认证");
                        studentSelect.setEnabled(false);
                    } else {
                        studentText.setText("去认证");
                        studentSelect.setEnabled(true);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppApplication.setUserInfo(userInfo);
            }
        });
    }

    @OnClick({R.id.school_select, R.id.start_school_select, R.id.identity_select, R.id.student_select, R.id.talk_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.school_select:
                break;
            case R.id.start_school_select:
                pvCustomLunar.show();
                BaseUtil.hideInput(mContext);
                break;
            case R.id.identity_select:
                ShowPop(mIdentity_str, mIdentitys, "选择身份", identityText);
                break;
            case R.id.student_select:
                AuthenticationActivity.startAction(this);
                break;
            case R.id.talk_select:
                talkCk.performClick();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryInfo();


    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, OrganizingData2Activity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
