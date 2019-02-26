package com.icebroken.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;
import com.mocuz.common.commonutils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseActivity;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.SignUtil;
import com.icebroken.utils.StringUtils;
import com.icebroken.utils.WwyUtil;
import com.icebroken.widget.MyToolbar;

/**
 * Created by Administrator on 2018/7/1.
 */

public class ApplyFeedBackActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.myToolbar)
    MyToolbar myToolbar;
    @Bind(R.id.topview)
    AppBarLayout topview;
    @Bind(R.id.ed_apply)
    EditText edApply;
    @Bind(R.id.bt_sure)
    Button btSure;
    private int mTid;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.apply_feed_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
         type =getIntent().getIntExtra("type",1);
         mTid =getIntent().getIntExtra("tid",0);
        edApply.setHint("请输入"+(type==1?"同意":"拒绝")+"理由");
        btSure.setText("确认"+(type==1?"同意":"拒绝"));

        WwyUtil.setButtonStyleEnable(btSure);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.mipmap.return_icon);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtil.hideInput(ApplyFeedBackActivity.this);
                finish();
            }
        });
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()){
                    QueryData();
                }
            }
        });
    }


    /**
     *
     * @param activity
     * @param type 1同意，0拒绝
     */
    public static void startAction(Activity activity,int type,int tid) {
        Intent intent = new Intent(activity, ApplyFeedBackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putInt("tid",tid);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    private boolean checkInfo() {
        if (StringUtils.isEmpty(edApply.getText().toString())) {
            ToastUitl.showShort("请先填写审批建议");
            return false;
        }

        return true;
    }

    //1同意0拒绝Result
    private void QueryData(){
        JSONObject map = new JSONObject();
        try {
            map.put("State", "approve");
            map.put("Uid", AppApplication.uid);
            map.put("Runid", mTid+"");
            map.put("NowPrcsid", "2");
            map.put("Data", edApply.getText().toString());
            map.put("Type", "0");
            map.put("People", type+"");
            map.put("Result", type+"");
            map.put("Sign", SignUtil.createSign(map));
            map.put("Carmode", type+"");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).approve(map.toString())
                .compose(RxHelper.<Object>handleResult()).subscribe(new RxSubscriber<Object>(this, false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
//                showShortToast(e);
                showShortToast("审批成功");
                finish();
            }

            @Override
            public void _onNext(Object beans) {
                showShortToast("审批成功");
                finish();

            }
        });

    }


}
