package com.panlei.web.model;

/**
 * Created by 361pa on 2017/6/4.
 */
public class UserNju {
    private Integer id;
    private String userName;
    private String passwd;
    private String cookie;
    private String webchatID;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getWebchatID() {
        return webchatID;
    }

    public void setWebchatID(String webchatID) {
        this.webchatID = webchatID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
