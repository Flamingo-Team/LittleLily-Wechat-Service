package com.panlei.web.dao;


import com.panlei.web.model.SectionMain;

import java.util.List;

/**
 * Created by 361pa on 2017/6/19.
 */
public interface SectionMapper {
    int insert(SectionMain sectionMain);
    public List<SectionMain> getAllSections();
}
