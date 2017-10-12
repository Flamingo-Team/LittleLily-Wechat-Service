package com.panlei.web.model;

/**
 * Created by 361pa on 2017/6/19.
 */
public class Board {

    private Integer id;
    private String boardName;
    private String boardNameCN;
    private String sectionName;
    private String sectionValue;
    private String boardUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardNameCN() {
        return boardNameCN;
    }

    public void setBoardNameCN(String boardNameCN) {
        this.boardNameCN = boardNameCN;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionValue() {
        return sectionValue;
    }

    public void setSectionValue(String sectionValue) {
        this.sectionValue = sectionValue;
    }

    public String getBoardUrl() {
        return boardUrl;
    }

    public void setBoardUrl(String boardUrl) {
        this.boardUrl = boardUrl;
    }
}
