package com.icebroken.bean;


/**
 * Created by Yorashe on 18-7-12.
 */

public class NotiInfoBean {
    /**
     * Result : yes
     * State : NoticeList
     * data : [{"Nid":"通知唯一ID","Imgurl":"图片地址","Time":"时间戳","Title":"标题"},{"Nid":"通知唯一ID","Imgurl":"图片地址","Time":"时间戳","Title":"标题"}]
     */

        /**
         * Nid : 通知唯一ID
         * Imgurl : 图片地址
         * Time : 时间戳
         * Title : 标题
         */

        private String Nid;
        private String Imgurl;
        private String Time;
        private String Title;
        private String Content;

        public String getNid() {
            return Nid;
        }

        public void setNid(String Nid) {
            this.Nid = Nid;
        }

        public String getImgurl() {
            return Imgurl;
        }

        public void setImgurl(String Imgurl) {
            this.Imgurl = Imgurl;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
