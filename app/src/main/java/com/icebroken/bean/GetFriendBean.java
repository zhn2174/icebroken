package com.icebroken.bean;

/**
 * Created by Administrator on 2018/7/1.
 */

public class GetFriendBean implements Comparable<GetFriendBean> {
    private String uid;
    private String username;
    private String onlinestatus;
    private String avatar;
    private int Follower;
    private int Following;
    private String Recentnote;
    private String field1;
    private String pinyin;
    private Integer FirstCar;
    private String birthyear;
    private String birthday;
    private String birthmonth;
    private String affectivestatus;
    private String sightml;
    private String age;
    private String regdate;
    private String gender;

    public GetFriendBean() {
    }

    public String getBirthyear() {
        return this.birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthmonth() {
        return this.birthmonth;
    }

    public void setBirthmonth(String birthmonth) {
        this.birthmonth = birthmonth;
    }

    public String getAffectivestatus() {
        return this.affectivestatus;
    }

    public void setAffectivestatus(String affectivestatus) {
        this.affectivestatus = affectivestatus;
    }

    public String getSightml() {
        return this.sightml;
    }

    public void setSightml(String sightml) {
        this.sightml = sightml;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRegdate() {
        return this.regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOnlinestatus() {
        return this.onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getField1() {
        return this.field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public int getFollower() {
        return this.Follower;
    }

    public void setFollower(int follower) {
        this.Follower = follower;
    }

    public int getFollowing() {
        return this.Following;
    }

    public void setFollowing(int following) {
        this.Following = following;
    }

    public String getRecentnote() {
        return this.Recentnote;
    }

    public void setRecentnote(String recentnote) {
        this.Recentnote = recentnote;
    }

    public Integer getFirstCar() {
        return this.FirstCar;
    }

    public void setFirstCar(Integer firstCar) {
        this.FirstCar = firstCar;
    }

    public int compareTo(GetFriendBean arg0) {
        return this.getFirstCar().compareTo(arg0.getFirstCar());
    }
}
