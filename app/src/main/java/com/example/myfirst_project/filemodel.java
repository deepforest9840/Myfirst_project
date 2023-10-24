package com.example.myfirst_project;

public class filemodel {
    String title, vurl,uname,uimg;

    public filemodel() {

    }

    public filemodel(String title, String vurl, String uname, String uimg) {
        this.title = title;
        this.vurl = vurl;
        this.uname = uname;
        this.uimg = uimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }
}

