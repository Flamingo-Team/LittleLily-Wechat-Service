package com.panlei.web.service;

import com.panlei.web.model.MsgInfo;

import java.util.List;

/**
 * Created by yhc on 2017/9/22.
 */
public interface MsgInfoService {

    int insert(MsgInfo pojo);

    int insertSelective(MsgInfo pojo);

    int insertList(List<MsgInfo> pojos);

    int update(MsgInfo pojo);

    String saveMsgInfo(String openid, String formid);

    List<MsgInfo> getMsgInfoList();



}
