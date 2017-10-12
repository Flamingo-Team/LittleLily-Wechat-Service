package com.panlei.web.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.panlei.web.model.MsgInfo;

public interface MsgInfoMapper {
    int insert(@Param("pojo") MsgInfo pojo);

    int insertSelective(@Param("pojo") MsgInfo pojo);

    int insertList(@Param("pojos") List<MsgInfo> pojo);

    int update(@Param("pojo") MsgInfo pojo);

    /**
     * @return
     */
    List<MsgInfo> selectAllMsgInfo();
}
