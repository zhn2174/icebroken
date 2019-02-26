package com.icebroken.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/26.
 */

public class TaskBean implements Serializable{
    /**
     * PRCS_ID : 2
     * PRCS_TIME : null
     * USER_NAME : admin
     * FORM_SEQ_ID : 729
     * RUN_ID : 78
     * RUN_NAME : 卫生安保申请(2018-07-31 13:59:22)
     * SEQ_ID : 669
     * BEGIN_USER : 1
     * FLOW_NAME : 卫生安保申请
     */

    private int PRCS_ID;
    private Object PRCS_TIME;
    private String USER_NAME;
    private int FORM_SEQ_ID;
    private int RUN_ID;
    private String RUN_NAME;
    private int SEQ_ID;
    private int BEGIN_USER;
    private String FLOW_NAME;
    /**
     * PRCS_TIME : {"date":20,"day":1,"hours":10,"minutes":14,"month":7,"nanos":0,"seconds":10,"time":1534731250000,"timezoneOffset":-480,"year":118}
     * CREATE_TIME : {"date":20,"day":1,"hours":10,"minutes":14,"month":7,"nanos":0,"seconds":9,"time":1534731249000,"timezoneOffset":-480,"year":118}
     * AnnotationNum : 0
     * PRCS_FLAG : 2
     */

//    @SerializedName("PRCS_TIME")
    private PRCSTIMEBean PRCS_TIMEX;
    private CREATETIMEBean CREATE_TIME;
    private String AnnotationNum;
    private String PRCS_FLAG;


    public int getPRCS_ID() {
        return PRCS_ID;
    }

    public void setPRCS_ID(int PRCS_ID) {
        this.PRCS_ID = PRCS_ID;
    }

    public Object getPRCS_TIME() {
        return PRCS_TIME;
    }

    public void setPRCS_TIME(Object PRCS_TIME) {
        this.PRCS_TIME = PRCS_TIME;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public int getFORM_SEQ_ID() {
        return FORM_SEQ_ID;
    }

    public void setFORM_SEQ_ID(int FORM_SEQ_ID) {
        this.FORM_SEQ_ID = FORM_SEQ_ID;
    }

    public int getRUN_ID() {
        return RUN_ID;
    }

    public void setRUN_ID(int RUN_ID) {
        this.RUN_ID = RUN_ID;
    }

    public String getRUN_NAME() {
        return RUN_NAME;
    }

    public void setRUN_NAME(String RUN_NAME) {
        this.RUN_NAME = RUN_NAME;
    }

    public int getSEQ_ID() {
        return SEQ_ID;
    }

    public void setSEQ_ID(int SEQ_ID) {
        this.SEQ_ID = SEQ_ID;
    }

    public int getBEGIN_USER() {
        return BEGIN_USER;
    }

    public void setBEGIN_USER(int BEGIN_USER) {
        this.BEGIN_USER = BEGIN_USER;
    }

    public String getFLOW_NAME() {
        return FLOW_NAME;
    }

    public void setFLOW_NAME(String FLOW_NAME) {
        this.FLOW_NAME = FLOW_NAME;
    }

    public PRCSTIMEBean getPRCS_TIMEX() {
        return PRCS_TIMEX;
    }

    public void setPRCS_TIMEX(PRCSTIMEBean PRCS_TIMEX) {
        this.PRCS_TIMEX = PRCS_TIMEX;
    }

    public CREATETIMEBean getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(CREATETIMEBean CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getAnnotationNum() {
        return AnnotationNum;
    }

    public void setAnnotationNum(String AnnotationNum) {
        this.AnnotationNum = AnnotationNum;
    }

    public String getPRCS_FLAG() {
        return PRCS_FLAG;
    }

    public void setPRCS_FLAG(String PRCS_FLAG) {
        this.PRCS_FLAG = PRCS_FLAG;
    }

    public static class PRCSTIMEBean {
        /**
         * date : 20
         * day : 1
         * hours : 10
         * minutes : 14
         * month : 7
         * nanos : 0
         * seconds : 10
         * time : 1534731250000
         * timezoneOffset : -480
         * year : 118
         */

        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nanos;
        private int seconds;
        private long time;
        private int timezoneOffset;
        private int year;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getNanos() {
            return nanos;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class CREATETIMEBean {
        /**
         * date : 20
         * day : 1
         * hours : 10
         * minutes : 14
         * month : 7
         * nanos : 0
         * seconds : 9
         * time : 1534731249000
         * timezoneOffset : -480
         * year : 118
         */

        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nanos;
        private int seconds;
        private long time;
        private int timezoneOffset;
        private int year;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getNanos() {
            return nanos;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
