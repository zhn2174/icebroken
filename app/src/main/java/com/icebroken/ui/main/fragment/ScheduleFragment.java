package com.icebroken.ui.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;
import com.mocuz.common.baserx.RxHelper;
import com.mocuz.common.baserx.RxSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import com.icebroken.R;
import com.icebroken.api.Api;
import com.icebroken.api.HostType;
import com.icebroken.app.AppApplication;
import com.icebroken.base.BaseFragment;
import com.icebroken.bean.ScheduleBean;
import com.icebroken.utils.BaseUtil;
import com.icebroken.utils.SignUtil;
/**
 * Created by Yorashe on 18-6-26.
 */

public class ScheduleFragment extends BaseFragment {
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;

    @Override
    protected int getLayoutResource() {
        return R.layout.schedule_lay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView view;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(BaseUtil.px(48), BaseUtil.px(48));
                    convertView.setLayoutParams(params);
                }

                view = (TextView) convertView.findViewById(R.id.text);

                view.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    view.setTextColor(0xff9299a1);
                } else {
                    view.setTextColor(0xff9299a1);
                }

                return convertView;
            }
        });

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                mTitle.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
                QueryData(dataOne(mTitle.getText().toString()));
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);
        QueryData(new java.util.Date().getTime() / 1000 + "");

    }
    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }


    private void QueryData(String mTime) {
        JSONObject map = new JSONObject();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String date = sdf1.format(new java.util.Date());
        try {
            map.put("State", "ScheduleList");
            map.put("Uid", AppApplication.uid);
            map.put("Date", mTime);
            map.put("Sign", SignUtil.createSign(map));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Api.getDefault(HostType.MAIN).queryScheduleList(map.toString())
                .compose(RxHelper.<List<ScheduleBean>>handleResult()).subscribe(new RxSubscriber<List<ScheduleBean>>(getActivity(), false) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void _onError(String e) {
                showShortToast(e);
            }

            @Override
            public void _onNext(List<ScheduleBean> beans) {
//             c
//           ,"data":[{"Did":"57","BeginTime":"1531641060","AllDay":"1","EndTime":"1532505075","Content":"tyuu"}]}]
                content.setText(beans.get(0).getContent());
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date start = new Date(Long.parseLong(beans.get(0).getBeginTime()+"000"));
                Date end = new Date(Long.parseLong(beans.get(0).getEndTime()+"000"));
                time.setText(format.format(start) + "-" + format.format(end));
            }
        });

    }
}