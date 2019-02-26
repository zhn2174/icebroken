package com.icebroken.bean;

/**
 * Created by Administrator on 2018/7/26.
 */

public class MeetingBean {

    /**
     * Mid : 会议唯一ID
     * Founder : 发布者
     * TimeTxt : 时间说明
     * ApplyTime : 发布的时间戳
     * Title : 会议标题
     * JoinNum : 参加人数
     */

    private String Mid;
    private String Founder;
    private String TimeTxt;
    private String ApplyTime;
    private String Title;
    private String JoinNum;

    public String getMid() {
        return Mid;
    }

    public void setMid(String Mid) {
        this.Mid = Mid;
    }

    public String getFounder() {
        return Founder;
    }

    public void setFounder(String Founder) {
        this.Founder = Founder;
    }

    public String getTimeTxt() {
        return TimeTxt;
    }

    public void setTimeTxt(String TimeTxt) {
        this.TimeTxt = TimeTxt;
    }

    public String getApplyTime() {
        return ApplyTime;
    }

    public void setApplyTime(String ApplyTime) {
        this.ApplyTime = ApplyTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getJoinNum() {
        return JoinNum;
    }

    public void setJoinNum(String JoinNum) {
        this.JoinNum = JoinNum;
    }
}
