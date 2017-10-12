package com.panlei.web.service.impl;

import com.panlei.web.model.MsgInfo;
import com.panlei.web.service.MsgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

import com.panlei.web.dao.MsgInfoMapper;

@Service("msgInfoService")
public class MsgInfoServiceImpl implements MsgInfoService{

    @Autowired
    private MsgInfoMapper msgInfoMapper;

    public int insert(MsgInfo pojo){
        return msgInfoMapper.insert(pojo);
    }

    public int insertSelective(MsgInfo pojo){
        return msgInfoMapper.insertSelective(pojo);
    }

    public int insertList(List<MsgInfo> pojos){
        return msgInfoMapper.insertList(pojos);
    }

    public int update(MsgInfo pojo){
        return msgInfoMapper.update(pojo);
    }

    public String saveMsgInfo(String openid, String formid) {
        MsgInfo msgInfo =  new MsgInfo();
        msgInfo.setOpenId(openid);
        msgInfo.setFormId(formid);
        msgInfo.setCount(1);
        msgInfo.setInsertTime(new Date());
        insert(msgInfo);
        return "Success";
    }

    public List<MsgInfo> getMsgInfoList() {
        return msgInfoMapper.selectAllMsgInfo();
    }
}
