package com.icebroken.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class DepBean {
    /**
     * Pid : 部门ID
     * Name : 部门名
     */

    private String Pid;
    private String Name;
    private List<AddressBean> addressBeans;

    public String getPid() {
        return Pid;
    }

    public void setPid(String Pid) {
        this.Pid = Pid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<AddressBean> getAddressBeans() {
        return addressBeans;
    }

    public void setAddressBeans(List<AddressBean> addressBeans) {
        this.addressBeans = addressBeans;
    }
}
