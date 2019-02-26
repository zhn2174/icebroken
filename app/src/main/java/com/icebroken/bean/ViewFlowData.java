package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Dev on 2017/3/27.
 */

public class ViewFlowData implements Serializable {
    private String day;
    private String mun;
    private String date;

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getMun() {
        return mun;
    }
    public void setMun(String mun) {
        this.mun = mun;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
