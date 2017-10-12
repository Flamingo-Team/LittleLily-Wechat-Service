package com.panlei.web.model;

/**
 * Created by 361pa on 2017/6/19.
 */
public class SectionMain {

    private Integer id;
    private String sectionName;
    private String sectionNameCN;
    private String sectionValue;
    private String sectionUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionNameCN() {
        return sectionNameCN;
    }

    public void setSectionNameCN(String sectionNameCN) {
        this.sectionNameCN = sectionNameCN;
    }

    public String getSectionValue() {
        return sectionValue;
    }

    public void setSectionValue(String sectionValue) {
        this.sectionValue = sectionValue;
    }

    public String getSectionUrl() {
        return sectionUrl;
    }

    public void setSectionUrl(String sectionUrl) {
        this.sectionUrl = sectionUrl;
    }
}
