package com.icebroken.bean;

import java.util.List;

/**
 * Created by Yorashe on 18-8-8.
 */

public class ApplyMainBean {


    /**
     * listdetails : [{"PLACE":"","RESULT":"","UNAME":"","BEGIN_TIME":"2018-08-08 14:12:10.0","REASON":"","REMARKS":"","UID":"1","RUN_ID":"196","RUN_NAME":"食堂申请(2018-08-08 14:12:10)","DEPARTMENT":""}]
     * ApproveList : []
     * lc : {formId:728,flowName:"食堂申请",runName:'食堂申请(2018-08-08 20:01:46)',flowType:1,introduction:"",autoEdit:1,prcsList:[{prcsNo:"1",prcsName:"申请",prcsTo:'2'},{prcsNo:"2",prcsName:"审批",prcsTo:'0,3,'},{prcsNo:"3",prcsName:"二级审批",prcsTo:'0'}]}
     * Now : [{"PEOPLRNAME":"admin-测试数据","REASON":"1","PEOPLRID":"1"}]
     * runid : 196
     */

    private String lc;
    private String runid;
    private List<ApplyBean> listdetails;
    private List<?> ApproveList;
    private List<NowBean> Now;

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getRunid() {
        return runid;
    }

    public void setRunid(String runid) {
        this.runid = runid;
    }

    public List<ApplyBean> getListdetails() {
        return listdetails;
    }

    public void setListdetails(List<ApplyBean> listdetails) {
        this.listdetails = listdetails;
    }

    public List<?> getApproveList() {
        return ApproveList;
    }

    public void setApproveList(List<?> ApproveList) {
        this.ApproveList = ApproveList;
    }

    public List<NowBean> getNow() {
        return Now;
    }

    public void setNow(List<NowBean> Now) {
        this.Now = Now;
    }

    public static class NowBean {
        /**
         * PEOPLRNAME : admin-测试数据
         * REASON : 1
         * PEOPLRID : 1
         */

        private String PEOPLRNAME;
        private String REASON;
        private String PEOPLRID;

        public String getPEOPLRNAME() {
            return PEOPLRNAME;
        }

        public void setPEOPLRNAME(String PEOPLRNAME) {
            this.PEOPLRNAME = PEOPLRNAME;
        }

        public String getREASON() {
            return REASON;
        }

        public void setREASON(String REASON) {
            this.REASON = REASON;
        }

        public String getPEOPLRID() {
            return PEOPLRID;
        }

        public void setPEOPLRID(String PEOPLRID) {
            this.PEOPLRID = PEOPLRID;
        }
    }
}
