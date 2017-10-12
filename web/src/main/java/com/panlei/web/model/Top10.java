package com.panlei.web.model;

/**
 * Created by 361pa on 2017/5/29.
 */
public class Top10 {
    String  id ;
    String  href ;
    String  title ;
    String  zone;
    String  author;
    String  number;
    String  upvoteNumber;

    public String getUpvoteNumber() {
        return upvoteNumber;
    }

    public void setUpvoteNumber(String upvoteNumber) {
        this.upvoteNumber = upvoteNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
