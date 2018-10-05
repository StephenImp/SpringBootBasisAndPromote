package com.cn.entity;

/**
 * Created by Administrator on 2018\9\12 0012.
 */
public class TUser {

    private int id;
    private String xname;
    private String xinfo;
    private int bid;

    public TUser() {
    }

    public TUser(int id, String xname, String xinfo, int bid) {
        this.id = id;
        this.xname = xname;
        this.xinfo = xinfo;
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname;
    }

    public String getXinfo() {
        return xinfo;
    }

    public void setXinfo(String xinfo) {
        this.xinfo = xinfo;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}
