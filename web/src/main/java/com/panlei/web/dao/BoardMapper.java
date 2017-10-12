package com.panlei.web.dao;


import com.panlei.web.model.Board;
import com.panlei.web.model.SectionMain;

import java.util.List;

/**
 * Created by 361pa on 2017/6/19.
 */
public interface BoardMapper {

    int insert(SectionMain sectionMain);
    public List<Board> getBoardsBySectionValue(String sectionValue);
    public List<Board> getAllBoards();
}
