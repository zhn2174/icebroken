package com.icebroken.bean;

public class SchoolsBean {
    /**
     * id : 726
     * name : 南京大学
     * identificationCode : 4132010284
     * cityName : 南京市
     * cityId : 3201
     * schoolLevel : 本科
     * lat : null
     * lng : null
     */

    private int id;
    private String name;
    private String cityName;
    private int cityId;
    private String schoolLevel;
    private String lat;
    private String lng;

    public SchoolsBean() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
