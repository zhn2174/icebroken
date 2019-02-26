package com.icebroken.bean;

/**
 * Created by Administrator on 2018/7/23.
 */

public class ScheduleBean {

    /**
     * Did : 日程唯一ID
     * BeginTime : 开始时间戳
     * EndTime : 结束时间戳
     * AllDay : 是否全天
     * Content : 内容
     */

    private String Did;
    private String BeginTime;
    private String EndTime;
    private String AllDay;
    private String Content;

    public String getDid() {
        return Did;
    }

    public void setDid(String Did) {
        this.Did = Did;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String BeginTime) {
        this.BeginTime = BeginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getAllDay() {
        return AllDay;
    }

    public void setAllDay(String AllDay) {
        this.AllDay = AllDay;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
