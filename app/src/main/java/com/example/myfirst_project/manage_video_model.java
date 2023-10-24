package com.example.myfirst_project;
public class manage_video_model {
        String title;
        String vurl;

        public manage_video_model() {
                // Default constructor required for Firebase
        }

        public manage_video_model(String title, String vurl) {
                this.title = title; // Change field name to match your Firebase database
                this.vurl = vurl;
        }

        public String getTitle() {
                return title; // Change method name to match the field name in your Firebase database
        }

        public void setTitle(String title) {
                this.title = title; // Change method name to match the field name in your Firebase database
        }

        public String getVurl() {
                return vurl;
        }

        public void setVurl(String vurl) {
                this.vurl = vurl;
        }
}
