package com.panlei.web.model;

/**
 * Created by 361pa on 2017/8/12.
 */
public class Upvote {
    private Integer id;
    private String topicID;
    private String floorID;
    private String webchatID;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getFloorID() {
        return floorID;
    }

    public void setFloorID(String floorID) {
        this.floorID = floorID;
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
