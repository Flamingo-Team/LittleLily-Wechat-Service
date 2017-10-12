package com.panlei.web.model;

/**
 * Created by 361pa on 2017/6/21.
 */
public class Dock {

    private String number;
    private Boolean top;
    private String author;
    private String createTime;
    private String title;
    private String replyNumber;
    private String  WatchNumber;
    private     String  upvoteNumber;
    private String url;


    public String getUpvoteNumber() {
        return upvoteNumber;
    }

    public void setUpvoteNumber(String upvoteNumber) {
        this.upvoteNumber = upvoteNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getWatchNumber() {
        return WatchNumber;
    }

    public void setWatchNumber(String watchNumber) {
        WatchNumber = watchNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
