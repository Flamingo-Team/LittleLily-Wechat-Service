package com.panlei.web.service;

import com.panlei.web.dao.AuthInfoMapper;
import com.panlei.web.model.AuthInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/3 0003.
 */
public interface AuthInfoService {

    int insert(AuthInfo pojo);

    int insertSelective(AuthInfo pojo);

    int insertList(List<AuthInfo> pojos);

    int update(AuthInfo pojo);

    Map getDecodeUserInfo(String encryptedData, String iv, String code);

}
