package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Yorashe on 18-7-23.
 */

public class AddressBean implements Serializable {

    /**
     * Name : admin
     * Mobile : 12345678011
     * Short :
     * Department : 句容市开发区
     * Tid : 1
     * Tel : 15651638828
     */

    private String Name;
    private String Mobile;
    private String Short;
    private String Department;
    private String Tid;
    private String Tel;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getShort() {
        return Short;
    }

    public void setShort(String Short) {
        this.Short = Short;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String Tid) {
        this.Tid = Tid;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }
}
