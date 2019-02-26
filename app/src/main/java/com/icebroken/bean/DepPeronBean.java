package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Yorashe on 18-7-19.
 */

public class DepPeronBean implements Serializable{

    /**
     * Uid : 用户ID
     * Name : 用户名
     * Role : 角色
     */

    private String Uid;
    private String Name;
    private String Role;
    private boolean isCheck;

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
