package com.panlei.web.service.impl;

import com.panlei.web.dao.SectionMapper;
import com.panlei.web.model.SectionMain;
import com.panlei.web.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by 361pa on 2017/6/19.
 */
@Service("sectionService")
public class SectionServiceImpl implements SectionService {
    private SectionMapper sectionMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setBoardMapper(SectionMapper sectionMapper){
        this.sectionMapper = sectionMapper;
    }

    public List<SectionMain> getAllSections() throws Exception{
        return sectionMapper.getAllSections();
    }
}
