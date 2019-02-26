package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/22.
 */

public class UserBean implements Serializable{

    /**
     * Name : 真实姓名
     * Headurl : 图片地址
     * Tel : 电话
     * Sex : 性别
     * Date : 日期时间戳
     * Area : 地区
     * Department : 主要所属部门
     * Role : 角色
     */

    private String Name;
    private String Headurl;
    private String Tel;
    private String Sex;
    private String Date;
    private String Area;
    private String Department;
    private String Role;
    private String Codeurl;

    public String getCodeurl() {
        return Codeurl;
    }

    public void setCodeurl(String codeurl) {
        Codeurl = codeurl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getHeadurl() {
        return Headurl;
    }

    public void setHeadurl(String Headurl) {
        this.Headurl = Headurl;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
}
