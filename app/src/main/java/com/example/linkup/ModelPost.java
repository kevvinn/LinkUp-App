package com.example.linkup;

import android.util.Log;

public class ModelPost {
    public ModelPost() {
    }

    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTitle() {
        return title;
    }

    public String getEventTime() { return event_time; }

    public void setEventTime(String event_time) {
        this.event_time = event_time;
    }

    public String getEventDate() { return event_date; }

    public void setEventDate(String event_date) {
        this.event_date = event_date;
    }

    public String getEventLocation() { return event_location; }

    public void setEventLocation(String event_location) {
        this.event_location = event_location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUdp() {
        return udp;
    }

    public void setUdp(String udp) {
        this.udp = udp;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUimage() {
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPlike() {
        return plike;
    }

    public void setPlike(String plike) {
        this.plike = plike;
    }

    String pid;

    public String getPcomments() {
        return pcomments;
    }

    public void setPcomments(String pcomments) {
        this.pcomments = pcomments;
    }

    public String getEvent_time() { return event_time; }

    public ModelPost(String description, String pid, String ptime, String pcomments, String title, String udp, String uemail, String uid, String uimage, String uname, String plike) {
        this.description = description;
        this.pid = pid;
        this.ptime = ptime;
        this.pcomments = pcomments;
        this.title = title;
        this.udp = udp;
        this.uemail = uemail;
        this.uid = uid;
        this.uimage = uimage;
        this.uname = uname;
        this.plike = plike;
    }

    String ptime, pcomments;

    String title;

    String udp;
    String uemail;
    String uid;
    String uimage;

    String uname, plike;

    String event_date, event_time, event_location;

}
