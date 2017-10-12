package com.panlei.web.service;

import com.panlei.web.model.SectionMain;

import java.util.List;

/**
 * Created by 361pa on 2017/6/19.
 */
public interface SectionService {
    List<SectionMain> getAllSections() throws Exception;
}
