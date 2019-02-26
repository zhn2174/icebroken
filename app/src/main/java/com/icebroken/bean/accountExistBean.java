package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Yorashe on 19-2-18.
 */

public class accountExistBean implements Serializable{
    private Boolean isExist;//账号是否已经被使用，true-已被使用
    private Boolean isComplete;//该账号是否完善基本信息了，true-已被完善
    private Boolean isCompleteSchool;//该账号是否完善学校信息了，true-已被完善
    private String token;//qq或者微信的isExist和isComplete都为true时，则说明登陆成功，token有值


    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Boolean getCompleteSchool() {
        return isCompleteSchool;
    }

    public void setCompleteSchool(Boolean completeSchool) {
        isCompleteSchool = completeSchool;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
