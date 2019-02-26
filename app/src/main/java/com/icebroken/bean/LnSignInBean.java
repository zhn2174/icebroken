package com.icebroken.bean;

import java.io.Serializable;

/**
 * 签收
 */

public class LnSignInBean implements Serializable{

    private String title;
    private String date;

    private String rec_type;

    private String baomi_level;

    private String doc_no;

    private String from;


    private String nigao_user;
    private String jinji_chengdu;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRec_type() {
        return rec_type;
    }

    public void setRec_type(String rec_type) {
        this.rec_type = rec_type;
    }

    public String getBaomi_level() {
        return baomi_level;
    }

    public void setBaomi_level(String baomi_level) {
        this.baomi_level = baomi_level;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getNigao_user() {
        return nigao_user;
    }

    public void setNigao_user(String nigao_user) {
        this.nigao_user = nigao_user;
    }

    public String getJinji_chengdu() {
        return jinji_chengdu;
    }

    public void setJinji_chengdu(String jinji_chengdu) {
        this.jinji_chengdu = jinji_chengdu;
    }
}
