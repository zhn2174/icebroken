package com.icebroken.bean;


/**
 * Created by Yorashe on 18-7-12.
 */

public class LoginBean {
    /**
     * Result : yes
     * State : Login
     * data : {"State":"Login","Version":"1.0","Uid":"1"}
     */

        private String State;
        private String Version;
        private String Uid;

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public String getUid() {
            return Uid;
        }

        public void setUid(String Uid) {
            this.Uid = Uid;
        }


    
}
