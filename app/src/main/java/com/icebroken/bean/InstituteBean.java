package com.icebroken.bean;

public class InstituteBean {
    /**
     * id : 7
     * schoolId : 776
     * departmentName : 传媒学院
     */

    private int id;
    private int schoolId;
    private String departmentName;

    public InstituteBean() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
