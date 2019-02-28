package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Yorashe on 19-2-20.
 */

public class UserInfo implements Serializable {

    private Boolean isComplete;//该账号是否完善基本信息了，true-已被完善
    private Boolean isCompleteSchool;//该账号是否完善学校信息了，true-已被完善
    public String headUrl;//    headUrl 	否 	string 	头像地址（基本信息）
    private String nickname;//    nickname 	否 	string 	用户昵称（基本信息）
    private int birthday;//    birthday 	否 	int 	生日，时间戳 （基本信息）
    private int hometownCode;//    hometownCode 	否 	int 	家乡code（基本信息）
    private int sex;//    sex 	否 	int 	0-女 1-男（基本信息）
    private int emotionalState;//    emotionalState 	否 	int 	情感状态 0-单身 1-恋爱中 2-保密（基本信息）
    private int schoolId;//    schoolId 	否 	int 	学校id （学校信息）
    private String department;//    department 	否 	string 	院系 （学校信息）
    private int enterSchoolYear;//    enterSchoolYear 	否 	int 	入学年，时间戳 （学校信息）
    private String identity;//    identity 	否 	string 	身份 0-大学生 1-研究生 2-博士生（学校信息)
    private String isAllowDirectChat;//    isAllowDirectChat 	否 	string 	是否允许其他人直接聊天 0-不允许 1-允许 （学校信息)
    /**
     * id : 6
     * phone : 18655081109
     * countryCode : 86
     * headUrl : null
     * nickname : null
     * birthday : null
     * hometownCode : null
     * schoolId : null
     * department : null
     * enterSchoolYear : null
     * identity : 0
     * isCertify : false
     * wechatId : null
     * qqId : null
     * createdTime : 1550666643968
     */

    private int id;
    private String phone;
    private String countryCode;
    private boolean isCertify;//是否认证学生,true-已认证
    private String wechatId;
    private String qqId;
    private long createdTime;


    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public boolean isCertify() {
        return isCertify;
    }

    public void setCertify(boolean certify) {
        isCertify = certify;
    }


    public Boolean getCompleteSchool() {
        return isCompleteSchool;
    }

    public void setCompleteSchool(Boolean completeSchool) {
        isCompleteSchool = completeSchool;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getHometownCode() {
        return hometownCode;
    }

    public void setHometownCode(int hometownCode) {
        this.hometownCode = hometownCode;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getEmotionalState() {
        return emotionalState;
    }

    public void setEmotionalState(int emotionalState) {
        this.emotionalState = emotionalState;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getEnterSchoolYear() {
        return enterSchoolYear;
    }

    public void setEnterSchoolYear(int enterSchoolYear) {
        this.enterSchoolYear = enterSchoolYear;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIsAllowDirectChat() {
        return isAllowDirectChat;
    }

    public void setIsAllowDirectChat(String isAllowDirectChat) {
        this.isAllowDirectChat = isAllowDirectChat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isIsCertify() {
        return isCertify;
    }

    public void setIsCertify(boolean isCertify) {
        this.isCertify = isCertify;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
