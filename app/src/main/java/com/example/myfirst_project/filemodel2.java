package com.example.myfirst_project;

public class filemodel2 {


    String title,u_img,u_nam,userid,vurl;

    public filemodel2(String title, String u_img, String u_nam, String userid, String vurl) {
        this.title = title;
        this.u_img = u_img;
        this.u_nam = u_nam;
        this.userid = userid;
        this.vurl = vurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }

    public String getU_nam() {
        return u_nam;
    }

    public void setU_nam(String u_nam) {
        this.u_nam = u_nam;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }
}
