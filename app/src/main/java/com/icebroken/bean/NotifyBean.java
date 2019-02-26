package com.icebroken.bean;

/**
 * Created by Yorashe on 18-6-25.
 */

public class NotifyBean {
    String imgUrl;
    String title;
    boolean isRead;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
