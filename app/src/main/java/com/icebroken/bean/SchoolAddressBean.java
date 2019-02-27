package com.icebroken.bean;

public class SchoolAddressBean  {

    public SchoolAddressBean() {
    }

    /**
     * code : 11
     * name : 北京市
     * parentCode : 0
     * lng : 116.39564503788
     * lat : 39.92998577808
     */

    private int code;
    private String name;
    private int parentCode;
    private String lng;
    private String lat;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
