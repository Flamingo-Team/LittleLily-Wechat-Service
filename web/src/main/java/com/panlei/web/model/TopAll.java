package com.panlei.web.model;

/**
 * Created by 361pa on 2017/6/21.
 */
public class TopAll {

    Integer id;
    String  href ;
    String  title ;
    String  section;
    String  board;
    String  author;
    String  replyNumber;
    String  upvoteNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getUpvoteNumber() {
        return upvoteNumber;
    }

    public void setUpvoteNumber(String upvoteNumber) {
        this.upvoteNumber = upvoteNumber;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
