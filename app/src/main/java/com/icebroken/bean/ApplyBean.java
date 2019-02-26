package com.icebroken.bean;

import android.text.TextUtils;

/**
 * Created by Yorashe on 18-7-31.
 */

public class ApplyBean {


    /**
     * RESULT :
     * CONTENT : yuiii
     * COUNTDAY : 1
     * BEGINTIME : 2018-07-31 16:09:31
     * RUN_ID : 82
     * RUN_NAME : 请假申请(2018-07-31 16:09:32)
     * ENDTIME : 2018-07-31 16:09:31
     * UID  :
     * TYPE :
     */
//{"State":"Form2","Uid":"1","Department":"5","BeginTime":"1533198646","EndTime":"1533285046","CountDay":"1","Title":"咯的","Content":"咯的","Style":"冬季","JoinNum":"2","JoinUid":"1","MeetingRoom":"164","Facilities":"165","People":"1","Sign":"6267d4a01b1ab2102139d2760e7115b6"}
//[{"Result":"yes","ApproveList":[],"State":"details","data":[{"RUN_ID":"106","RUN_NAME":"会议申请(2018-08-01 16:28:14)","FACILITIES":"165","STYLE":"冬季","RESULT":"","UID":"1","JOINNUM":"2","BEGINTIME":"2018-08-01 16:28:14","MEETINGROOM":"164","COUNTDAY":"1","ENDTIME":"2018-08-01 16:28:14","DEPARTMENT":"5","TITLE":"咯的"}],"ListPhotoname":[],"Now":"2","WorkList":"{formId:670,flowName:\"会议申请\",runName:'会议申请(2018-08-01 16:28:28)',flowType:1,introduction:\"\",autoEdit:1,prcsList:[{prcsNo:\"1\",prcsName:\"申请\",prcsTo:'2'},{prcsNo:\"2\",prcsName:\"审批\",prcsTo:'0'}]}","Runid":"106"}]

    private String RESULT;
    private String CONTENT;
    private String COUNTDAY;
    private String BEGINTIME;
    private String RUN_ID;
    private String RUN_NAME;
    private String ENDTIME;
    private String UID;
    private String TYPE;
    /**
     * PLACE : 所在地
     * REMARKS : 备注
     * APPLYDATE : 2018-07-31 13:59:21
     * UNAME  :
     * DEPARTMENT : 测试部
     */

    private String PLACE;
    private String REMARKS;
    private String APPLYDATE;
    private String UNAME;
    private String DEPARTMENT;
    /**
     * FACILITIES : 投影,
     * STYLE : uuwuuw
     * BEGIN_TIME : 2018-08-06 13:43:56.0
     * JOINNUM : 2
     * MEETINGROOM : 164
     * TITLE : yuwii2
     */

    private String FACILITIES;
    private String STYLE;
    private String BEGIN_TIME;
    private String JOINNUM;
    private String MEETINGROOM;
    private String TITLE;


    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getCOUNTDAY() {
        return COUNTDAY;
    }

    public void setCOUNTDAY(String COUNTDAY) {
        this.COUNTDAY = COUNTDAY;
    }

    public String getBEGINTIME() {
        return TextUtils.isEmpty(BEGINTIME)?BEGIN_TIME:BEGINTIME;
    }

    public void setBEGINTIME(String BEGINTIME) {
        this.BEGINTIME = BEGINTIME;
    }

    public String getRUN_ID() {
        return RUN_ID;
    }

    public void setRUN_ID(String RUN_ID) {
        this.RUN_ID = RUN_ID;
    }

    public String getRUN_NAME() {
        return RUN_NAME;
    }

    public void setRUN_NAME(String RUN_NAME) {
        this.RUN_NAME = RUN_NAME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getPLACE() {
        return PLACE;
    }

    public void setPLACE(String PLACE) {
        this.PLACE = PLACE;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getAPPLYDATE() {
        return APPLYDATE;
    }

    public void setAPPLYDATE(String APPLYDATE) {
        this.APPLYDATE = APPLYDATE;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getFACILITIES() {
        return FACILITIES;
    }

    public void setFACILITIES(String FACILITIES) {
        this.FACILITIES = FACILITIES;
    }

    public String getSTYLE() {
        return STYLE;
    }

    public void setSTYLE(String STYLE) {
        this.STYLE = STYLE;
    }

    public String getBEGIN_TIME() {
        return BEGIN_TIME;
    }

    public void setBEGIN_TIME(String BEGIN_TIME) {
        this.BEGIN_TIME = BEGIN_TIME;
    }

    public String getJOINNUM() {
        return JOINNUM;
    }

    public void setJOINNUM(String JOINNUM) {
        this.JOINNUM = JOINNUM;
    }

    public String getMEETINGROOM() {
        return MEETINGROOM;
    }

    public void setMEETINGROOM(String MEETINGROOM) {
        this.MEETINGROOM = MEETINGROOM;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
