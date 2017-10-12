package com.panlei.web.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.panlei.web.model.AuthInfo;


public interface AuthInfoMapper {
    int insert(@Param("pojo") AuthInfo pojo);

    int insertSelective(@Param("pojo") AuthInfo pojo);

    int insertList(@Param("pojos") List<AuthInfo> pojo);

    int update(@Param("pojo") AuthInfo pojo);


}
