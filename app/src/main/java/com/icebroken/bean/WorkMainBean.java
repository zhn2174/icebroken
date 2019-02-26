package com.icebroken.bean;

import java.util.List;

/**
 * Created by Yorashe on 18-6-27.
 */

public class WorkMainBean {
    private String image_url;
    private List<ItemBean> items;
    private List<ItemBean> gvItems;
    public static class ItemBean{
        private String title;
        private String icon;
        private String url;
        private int resid;

        public int getResid() {
            return resid;
        }

        public void setResid(int resid) {
            this.resid = resid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public List<ItemBean> getItems() {
        return items;
    }

    public void setItems(List<ItemBean> items) {
        this.items = items;
    }

    public List<ItemBean> getGvItems() {
        return gvItems;
    }

    public void setGvItems(List<ItemBean> gvItems) {
        this.gvItems = gvItems;
    }
}
